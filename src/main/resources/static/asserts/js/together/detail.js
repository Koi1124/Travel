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