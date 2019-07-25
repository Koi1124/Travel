$(function () {
    setContent();

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

var index = 0;

//加载内容
function setContent() {
    //页面加载的时候设置内容
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

//==============管理员====================================================
function changeStauts(status) {
    $.ajax({
        type: "post",
        url: "/note/changeStatus",
        data: JSON.stringify({
            nid: nid.toString(),
            status: status
        }),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            if (result) {
                if (status == 2)
                    logSuccess("通过成功, 1秒后页面自动关闭");
                else if (status == 3)
                    logSuccess("设置成功, 1秒后页面自动关闭");
                $(".btn-publish").unbind();

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
            console.log(e);
        }
    });
}

function setTopNote() {
    $.ajax({
        type: "post",
        url: "/admin/note/setTop",
        data: JSON.stringify({
            nid: nid.toString(),
            status: 2,
            title:title,
            pic:pic,
            date:date
        }),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            if (result) {
                if (status == 2)
                    logSuccess("通过成功, 1秒后页面自动关闭");
                else if (status == 3)
                    logSuccess("设置成功, 1秒后页面自动关闭");
                $(".btn-publish").unbind();

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
            console.log(e);
        }
    });
}

//==============工具方法==================================================
//检验字符串是否为空
function testBlank(str) {
    str = str.replace(" ", "");
    var reg = /<br\/>/g;
    str = str.replace(reg, "");
    return str.length == 0;
}