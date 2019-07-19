var activeDraft = null;

$(function () {
    $("._j_pop_close").find("i").click(function () {
        $(".drafts").fadeOut(250);
    });

    $(".btn2").find("._jump").click(function () {
        $(".drafts").fadeIn(250);
    });

    // 删除弹出框
    $(".popbtn-cancel").click(function () {
        $("#_j_layer_0").fadeOut(250);
    });
    //删除，即把状态该成9
    $(".popbtn-submit").click(function () {
        $.ajax({
            type: "post",
            url: "/note/changeStatus",
            contentType: "application/json",
            dataType: "json",
            data:JSON.stringify({
                nid:activeDraft.attr("data-nid"),
                status:9
            }),
            async: true,
            success: function (result) {
                if (result) {
                    $("#_j_layer_0").fadeOut(250);
                    activeDraft.hide(250, function () {
                        activeDraft.remove();
                        activeDraft = null;
                        logSuccess("删除成功");
                    });
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
    });


    $.ajax({
        type: "post",
        url: "/note/myDraft",
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            if (result != null) {
                $("._j_roughcount").text(result.length);
                if (result.length == 0) {
                    $("#todo-draft").html("还没有草稿哦，快去写游记吧~");
                }
                $(result).each(function (i, item) {
                    var li = $("<li data-nid=" + item.nid + ">\n" +
                               "    <span class='img'><img\n" +
                               "                src='/asserts/img/common/page_bg.jpg'\n" +
                               "                style='height: 100px;width: 150px;'></span>\n" +
                               "    <div class='detail'>\n" +
                               "        <div class='title'><a\n" +
                               "                href='/note/editNote/" + item.nid + "'>未命名草稿" +
                               "           </a></div>\n" +
                               "        <span class='time'>" + item.editTime + "</span>\n" +
                               "        <div class='action'>\n" +
                               "            <a class='btn-continue' href='/note/editNote/" + item.nid + "'><i\n" +
                               "                    class='icon-write'></i>继续写</a>\n" +
                               "            <a class='i-delete _j_del_rough'>删除</a>\n" +
                               "        </div>\n" +
                               "    </div>\n" +
                               "</li>");
                    if (item.topImg != "") {
                        li.find("img").attr("src", item.topImg);
                    }
                    if (!testBlank(item.title)) {
                        li.find(".title").find("a").text(item.title);
                    }
                    li.find(".i-delete").click(function () {
                        activeDraft = li;
                        $("#_j_layer_0").fadeIn(250);
                    });
                    $("#darft-container").append(li);
                });
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
});

function testBlank(str) {
    str = str.replace(" ", "");
    var reg = /\n/g;
    str = str.replace(reg, "");
    return str.length == 0;
}