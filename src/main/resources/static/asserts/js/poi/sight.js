$(function () {
    $(".btn-collect").click(function () {
        if (uid == null) {
            logError("请先登录");
        }
        else {
            clickCollect();
        }
    });
});

function clickCollect() {
    if ($(".btn-collect").attr("class") == "_j_favpoi btn-collect") {
        $.ajax({
            type: "post",
            url: "/collect",
            data: JSON.stringify({
                userId: uid,
                collectId: sid,
                type: type
            }),
            contentType: "application/json",
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    logSuccess("收藏成功");
                    $("#text-collect").text("已收藏");
                    $(".btn-collect").attr("class", "_j_favpoi btn-collect on")
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
        $.ajax({
            type: "post",
            url: "/uncollect",
            data: JSON.stringify({
                userId: uid,
                collectId: sid,
                type: type
            }),
            contentType: "application/json",
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    logSuccess("取消收藏成功");
                    $("#text-collect").text("收藏");
                    $(".btn-collect").attr("class", "_j_favpoi btn-collect")
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
}