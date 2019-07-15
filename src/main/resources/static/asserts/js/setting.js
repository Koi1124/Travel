var obUrl = '';
$(document).ready(function(){
    var src = "";
    $("#btn-logo").click(function(){
        $(".clip-mask").show();
    });
    $("#clipBtn").click(function(){
        $(".clip-mask").hide();
        src = $('#user_logo').attr("src");
        $("#user_logo").attr("src", "/asserts/img/upload/loading.gif")
    });
    $("#clipArea").photoClip({
        width: 300,
        height: 300,
        file: "#file",
        view: "#view",
        ok: "#clipBtn",
        loadStart: function() {
            console.log("照片读取中");
        },
        loadComplete: function() {
            console.log("照片读取完成");
        },
        clipFinish: function(dataURL) {
            console.log("finish");
            $.ajax({
                type: "post",
                url: "http://localhost:8080/change_logo",
                data: JSON.stringify({
                    image:dataURL
                }),
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    if (result) {
                        $("#alert-box").hide(200);
                        $('#user_logo').attr("src", dataURL);
                        spop({
                            template: '头像更换成功',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                    }
                    else {
                        $('#user_logo').attr("src", src);
                        $("#alert-box").text("网络故障，请稍后再试").show(200);
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    });
});

function infoSubmit() {
    if ($("#input-name").val().length < 4) {
        $("#alert-box").text("用户名应为4~20位！").show(200);
        return;
    }
    else {
        $("#alert-box").hide(200);
        $.ajax({
            type: "post",
            url: "http://localhost:8080/change_info",
            data: JSON.stringify({
                name:$("#input-name").val(),
                sex:$("input:radio:checked").val(),
                address:$("#input-address-name").val(),
                addtion:$("#input-addtion").val()
            }),
            contentType: "application/json",
            dataType: "json",
            success: function (result) {
                if (result) {
                    $("#alert-box").hide(200);
                    spop({
                        template: '信息修改成功',
                        position  : 'top-center',
                        style: 'success',
                        autoclose: 1500
                    });
                }
                else {
                    $("#alert-box").text("非法的居住城市").show(200);
                }
            },
            error: function (e) {
                $("#alert-box").text("网络故障，请稍后再试").show(200);
                console.log(e);
            }
        });
    }
}

function passwordSubmit() {
    if ($("#password-input").val().length < 8) {
        $("#alert-box").text("用户名应为8~18位！").show(200);
        return;
    }
    else if ($("#password-input").val() != $("#password-repeat").val()) {
        $("#alert-box").text("两次密码输入不一致！").show(200);
        return;
    }
    else {
        $("#alert-box").hide(200);
        $.ajax({
            type: "post",
            url: "http://localhost:8080/change_password",
            data: JSON.stringify({
                oldPassword:$("#password-old").val(),
                newPassword:$("#password-input").val()
            }),
            contentType: "application/json",
            dataType: "json",
            success: function (result) {
                if (result) {
                    $("#alert-box").hide(200);
                    $("#password-repeat").val("");
                    $("#password-input").val("");
                    $("#password-old").val("");
                    spop({
                        template: '密码修改成功',
                        position  : 'top-center',
                        style: 'success',
                        autoclose: 1500
                    });
                }
                else {
                    $("#alert-box").text("原密码错误").show(200);
                }
            },
            error: function (e) {
                $("#alert-box").text("网络故障，请稍后再试").show(200);
                console.log(e);
            }
        });
    }
}


//左侧三个按钮的点击事件
function infoClick(e) {
    if ($(e).attr("class") != "on") {
        $(".aside").children("a").attr("class", "default");
        $(e).attr("class", "on");

        $(".content").children("div").hide();
        $(".hd").html("<strong>我的信息</strong>").show();
        $("#user-info").show();
    }
}

function logoClick(e) {
    if ($(e).attr("class") != "on") {
        $(".aside").children("a").attr("class", "default");
        $(e).attr("class", "on");

        $(".content").children("div").hide();
        $(".hd").html("<strong>我的头像</strong>").show();
        $("#user-logo").show();
    }
}

function passwordClick(e) {
    if ($(e).attr("class") != "on") {
        $(".aside").children("a").attr("class", "default");
        $(e).attr("class", "on");

        $(".content").children("div").hide();
        $(".hd").html("<strong>更换密码</strong>").show();
        $("#user-password").show();
    }
}