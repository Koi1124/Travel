var id = -1;
var s_type=2;
var page = 1;
var totalPage = 10;
var itemCount = 10;


$(function () {
    search();
    getNotes(mdd,s_type);

    $(".tn-nav-hot").click(function () {
        $("._j_gs_tab").removeClass("active");
        $(".tn-nav-hot").addClass("active");
        s_type=2;
        getNotes(id,s_type);
        scroll();
    });
    $(".tn-nav-new").click(function () {
        $("._j_gs_tab").removeClass("active");
        $(".tn-nav-new").addClass("active");
        s_type=1;
        getNotes(id,s_type);
        scroll();
    });
});




//标注点
function search() {
    if (results.length > 0) {
        map1.clearOverlays(); // 清除标注信息
        var points = []; // 添加折线运动轨迹
        for (var j = 0; j < results[0].routes.length; j++) {
            var longitude = results[0].routes[j].longitude; // 经度
            var latitude = results[0].routes[j].latitude; // 纬度
            var point = new BMap.Point(longitude, latitude); // 填充标注点
            points.push(point);
        }


        var convertor = new BMap.Convertor();

        convertor.translate(points, 3, 5, function (data) {
            points = [];
            for (var j = 0; j < data.points.length; j++) {
                var point = new BMap.Point(data.points[j].lng, data.points[j].lat);
                addMarker(point, map1);
                points.push(point);
            }

            var view = map1.getViewport(eval(points));
            var mapZoom = view.zoom;
            var centerPoint = view.center;
            map1.centerAndZoom(centerPoint, mapZoom);

            var polyline = new BMap.Polyline(points);
            map1.addOverlay(polyline);
        });
    }
    if (results.length > 1) {
        map2.clearOverlays(); // 清除标注信息
        var points = []; // 添加折线运动轨迹
        for (var j = 0; j < results[1].routes.length; j++) {
            var longitude = results[1].routes[j].longitude; // 经度
            var latitude = results[1].routes[j].latitude; // 纬度
            var point = new BMap.Point(longitude, latitude); // 填充标注点
            points.push(point);
        }


        var convertor = new BMap.Convertor();

        convertor.translate(points, 3, 5, function (data) {
            points = [];
            for (var j = 0; j < data.points.length; j++) {
                var point = new BMap.Point(data.points[j].lng, data.points[j].lat);
                addMarker(point, map2);
                points.push(point);
            }

            var view = map2.getViewport(eval(points));
            var mapZoom = view.zoom;
            var centerPoint = view.center;
            map2.centerAndZoom(centerPoint, mapZoom);

            var polyline = new BMap.Polyline(points);
            map2.addOverlay(polyline);
        });
    }
}

//编写自定义函数,创建标注
function addMarker(point, map) {
    var marker = new BMap.Marker(point);
    map.addOverlay(marker);
}


function scroll() {
    var scroll_offset = $(".zy-wrapper").offset(); //得到pos这个div层的offset，包含两个值，top和left
    $("body,html").animate({
        scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
    },400);
}


function setNotes(data) {
    $(data).each(function (i,item) {
        $(".tn-list").append('<div class="tn-item clearfix">\n' +
            '                                <div class="tn-image">\n' +
            '                                    <a href="/note/'+ item.id +'" target="_self">\n' +
            '                                        <img class="" src="'+ item.coverPic +'" style="display: inline;">\n' +
            '\n' +
            '\n' +
            '                                    </a>\n' +
            '                                </div>\n' +
            '                                <div class="tn-wrapper">\n' +
            '                                    <dl style="overflow:hidden">\n' +
            '                                        <dt>\n' +
            '                                            <a href="/note/'+ item.id +'" target="_self">'+ item.title +'</a>\n' +
            '                                        </dt>\n' +
            '                                        <dd>\n' +
            '                                            <a href="/note/'+ item.id + '" target="_self"> \n' +
            '                                                '+ item.content +'</a>\n' +
            '                                        </dd>\n' +
            '                                    </dl>\n' +
            '                                    <div class="tn-extra">\n' +
            '                                        <span class="tn-ding">\n' +
            '                                            <a class="btn-ding" href="javascript:;" data-japp="articleding" data-iid="'+ item.id +'" data-vote="163" rel="nofollow"></a>\n' +
            '                                            <em>'+ item.thumbsUpCount +'</em>\n' +
            '                                        </span>\n' +
            '                                        <span class="tn-place"><i></i><a href="javascript:void(0);" class="_j_gs_item" rel="nofollow" data-name="'+ item.mddName +'" data-objid="'+ item.mddId +'" data-type="2">'+ item.mddName +'</a>，by</span>\n' +
            '                                        <span class="tn-user">\n' +
            '                                            <a href="/u/'+ item.authorId +'/note" target="_self" rel="nofollow">\n' +
            '                                                <img src="'+ item.authorPic +'" style="width:15px;padding-bottom:1px">\n' +
            '                                                '+ item.authorName +'\n' +
            '                                            </a>\n' +
            '                                        </span>\n' +
            '                                        <span class="tn-nums"><i></i>'+ item.view +'/'+ item.commentCount +'</span>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>');
    })
}

function getNotes(mddId,type) {
    // 主页游记列表
    $.ajax({
        type: "post",
        url: "/displayNotes",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            page:page,
            itemCount:itemCount,
            mddId:mddId,
            type:type
        }),
        success:function (result) {
            if (id != mddId){
                id = mddId;
                s_type=type;
                page = 1;
                totalPage =Math.ceil(result.count / itemCount);
                initPagination();
            }
            $(".tn-list").empty();
            setNotes(result.noteInfo);
        },
        error:function (e) {
            console.log(e);
        }
    });
}


function initPagination() {
    $("#pagination").pagination({
        currentPage: page,// 当前页数
        totalPage: totalPage,// 总页数
        isShow: true,// 是否显示首尾页
        count: 10,// 显示个数
        homePageText: "首页",// 首页文本
        endPageText: "尾页",// 尾页文本
        prevPageText: "上一页",// 上一页文本
        nextPageText: "下一页",// 下一页文本
        callback: function(current) {
            // 回调,current(当前页数)
            scroll();
            page = current;
            getNotes(id,s_type)

        }
    });
}