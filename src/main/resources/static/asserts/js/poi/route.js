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
            d.append("<span class='arrow'>→</span>");
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


//编写自定义函数,创建标注
function addMarker(point, map) {
    var marker = new BMap.Marker(point);
    map.addOverlay(marker);
}