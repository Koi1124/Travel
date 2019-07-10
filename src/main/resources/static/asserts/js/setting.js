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
                        $('#user_logo').attr("src", dataURL);
                    }
                    else {
                        $('#user_logo').attr("src", src);
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
                }
                else {
                    $("#alert-box").text("非法的居住城市").show(200);
                }
            },
            error: function (e) {
                $("#alert-box").text("网络故障，请稍后再试").show();
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