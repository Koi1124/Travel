var box = "<div class='day-item'>\n" +
    "    <div class='J_playpois'>\n" +
    "        <div class='day-hd'>\n" +
    "            <span class='day-num' >D1</span>\n" +
    "            <span class='tit'>\n" +
    "                <i class='i-scenic'></i>游玩攻略\n" +
    "                <a onclick='deleteDay(this)'>删除</a>\n" +
    "            </span>\n" +
    "        </div>\n" +
    "        <textarea class='poi-txt' style='width:100%;height:200px'></textarea>\n" +
    "        <div>\n" +
    "            <div class='poi-name'>\n" +
    "                <ul class='clearfix'>\n" +
    "                    <li class='route-line'>\n" +
    "                        <!--路线地点顺序-->\n" +
    "                        <div class='tn-selected _j_tag_choose_container' style='display: block;'>\n" +
    "                        </div>\n" +
    "                    </li>\n" +
    "                </ul>\n" +
    "            </div>\n" +
    "            <div class='poi-figure poi-play figure-slide' style='width: 100%;overflow-x:auto;height:auto'>\n" +
    "                <span style='font-size: 16px'>景点顺序：</span><input class='poi-input' type='text' style='font-size: 16px'/>\n" +
    "            </div>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class='J_trafficpois hide' style='display: block;'>\n" +
    "        <div class='day-hd mt30'>\n" +
    "            <span class='tit'><i class='i-traffic'></i>交通攻略</span>\n" +
    "        </div>\n" +
    "        <div>\n" +
    "            <textarea class='traffic' style='width: 100%; height: 200px;font-size: 16px'></textarea>\n" +
    "        </div>\n" +
    "        <div class='ticket-box clearfix'>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <div class='J_hotelpois hide' style='display: block;'>\n" +
    "        <div class='day-hd mt30'>\n" +
    "            <span class='tit'><i class='i-hotel'></i>住宿攻略</span>\n" +
    "        </div>\n" +
    "        <span style='font-size: 24px'>住宿地点：</span><input class='stayName' type='text' style='font-size: 24px'/>\n" +
    "        <br/>\n" +
    "        <span style='font-size: 24px'>住宿简介：</span><textarea class='stayInfo' style='width: 100%;height: 100px;font-size: 16px'></textarea>\n" +
    "    </div>\n" +
    "</div>";


$(function () {
    loadCity();
    loadSight();
});

var avaliableTags;

function addDay() {
    var newbox = $(box);
    newbox.find(".poi-input").autocomplete({
        source:avaliableTags,
        message:{
            results: function(){
            },
            noResults: ''
        },
        select:function (event,ui) {
            var str ="<hhh>\n" +
                "    <a class='tn-tag _j_gs_tag' poi=" + ui.item.id + ">" + ui.item.value + "<i onclick='removeThis(this)'></i></a>\n" +
                "</hhh>\n";
            var btn = $(this);
            $(this).closest(".day-item").find("._j_tag_choose_container").append(str);
            setTimeout(function () {
                btn.val("");
            }, 100);
        }
    });
    $("#btn-add").before(newbox);
    renameTitle();
}

function removeThis(e) {
    $(e).closest("hhh").remove();
}

function deleteDay(e) {
    $(e).closest('.day-item').remove();
    renameTitle();
}

