var followIds =  new Array();

$(function () {
    $("#btn-follow").click(function () {
        $("hhh").text("关注");
        $("#myfollow").show();
        $("#myfan").hide();
        $("#btn-follow").attr("class", "on");
        $("#btn-fan").attr("class", "");
        $("#count").text(followCount);
    });
    $("#btn-fan").click(function () {
        $("hhh").text("粉丝");
        $("#myfollow").hide();
        $("#myfan").show();
        $("#btn-follow").attr("class", "");
        $("#btn-fan").attr("class", "on");
        $("#count").text(fanCount);
    });

    if (isFan != null) {
        $("#btn-fan").click();
    }
    $("#myfollow").find("li").each(function (i, item) {
        followIds.push($(item).attr("userId"));
    });
    $("#myfan").find("li").each(function (i, item) {
        if (followIds.indexOf($(item).attr("userId")) > -1) {
            $(item).find("._j_add_friend").html("<span class=\"_j_change\">已关注</span>\n" +
                "<i class=\"_j_do_change\">取消关注</i>");
            $(item).find("._j_add_friend").attr("class", "btn_qx _j_add_friend");
        }
    });
    $("._j_add_friend").click(function () {
        var uid = $(this).closest("li").attr("userId");
        if ($(this).attr("class") == "btn_qx _j_add_friend") {
            //取消关注
            unfollow(uid);
        }
        else {
            //关注
            follow(uid);
        }
    });
});

function follow(uid) {
    $.ajax({
        type: "post",
        url: "/follow",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            userId:uid.toString(),
            followerId:myId.toString()
        }),
        success:function (result) {
            if (result){
                $("li[userId=" + uid + "]").find("._j_add_friend").html("<span class=\"_j_change\">已关注</span>\n" +
                    "<i class=\"_j_do_change\">取消关注</i>");
                $("li[userId=" + uid + "]").find("._j_add_friend").attr("class", "btn_qx _j_add_friend");
                logSuccess("关注成功");
            }
            else {
                logError("网络故障，请稍后再试");
            }
        },
        error:function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });
}

function unfollow(uid) {
    $.ajax({
        type: "post",
        url: "/unfollow",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            userId:uid.toString(),
            followerId:myId.toString()
        }),
        success:function (result) {
            if (result){
                $("li[userId=" + uid + "]").find("._j_add_friend").html("<i class=\"_j_do_change\">关注</i>");
                $("li[userId=" + uid + "]").find("._j_add_friend").attr("class", "_j_add_friend");
                logSuccess("取消关注成功");
            }
            else {
                logError("网络故障，请稍后再试");
            }
        },
        error:function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });

}