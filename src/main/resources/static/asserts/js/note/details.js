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
            title.find("h2 span").html(item['title']);
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

//加载标题
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

//初始化有关消息的点击事件
function initClick() {
    if (uid != null) {
        initThumbs();
        initStar();
        initFollow();
    }
    else {
        $("._j_do_fav").click(function () {
            logError("请先登录");
        });
        $(".up_act").click(function () {
            logError("请先登录");
        });
        $(".per_attention").click(function () {
            logError("请先登录");
        });
    }
}

//点赞点击事件
function initThumbs() {
    $(".up_act").click(function () {
        if ($(".up_act").attr("hasThumbs") == 0) {
            $.ajax({
                type: "post",
                url: "/thumbsUp",
                contentType: "application/json",
                data:JSON.stringify({
                    uid:uid,
                    type:type,
                    pid:nid,
                    rUserId:rUserId,
                    jump_type:type,
                    title:title,
                    jump_pid:nid
                }),
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        $("._j_up_num").text(parseInt($("._j_up_num").text()) + 1);
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
                    type:type,
                    pid:nid
                }),
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        $("._j_up_num").text(parseInt($("._j_up_num").text()) - 1);
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

//收藏点击事件
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
                     type:type
                 }),
                 dataType: "json",
                 async: true,
                 success: function (result) {
                     if (result) {
                         $("#star-count").text(parseInt($("#star-count").text()) - 1);
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
                     type:type,
                     rUserId:rUserId,
                     title:title
                 }),
                 dataType: "json",
                 async: true,
                 success: function (result) {
                     if (result) {
                         $("#star-count").text(parseInt($("#star-count").text()) + 1);
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

//关注作者点击事件
function initFollow() {
    $(".per_attention").click(function () {
        $.ajax({
            type: "post",
            url: "/follow",
            contentType: "application/json",
            data:JSON.stringify({
                userId:rUserId,
                followerId:uid
            }),
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    $(".per_attention").remove();
                    logSuccess("关注成功");
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
    });
}

//==============作者操作==================================================
function initDelete() {

}

//==============工具方法==================================================
//检验字符串是否为空
function testBlank(str) {
    str = str.replace(" ", "");
    var reg = /<br\/>/g;
    str = str.replace(reg, "");
    return str.length == 0;
}