function clickSubmit() {
    if (testBlank($("#title-input").val())) {
        logError("标题不能为空");
        return;
    }
    if (testBlank($("#poi-hidden").val())) {
        logError("城市不能为空");
        return;
    }
    var routes = new Array();
    var t = true;
    $(".day-item").each(function (i, item) {
        var pois = "";
        $(item).find("._j_gs_tag").each(function (i, poi) {
            pois += ',' + $(poi).attr("poi");
        });
        if (pois == "") {
            logError("攻略路线不能为空");
            t = false;
            return;
        }
        else if (testBlank($(item).find(".poi-txt").val())) {
            logError("攻略不能为空");
            t = false;
            return;
        }
        var traffic = $(item).find(".traffic").val();
        if (testBlank(traffic)) traffic = "";
        var stayname = $(item).find(".stayName").val();
        if (testBlank(stayname)) stayname = "";
        var stayInfo = $(item).find(".stayInfo").val();
        if (testBlank(stayInfo)) stayInfo = "";

        var data = {
            index: i + 1,
            play:$(item).find(".poi-txt").val(),
            pois:pois.substr(1),
            traffic:traffic,
            stayName:stayname,
            stayInfo:stayInfo,
            stayPic:"http://b3-q.mafengwo.net/s10/M00/76/61/wKgBZ1mBm7qAS6ZKAAD5Q7_NOvE857.png?imageMogr2%2Fthumbnail%2F%21540x360r%2Fgravity%2FCenter%2Fcrop%2F%21540x360%2Fquality%2F100"
        };
        routes.push(data);
    });
    if (t) {
        if (routes.length > 0) {
            var all, url, msg;
            if (rid == null) {
                all = {
                    name: $("#title-input").val(),
                    summary: $("#summary").val(),
                    pid: $("#poi-hidden").val(),
                    pic: "http://n2-q.mafengwo.net/s10/M00/4F/AF/wKgBZ1lsKTuAUOcqAAJLnuu3Czc692.png?imageMogr2%2Fthumbnail%2F%21540x320r%2Fgravity%2FCenter%2Fcrop%2F%21540x320%2Fquality%2F100",
                    routes: routes
                };
                url = "/admin/strategy/add";
                msg = "添加";
            }
            else {
                all = {
                    name: $("#title-input").val(),
                    summary: $("#summary").val(),
                    pid: $("#poi-hidden").val(),
                    pic: "http://n2-q.mafengwo.net/s10/M00/4F/AF/wKgBZ1lsKTuAUOcqAAJLnuu3Czc692.png?imageMogr2%2Fthumbnail%2F%21540x320r%2Fgravity%2FCenter%2Fcrop%2F%21540x320%2Fquality%2F100",
                    routes: routes,
                    sid:rid
                };
                url = "/admin/strategy/update";
                msg = "修改";
            }
            console.log(all);
            $.ajax({
                url: url,
                type: "post",
                data:JSON.stringify(all),
                dataType: "json",
                contentType: "application/json",
                async: true,
                success: function (data) {
                    if (data) {
                        logSuccess(msg + "成功，1秒后自动关闭");
                        setTimeout(function () {
                            xadmin.close();
                            xadmin.father_reload();
                        }, 1000);
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
            logError("路线不能为空");
        }
    }
}

function renameTitle() {
    $(".day-item").each(function (i, item) {
        $(item).find(".day-num").text("D" + (i + 1));
    });
}

function loadCity() {
    $.ajax({
        url: "/together/mdd_info",
        type: "post",
        dataType: "json",
        async: true,
        success: function (data) {
            var tags = eval(data);
            $("#poi-input").autocomplete({
                source:tags,
                message:{
                    results: function(){
                    },
                    noResults: ''
                },
                select:function (event,ui) {
                    $("#poi-hidden").val(ui.item.id);
                }
            });
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function loadSight() {
    $.ajax({
        type: "post",
        url: "/getAllSights",
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            avaliableTags = eval(result);
            $(".poi-input").autocomplete({
                source:avaliableTags,
                message:{
                    results: function(){
                    },
                    noResults: ''
                },
                select:function (event,ui) {
                    var str ="<hhh>\n" +
                        "    <a class='tn-tag _j_gs_tag' poi=" + ui.item.id + ">" + ui.item.value + "<i onclick='removeThis(this)'></i></a>\n" +
                        "</hhh>\n";
                    var btn = $(this);
                    $(this).closest(".day-item").find("._j_tag_choose_container").append(str);
                    setTimeout(function () {
                        btn.val("");
                    }, 100);
                }
            });
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function testBlank(str) {
    str = str.replace(/\s+/g, "");
    var reg = /\n/g;
    str = str.replace(reg, "");
    return str.length == 0;
}