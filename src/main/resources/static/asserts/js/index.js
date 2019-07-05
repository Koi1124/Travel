//注册登录页面
$(document).ready(function() {
    var name = $("#name");
    var mail = $("#mail");
    var btnICode = $("#btn-i-code");
    btnICode.attr("disabled", true);
    var pass = $("#password");
    var passRepeat = $("#password-repeat");
    var btnResgister = $("#test");

    var illegal = [1, 1, 1, 1];//合法性检测


    name.blur(function () {
        if (name.val().length < 4 || name.val().length > 20) {
            $("#name-tip").text("用户名应该为4~20位");
            illegal[0] = 1;
        }
        else {
            $("#name-tip").text("");
            illegal[0] = 0;
        }
    });

    //mail输入框失焦事件
    mail.blur(function () {
        if (mail.val().indexOf('@') < 0) {
            $("#email-tip").text("请输入正确的邮箱格式！");
            btnICode.attr("disabled", true);
            illegal[1] = 1;
        }
        else {
            $.ajax({
                type: "post",
                url: "http://localhost:8080/register/mail_check",
                data: JSON.stringify({mail:mail.val()}),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        $("#email-tip").text("该邮箱已被使用，请前往登录页面！");
                        btnICode.attr("disabled", true);
                        illegal[1] = 1;
                    }
                    else {
                        $("#email-tip").text("");
                        btnICode.attr("disabled", false);
                        illegal[1] = 0;
                    }
                },
                error: function (e) {
                    console.log(e);
                    $("#email-tip").text("提示：网络故障");
                    illegal[1] = 1;
                }
            });
        }
    });

    pass.blur(function () {
        if (pass.val().length < 8 || pass.val().length > 18) {
            $("#password-tip").text("密码应该为8~18位");
            illegal[2] = 1;
        }
        else if (containChinese(pass.val())) {
            $("#password-tip").text("密码只能为英文字母、标点或数字");
            illegal[2] = 1;
        }
        else {
            $("#password-tip").text("");
            illegal[2] = 0;
        }

        if (passRepeat.val() != "") {
            if (pass.val() === passRepeat.val()) {
                $("#password-repeat-tip").text("");
                illegal[3] = 0;
            }
            else {
                $("#password-repeat-tip").text("两次密码输入不同！");
                illegal[3] = 1;
            }
        }
    });

    passRepeat.blur(function () {
        if (pass.val() === passRepeat.val()) {
            $("#password-repeat-tip").text("");
            illegal[3] = 0;
        }
        else {
            $("#password-repeat-tip").text("两次密码输入不同！");
            illegal[3] = 1;
        }
    });

    //获取验证码按钮点击事件
    btnICode.click(function () {
        console.log("click");
        $.ajax({
            type: "post",
            url: "http://localhost:8080//register/get_identify_code",
            data: JSON.stringify({mail:mail.val()}),
            contentType: "application/json",
            dataType: "json",
            async: true,
            success: function (data) {
                waitTime(btnICode);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    //注册按钮点击事件
    btnResgister.click(function () {
        if (illegal[0] + illegal[1] + illegal[2] + illegal[3] === 0) {
            var msg = {
                mail: mail.val(),
                identifyCode: $("#identify-code").val(),
                name: name.val(),
                password: pass.val()
            };
            $.ajax({
                type: "post",
                url: "http://localhost:8080/register",
                data: JSON.stringify(msg),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        $("#i-code-tip").text("");
                        alert("注册成功");
                    }
                    else {
                        $("#i-code-tip").text("验证码错误！");
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    });


    //登录邮箱失焦时事件
    var mailLogin = $("#mail-login");
    var passLogin = $("#password-login");

    mailLogin.blur(function () {
        if (mailLogin.val().indexOf('@') < 0) {
            $("#email-login-tip").text("请输入正确的邮箱格式！");
        }
        else {
            $.ajax({
                type: "post",
                url: "http://localhost:8080/register/mail_check",
                data: JSON.stringify({mail:mailLogin.val()}),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        $("#email-login-tip").text("");
                    }
                    else {
                        $("#email-login-tip").text("用户不存在");
                    }
                },
                error: function (e) {
                    console.log(e);
                    $("#email-login-tip").text("提示：网络故障");
                }
            });
        }
    });

    $("#btn-login").click(function () {
        $.ajax({
            type: "post",
            url: "http://localhost:8080/login",
            data: JSON.stringify({
                mail:mailLogin.val(),
                password:passLogin.val()
            }),
            contentType: "application/json",
            dataType: "json",
            async: true,
            success: function (result) {
                if (result == "200") {
                    alert("登录成功");
                }
                else if (result == "300") {
                    alert("用户名不存在");
                }
                else if (result == "400") {
                    alert("密码错误");
                }
            },
            error: function (e) {
                console.log(e);
                $("#email-login-tip").text("提示：网络故障");
            }
        });
    });
});


/**
 * 获取验证码按钮设置等待时间
 */
var wait = 60;
function waitTime(o) {
    if (wait === 0) {
        o.attr("disabled", false);
        o.text("获取验证码");
        wait = 60;
    } else {
        o.attr("disabled", true);
        o.text(wait + "秒后重新发送");
        wait--;
        setTimeout(function () {waitTime(o);},1000);
    }
}


//检测是否含有中文
function containChinese(val){
    var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    if(reg.test(val)){
        return true;
    }
    return false;
}