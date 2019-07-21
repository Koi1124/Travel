$(function () {
    search();
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