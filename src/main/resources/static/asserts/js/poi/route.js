var days = new Array();

$(function () {
    $(routes).each(function (i, route) {
        days.push(route.pois);
    });
    search(days[0]);
    $(".J_overview").empty();
    for (var i = 0; i < days.length; i++) {
        var d = $("<p class='day'><a onclick='turnDays(" + i + ")' class='num'>D" + (i + 1) + "</a></p>");
        for (var j = 0; j < days[i].length; j++) {
            d.append("<a href='/sight/" + days[i][j].sid + "' target='_blank'>" + days[i][j].name + "</a>");
            if (j != days[i].length - 1)
            d.append("<span class='arrow' style='padding-top: 0px'>→</span>");
        }
        $(".J_overview").append(d);
    }
});

function turnDays(i) {
    search(days[i]);
}

function search(result) {
    points = [];
    map.clearOverlays(); // 清除标注信息

    var points = []; // 添加折线运动轨迹
    for (i = 0; i < result.length; i++) {
        var longitude = result[i].longitude; // 经度
        var latitude = result[i].latitude; // 纬度
        var point = new BMap.Point(longitude, latitude); // 填充标注点
        points.push(point);
    }


    var convertor = new BMap.Convertor();

    convertor.translate(points, 3, 5, function(data){
        console.log(data);
        points = [];
        for (i = 0; i < data.points.length; i++) {
            var point = new BMap.Point(data.points[i].lng, data.points[i].lat);
            addMarker(point, map);
            points.push(point);
        }

        var view = map.getViewport(eval(points));
        var mapZoom = view.zoom;
        var centerPoint = view.center;
        map.centerAndZoom(centerPoint, mapZoom);

        var polyline = new BMap.Polyline(points);
        map.addOverlay(polyline);
    })
}

function clickStar() {
    if (uid == null) {
        pleaseLogin("收藏");
        return;
    }
    $("#btn-star").click(function () {
        if ($(this).find("i").attr("class") == "icon-like") {
            $.ajax({
                type: "post",
                url: "/collect",
                data: JSON.stringify({
                    userId: uid,
                    collectId: rid,
                    type: type
                }),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        logSuccess("收藏成功");
                        $(this).find("i").attr("class", "icon-liked")
                    }
                    else {
                        logError("网络故障，请稍后再试");
                    }
                },
                error: function (e) {
                    logError("网络故障，请稍后再试");
                    console.log(e);
                }
            });
        }
        else {
            $.ajax({
                type: "post",
                url: "/uncollect",
                data: JSON.stringify({
                    userId: uid,
                    collectId: rid,
                    type: type
                }),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        logSuccess("取消收藏成功");
                        $(this).find("i").attr("class", "icon-like")
                    }
                    else {
                        logError("网络故障，请稍后再试");
                    }
                },
                error: function (e) {
                    logError("网络故障，请稍后再试");
                    console.log(e);
                }
            });
        }
    });
}


function clickThumbsUp() {
    if (uid == null) {
        pleaseLogin("点赞");
        return;
    }
    $("#btn-star").click(function () {
        if ($(this).find("i").attr("class") == "icon-like") {
            $.ajax({
                type: "post",
                url: "/thumbsUp",
                data: JSON.stringify({
                    userId: uid,
                    collectId: rid,
                    type: type
                }),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        logSuccess("点赞成功");
                        $(this).find("i").attr("class", "icon-liked")
                    }
                    else {
                        logError("网络故障，请稍后再试");
                    }
                },
                error: function (e) {
                    logError("网络故障，请稍后再试");
                    console.log(e);
                }
            });
        }
        else {
            $.ajax({
                type: "post",
                url: "/thumbsDown",
                data: JSON.stringify({
                    userId: uid,
                    collectId: rid,
                    type: type
                }),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        logSuccess("取消点赞成功");
                        $(this).find("i").attr("class", "icon-like")
                    }
                    else {
                        logError("网络故障，请稍后再试");
                    }
                },
                error: function (e) {
                    logError("网络故障，请稍后再试");
                    console.log(e);
                }
            });
        }
    });
}


//编写自定义函数,创建标注
function addMarker(point, map) {
    var marker = new BMap.Marker(point);
    map.addOverlay(marker);
}