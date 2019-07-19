$(function () {
    setContent();

    initClick();

    //滚动事件
    var sideTopOffset = $("#sidebar").offset().top;
    var sideLeftOffset = $("#sidebar").offset().left;
    $(window).scroll(function(event){
        if ($(window).scrollTop() > sideTopOffset) {
            $("#sidebar").attr("class", "sidebar-fixed");
            $("#sidebar").css({left:sideLeftOffset});
        }
        else {
            $("#sidebar").attr("class", "sidebar");
            $("#sidebar").css({left:0});
        }
    });
});

var index = 0;//title序号

//加载内容
function setContent() {
    //页面加载的时候设置内容
    console.log(content);
    $("._j_content_container").empty();
    $(content).each(function (i, item) {
        if (item['text'] != undefined) {
            if (!testBlank(item['text'])) {
                var text = $("<p class='_j_note_content _j_seqitem'></p>");
                text.html(item['text']);
                $("._j_content_container").append(text);
            }
        }
        else if (item['title'] != undefined) {
            var title = $("<div id='1' class='article_title _j_anchorcnt _j_seqitem'>\n" +
                          "    <h2 class='t9'><span class='_j_anchor'>天津</span></h2>\n" +
                          "</div>");
            title.attr("id", "title-" + index);
            index++;
            title.find("h2 span").text(item['title']);
            $("._j_content_container").append(title);
        }
        else {
            var pic = $("<div class='add_pic _j_anchorcnt _j_seqitem' style='width:680px'>\n" +
                        "    <a>\n" +
                        "        <img style='width: 680px; background: rgb(252, 242, 220); display: block;' src=''>\n" +
                        "    </a>\n" +
                        "</div>");
            pic.find("img").attr("src", item['pic']);
            $("._j_content_container").append(pic);
        }
    });
    refreshTitle();
}

function refreshTitle() {
    $(".catalog_content").empty();
    $("._j_content_box").find(".article_title").each(function (i, item) {
        var li = $("<li to-id=" + $(item).attr("id") + ">\n" +
            "    <span class='catalog_num'>" + (i + 1) + "/</span>\n" +
            "    <a role='button' title=" + $(item).find("h2 span").text() +
            "        class='catalog_line _j_cataloglink'>" + $(item).find("h2 span").text() + "</a>\n" +
            "</li>");
        li.click(function () {
            $("html,body").animate({scrollTop: $("#" + li.attr("to-id")).offset().top}, 200);
        });
        $(".catalog_content").append(li);
    });
}

function initClick() {
    if (uid != null) {
        initThumbs();
        initStar();
    }
    else {
        $("._j_do_fav").click(function () {
            logError("请先登录");
        });
        $(".up_act").click(function () {
            logError("请先登录");
        });
    }
}

function initThumbs() {
    $(".up_act").click(function () {
        if ($(".up_act").attr("hasThumbs") == 0) {
            $.ajax({
                type: "post",
                url: "/thumbsUp",
                contentType: "application/json",
                data:JSON.stringify({
                    uid:uid,
                    type:1,
                    pid:nid
                }),
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        $("._j_up_num").text($("._j_up_num").text() + 1);
                        $(".up_act").attr("hasThumbs", "1")
                        logSuccess("点赞成功");
                    }
                    else {
                        logError('网络故障，请稍后再试');
                    }
                },
                error: function (e) {
                    console.log(e);
                    logError('网络繁忙，请稍后再试');
                }
            });
        }
        else {
            $.ajax({
                type: "post",
                url: "/thumbsDown",
                contentType: "application/json",
                data:JSON.stringify({
                    uid:uid,
                    type:1,
                    pid:nid
                }),
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        $("._j_up_num").text($("._j_up_num").text() - 1);
                        $(".up_act").attr("hasThumbs", "0")
                        logSuccess("取消成功");
                    }
                    else {
                        logError('网络故障，请稍后再试');
                    }
                },
                error: function (e) {
                    console.log(e);
                    logError('网络繁忙，请稍后再试');
                }
            });
        }
    });
}

function initStar() {
    $("._j_do_fav").click(function () {
         if ($(".bs_collect").attr("class") == "bs_collect collected") {
             $.ajax({
                 type: "post",
                 url: "/uncollect",
                 contentType: "application/json",
                 data:JSON.stringify({
                     userId:uid,
                     collectId:nid,
                     type:1
                 }),
                 dataType: "json",
                 async: true,
                 success: function (result) {
                     if (result) {
                         $("#star-count").text($("#star-count").text() - 1);
                         $(".bs_collect").attr("class", "bs_collect")
                         logSuccess("取消收藏成功");
                     }
                     else {
                         logError('网络故障，请稍后再试');
                     }
                 },
                 error: function (e) {
                     console.log(e);
                     logError('网络繁忙，请稍后再试');
                 }
             });
         }
         else {
             $.ajax({
                 type: "post",
                 url: "/collect",
                 contentType: "application/json",
                 data:JSON.stringify({
                     userId:uid,
                     collectId:nid,
                     type:1
                 }),
                 dataType: "json",
                 async: true,
                 success: function (result) {
                     if (result) {
                         $("#star-count").text($("#star-count").text() + 1);
                         $(".bs_collect").attr("class", "bs_collect collected")
                         logSuccess("收藏成功");
                     }
                     else {
                         logError('网络故障，请稍后再试');
                     }
                 },
                 error: function (e) {
                     console.log(e);
                     logError('网络繁忙，请稍后再试');
                 }
             });
         }
    });
}


function testBlank(str) {
    str = str.replace(" ", "");
    var reg = /<br\/>/g;
    str = str.replace(reg, "");
    return str.length == 0;
}