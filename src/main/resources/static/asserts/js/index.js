//注册登录页面
var type = "1";
var path = "/user";

$(document).ready(function() {
    var name = $("#name");
    var mail = $("#mail");
    var btnICode = $("#btn-i-code");
    btnICode.attr("disabled", true);
    var pass = $("#password");
    var passRepeat = $("#password-repeat");
    var btnResgister = $("#register");

    var illegal = [1, 1, 1, 1];//合法性检测


    if(window.location.hash =="#1") {
        $("title").text("注册");
        turnToRegister();
    }
    if (window.location.pathname.substr(window.location.pathname.lastIndexOf("/") + 1) == "agency") {
        $("title").text("旅行社登录");
        $("#name").hide();
        path = "/agency";
        type = "2";
        illegal[0] = 0;
    }


    name.blur(function () {
        if (name.val().length < 4 || name.val().length > 20) {
            setText($("#name-tip"), "用户名应该为4~20位");
            illegal[0] = 1;
        }
        else {
            setText($("#name-tip"), "");
            illegal[0] = 0;
        }
    });

    //mail输入框失焦事件
    mail.blur(function () {
        if (mail.val().indexOf('@') < 0) {
            setText($("#email-tip"), "请输入正确的邮箱格式！");
            btnICode.attr("disabled", true);
            illegal[1] = 1;
        }
        else {
            $.ajax({
                type: "post",
                url: path + "/register/mail_check",
                data: JSON.stringify({mail:mail.val()}),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        setText($("#email-tip"), "该邮箱已被使用，请前往登录页面！");
                        btnICode.attr("disabled", true);
                        illegal[1] = 1;
                    }
                    else {
                        setText($("#email-tip"), "");
                        btnICode.attr("disabled", false);
                        illegal[1] = 0;
                    }
                },
                error: function (e) {
                    console.log(e);
                    setText($("#email-tip"), "提示：网络故障");
                    illegal[1] = 1;
                }
            });
        }
    });

    pass.blur(function () {
        if (pass.val().length < 8 || pass.val().length > 18) {
            setText($("#password-tip"), "密码应该为8~18位");
            illegal[2] = 1;
        }
        else if (containChinese(pass.val())) {
            setText($("#password-tip"), "密码只能为英文字母、标点或数字");
            illegal[2] = 1;
        }
        else {
            setText($("#password-tip"), "");
            illegal[2] = 0;
        }

        if (passRepeat.val() != "") {
            if (pass.val() === passRepeat.val()) {
                setText($("#password-repeat-tip"), "");
                illegal[3] = 0;
            }
            else {
                setText($("#password-repeat-tip"), "两次密码输入不同！");
                illegal[3] = 1;
            }
        }
    });

    passRepeat.blur(function () {
        if (pass.val() === passRepeat.val()) {
            setText($("#password-repeat-tip"), "");
            illegal[3] = 0;
        }
        else {
            setText($("#password-repeat-tip"), "两次密码输入不同！");
            illegal[3] = 1;
        }
    });

    //获取验证码按钮点击事件
    btnICode.click(function () {
        $.ajax({
            type: "post",
            url: "/get_identify_code",
            data: JSON.stringify({
                mail:mail.val(),
                type:type
            }),
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
                password: pass.val(),
                type:type
            };
            $.ajax({
                type: "post",
                url: path + "/register",
                data: JSON.stringify(msg),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    console.log(result);
                    if (result) {
                        setText($("#i-code-tip"), "");
                        login(mail.val(), pass.val());
                    }
                    else {
                        setText($("#i-code-tip"), "验证码错误！");
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
            setText($("#email-login-tip"), "请输入正确的邮箱格式");
        }
        else {
            $.ajax({
                type: "post",
                url: path + "/register/mail_check",
                data: JSON.stringify({mail:mailLogin.val()}),
                contentType: "application/json",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result) {
                        setText($("#email-login-tip"), "");
                    } else {
                        setText($("#email-login-tip"), "用户不存在");
                    }
                },
                error: function (e) {
                    console.log(e);
                    setText($("#email-login-tip"), "提示：网络故障");
                }
            });
        }
    });

    $("#btn-login").click(function () {
        login(mailLogin.val(), passLogin.val());
    });


    var mailForget = $("#mail-forget");

    $("#btn-forget").click(function () {
        $.ajax({
            type: "post",
            url: path + "/forget",
            data: JSON.stringify({mail:mailForget.val()}),
            contentType: "application/json",
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    turnToLogin();
                    mailLogin.val(mailForget.val());
                }
                else {
                    alert("网络故障");
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    });
});


function login(mail, pass) {
    $.ajax({
        type: "post",
        url: path + "/login",
        data: JSON.stringify({
            mail:mail,
            password:pass
        }),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            if (result == "300") {
                setText($("#email-login-tip"), "用户名不存在");
            }
            else if (result == "400") {
                setText($("#password-login-tip"), "密码错误");
            }
            else if (result == "200") {
                window.location.href = "/together";
            }
        },
        error: function (e) {
            console.log(e);
            $("#email-login-tip").text("提示：网络故障");
        }
    });
}

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


function turnToLogin() {
    $("#_j_signup_box").hide();
    $("#_j_forget_box").hide();
    $("#_j_login_box").show();
}

function turnToRegister() {
    $("#_j_signup_box").show();
    $("#_j_login_box").hide();
}

function turnToForget() {
    $("#_j_forget_box").show();
    $("#_j_login_box").hide();
}

function setText(input, text) {
    if (text === "") {
        $(input).hide();
    }
    else {
        $(input).text(text);
        $(input).show();
    }
}