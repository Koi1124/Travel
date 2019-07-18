//获取当前网址，如： http://localhost:80/ybzx/index.jsp  
var curPath=window.document.location.href;
//获取主机地址之后的目录，如： ybzx/index.jsp  
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:80  
var localhostPaht=curPath.substring(0,pos);

var tag=true;

console.log(user_id);
$(document).ready(function () {
    $.ajax({
        type: "post",
        url: localhostPaht+"/message/messageCount",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            user_id:user_id
        }),
        success:function (result) {
            $("._head_msg").text(result);
            if (result==0) {
                $("._head_msg").hide();
            }
        },
        error:function (e) {
            console.log(e);
        }
    });
});


var socket;
if(typeof(WebSocket) == "undefined") {
    console.log("您的浏览器不支持WebSocket");
}else{
    console.log("您的浏览器支持WebSocket");
    //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
    //等同于socket = new WebSocket("ws://localhost:8083/checkcentersys/websocket/20");
    socket = new WebSocket((localhostPaht+"/message").replace("http","ws"));
    //打开事件
    socket.onopen = function() {
        console.log("Socket 已打开");
        //socket.send("这是来自客户端的消息" + location.href + new Date());
    };
//获得消息事件
    socket.onmessage = function(msg) {
        console.log(msg.data);
        console.log(user_id);
        if (msg.data==user_id)
        {
            var num=$("._head_msg").text();
            var updateNum=Number(num)+1;
            $("._head_msg").text(updateNum);
            var origin=$(document).attr("title");
            $(document).attr("title","["+updateNum+"条新消息]"+origin);
        }
    };
    //关闭事件
    socket.onclose = function() {
        console.log("Socket已关闭");
    };
    //发生了错误事件
    socket.onerror = function() {
        alert("Socket发生了错误");
        //此时可以尝试刷新页面
    }
    //离开页面时，关闭socket
    //jquery1.8中已经被废弃，3.0中已经移除
    // $(window).unload(function(){
    //     socket.close();
    //});
}



$(function () {
    checkout=function(id) {
        $.ajax({
            type: "post",
            url: localhostPaht+"/message/checkout",
            contentType: "application/json",
            dataType: "json",
            async: true,
            data:JSON.stringify({
                msg_id:id
            }),
            success:function (result) {
                console.log(result);
                if (result) {
                    var num=$("._head_msg").text();
                    var updateNum=Number(num)-1;
                    $("._head_msg").text(updateNum);
                    $("#_j_msg_panel").hide(250);
                }
            },
            error:function (e) {
                console.log(e);
            }
        })


    }


    $("._drop_msg_detail").click(function () {
        $.ajax({
            type: "post",
            url: localhostPaht+"/message/detail",
            contentType: "application/json",
            dataType: "json",
            async: true,
            data:JSON.stringify({
                user_id:user_id
            }),
            success:function (result) {
                if (tag){
                    $(result).each(function (i,message) {
                        $("#_j_msg_panel ul").append('<li class=""><a href="'+ message.url +'" target="_blank" onclick="checkout('+ message.id +')" style="text-decoration: none;">'+ message.msg +'</a></li>')
                        $("#_j_msg_panel").show(250);
                    })
                    tag=false;
                }
            },
            error:function (e) {
                console.log(e);
            }
        })
    });
    $("._drop_msg_detail").blur(function () {
        setTimeout(function(){
            $("#_j_msg_panel").hide(250);
            $("#_j_msg_panel ul").html("");
        }, 250);
        tag=true;
    })
})

