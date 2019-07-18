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
                    activeDraft.hide(250, function () {
                        activeDraft.remove();
                        activeDraft = null;
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
            if (result) {
                $(result).each(function (i, item) {
                    var li = $("<li data-nid=" + item.nid + ">\n" +
                               "    <span class='img'><img\n" +
                               "                src='/asserts/img/date_bg.png'\n" +
                               "                style='height: 100px;width: 150px;'></span>\n" +
                               "    <div class='detail'>\n" +
                               "        <div class='title'><a\n" +
                               "                href='/note/editNote/" + item.nid + "'>" + item.title +
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
                    li.find(".i-delete").click(function () {
                        activeDraft = li;
                        $("#_j_layer_0").fadeIn(250);
                    });
                });
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
});