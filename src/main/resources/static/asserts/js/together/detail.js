var joinUrl = "/together/join";
var gender = "1";
$(function () {
    $(".radio").click(function () {
        $(this).closest(".gender").find(".radio").attr("class", "radio _j_add_gender");
        $(this).attr("class", "radio on _j_add_gender");
        gender = $(this).attr("data-gender");
    })

    $("#join_submit").click(function () {
        clickSubmit();
    });

    //判断用户之前有没有提交过加入信息
    $.ajax({
        type: "post",
        url: "/together/check_join",
        contentType: "application/json",
        data:JSON.stringify({
            uid:$("#user_id").val(),
            tid:pid
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            if (result != null) {
                $("._j_add_phone").attr("value", result.phone);
                $("._j_together_men").attr("value", result.count);
                $("._j_together_names").attr("value", result.list);
                $("._j_together_remark").attr("value", result.addition);
                if (result.sex == "2") $(".radio[data-gender='2']").click();
                $("#join_submit").text("修改信息");
                if (result.status == "1") {
                    $("#join_submit").unbind();
                    $("#join_submit").click(function () {
                        logError("请联系发起人以修改信息");
                    });
                }
                else {
                    if (result.status == "2") $("#join_submit").text("重新报名");
                    joinUrl = "/together/join_update";
                }
            }
        },
        error: function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });
});

function clickSubmit() {
    if ($("._j_add_phone").val() == "") {
        logError("联系方式不能为空！");
        return;
    }
    if ($("._j_together_men").val() == "") {
        $("._j_together_men").attr("value", "0");
    }
    else if (!IsNum($("._j_together_men").val())) {
        logError("请输入正确的同行人数！");
        return
    }
    var data = {
        tid: pid,
        phone: $("._j_add_phone").val(),
        count: $("._j_together_men").val(),
        list: $("._j_together_names").val(),
        addition: $("._j_together_remark").val(),
        sex: gender,
        uid: userId
    };

    $.ajax({
        type: "post",
        url: joinUrl,
        contentType: "application/json",
        data:JSON.stringify(data),
        dataType: "json",
        async: true,
        success: function (result) {
            if (result) {
                joinUrl = "/together/join_update";
                $("#join_submit").text("修改信息");
                logSuccess("提交成功");
            }
            else {
                logError("网络故障");
            }
        },
        error: function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });

}

//判断是否为数字
function IsNum(s)
{
    if (s!=null && s!="")
    {
        return !isNaN(s);
    }
    return false;
}

$(document).ready(function () {
    $(".cell-join").click(function () {
        var scroll_offset = $(".mod-joinform").offset(); //得到pos这个div层的offset，包含两个值，top和left
        $("body,html").animate({
            scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
        },400);
    });

    // 结伴关注点击事件
    $("._j_together_care").click(function () {
        var data;
        var collectNum=$(".total span:eq(2)").text();
        console.log(collectNum);
        var userId=$("#user_id").val();
        var path=window.location.pathname;
        var collectId=path.substr(path.lastIndexOf("/")).split(".")[0].split("/")[1];
        data={
            userId:userId,
            collectId:collectId,
            type:5
        }
        $.ajax({
            type: "post",
            url: "/collect",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: "json",
            async: true,
            success:function (result) {
                if (result) {
                    spop({
                        template: '成功关注',
                        position  : 'top-center',
                        style: 'success',
                        autoclose: 1500
                    });
                    $(".cell-collect").attr("class","cell-collect on");
                    $(".cell-collect span").text("已关注结伴");
                    $("._j_together_care").attr("class","btn-collect on");
                    $(".total span:eq(2)").text(Number(collectNum)+1);
                }
                else {
                    spop({
                        template: '网络故障，请稍后再试',
                        position  : 'top-center',
                        style: 'error',
                        autoclose: 1500
                    });
                }
            },
            error: function (e) {
                console.log(e);
            }
        })
    })


    // 用户关注点击事件
    $(".btn-follow").click(function () {
        var isFollow=$(".btn-follow span:eq(0)").text();
        var data;
        var userId=$(".btn-follow").attr("data-uid");
        var followerId=$("#user_id").val();
        data={
            userId:userId,
            followerId:followerId
        }
        console.log(data);
        if (isFollow=="加关注") {
            $.ajax({
                type: "post",
                url: "/follow",
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                async: true,
                success:function (result) {
                    if (result) {
                        spop({
                            template: '成功关注',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $(".follow").remove();
                        $(".btn-follow").append("<span class=\"followed\">已关注</span>");
                        $(".btn-follow").append("<span class=\"unfollow\">取消关注</span>");
                    }
                    else {
                        spop({
                            template: '网络故障，请稍后再试',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }
        else {
            $.ajax({
                type: "post",
                url: "/unfollow",
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                async: true,
                success:function (result) {
                    if (result) {
                        spop({
                            template: '取消关注',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $(".followed").remove();
                        $(".unfollow").remove();
                        $(".btn-follow").append("<span class=\"follow\"><i></i>加关注</span>");
                    }
                    else {
                        spop({
                            template: '网络故障，请稍后再试',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }
    })

})