var data = new Array();

$(document).ready(function() {

    $.ajax({
        type: "post",
        url: "http://localhost:8080/together/mdd_top",
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            $(result).each(function (i, mdd) {
                $("#together-mdd-box").append("<li class=\"item\">\n" +
                    "    <a class=\"_j_hot_mdd\" data-mddname=''" + mdd.name + " data-mddid:" + mdd.id + ">\n" +
                    "    <div class=\"image\"><img src=" + mdd.src + " style=\"width: 220px;height: 220px;\"></div>\n" +
                    "    <div class=\"bg\"></div>\n" +
                    "    <div class=\"txt\">\n" +
                    "    <strong>" + mdd.name + "</strong>\n" +
                    "    <p>发起 " + mdd.count + " 个结伴<br>" + 0 + " 人关注<br>" + mdd.app + " 人报名</p>\n" +
                    "    </div>\n" +
                    "    </a>\n" +
                    "</li>\n");
            });
        },
        error: function (e) {
            console.log(e);
        }
    });

    $.ajax({
        type: "post",
        url: "http://localhost:8080/together/mdd_search",
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            setData(result);
        },
        error: function (e) {
            console.log(e);
        }
    });

    for (var i = 0; i < 8; i++) {
        $("._j_together_list").append("<li class=\"item\">\n" +
            "                    <a href=\"/together/detail/2362047.html\" target=\"_blank\">\n" +
            "                        <div class=\"image\">\n" +
            "                            <img src=\"http://b2-q.mafengwo.net/s7/M00/E8/AA/wKgB6lQ3UfSAIxLHAAE0oJAh8c028.jpeg?imageMogr2%2Fthumbnail%2F%21200x130r%2Fgravity%2FCenter%2Fcrop%2F%21200x130%2Fquality%2F100\" style=\"width: 200px;height: 130px;\">\n" +
            "                            <div class=\"after\"><b>16</b>天后</div>\n" +
            "                            <hr>\n" +
            "                        </div>\n" +
            "                        <h3 class=\"title\">独库公路|喀纳斯|北疆</h3>\n" +
            "                        <div class=\"desc\">90后爱旅行的菇凉一枚，目前四个菇凉，机票已出，希望找（会...</div>\n" +
            "                        <div class=\"user clearfix\">\n" +
            "                <span class=\"avatar\"><img src=\"http://n4-q.mafengwo.net/s14/M00/E7/0E/wKgE2l0J7muAR4QiAAC1CK3ccSk14.jpeg?imageMogr2%2Fthumbnail%2F%21120x120r%2Fgravity%2FCenter%2Fcrop%2F%21120x120%2Fquality%2F90\" width=\"48\">\n" +
            "                </span>\n" +
            "                            <span class=\"name\">卢卢  </span>\n" +
            "                            <span class=\"level\">上海</span>\n" +
            "                        </div>\n" +
            "                        <div class=\"attention\"><i class=\"icon-arrow\"></i>已有<b>21</b>人关注</div>\n" +
            "                    </a>\n" +
            "                </li>");
    }

    initDropBar();
});

function initDropBar() {
    $("#_j_together_mdd_search").focus(function () {
        $("#drop-place").show(250);
    });
    $("#_j_together_mdd_search").blur(function () {
        $("#drop-place").hide(250);
    });
    $("._j_time_input").focus(function () {
        $("#drop-date").show(250);
    });
    $("._j_time_input").blur(function () {
        $("#drop-date").hide(250);
    });
}

//根据json数据设置地点选择框
function setData(json) {
    $(json).each(function (i, map) {
        $(".col1").append("<li class=\"_j_country_select\" data-index=" + i + " data-name=" + map["name"] +
            "><a>" + map["name"] + "<i class=\"icon-arrow\"></i></a></li>");
        var list = new Array();
        for (var key in map) {
            if (key != "name") {
                list[key] = map[key];
            }
        }
        data[i] = list;
    });

    //col1框中数据点击事件
    $("._j_country_select").mouseenter(function () {
        $(".col2").empty();
        initCol2($(this).attr("data-index"));
        $(".on").attr("class", "_j_country_select");
        $(this).attr("class", "on");
    });

    $(".col1").children("li").get(0).attr("class", "on");
    initCol2(0);
}

//初始化col2
function initCol2(col1) {
    $(data[col1]).each(function (i, item) {
        $(".col2").append("<li class=\"_j_city_select\" data-mddid=" + item.id + " data-name=" + item.name +
            "       ><a><span class=\"num\"><em>" + item.count + "</em>结伴</span>" + item.name + "</a></li>");

        //    TODO：col2点击事件
    });
}
