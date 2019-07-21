//获取当前网址，如： http://localhost:80/ybzx/index.jsp  
var curPath=window.document.location.href;
//获取主机地址之后的目录，如： ybzx/index.jsp  
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:80  
var localhostPaht=curPath.substring(0,pos);

var tag=true;
var socket;
var origin=$(document).attr("title");
$(document).ready(function () {
    if (user_id!=null) {
        initSocket();
        initSocketHelper();
    }

    $("._user_manage").click(function () {
        $("#_j_user_panel").show(300);
    });
    $("._user_manage").blur(function () {
        $("#_j_user_panel").hide(300);
    })

});


function initSocket() {
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
            if (msg.data==user_id) {
                var num=$("._head_msg").text();
                var updateNum=Number(num)+1;
                changeTitle(updateNum);
                $("._head_msg").show();
            }
            if (Number(msg.data.split(",")[1])==user_id) {
                $("._head_private_letter").show();
                $(document).attr("title","[收到私信]"+origin);
            }
            if (Number(msg.data.split(",")[2])==user_id) {
                if (msg.data.split(",")[1]=="single") {
                    var num=$("._head_msg").text();
                    var updateNum=Number(num)-1;
                    changeTitle(updateNum)
                }else {
                    changeTitle(0);
                }
            }

        }

        ;
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
}

function initSocketHelper() {
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
            changeTitle(result);
        },
        error:function (e) {
            console.log(e);
        }
    });

    $.ajax({
        type: "post",
        url: "/letter/haveLetter",
        contentType: "application/json",
        dataType: "json",
        async: true,
        success:function (result) {
            if (result) {
                $("._head_private_letter").show();
                $(document).attr("title","[收到私信]"+origin);
            }
        },
        error:function (e) {
            console.log(e);
        }
    });
}

function changeTitle(data) {
    if (data==0){
        $(document).attr("title",origin);
        $("._head_msg").hide();
    } else {
        $(document).attr("title","["+data+"条新消息]"+origin);
        $("._head_msg").show();
    }
    $("._head_msg").text(data);
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
                    var jsonMsg={
                        type:0,
                        id:user_id
                    }
                    socket.send(JSON.stringify(jsonMsg));
                    $("#_j_msg_panel").hide(250);
                }
            },
            error:function (e) {
                console.log(e);
            }
        });
    }

    checkAll=function () {
        $.ajax({
            type: "post",
            url: localhostPaht + "/message/clearAll",
            contentType: "application/json",
            dataType: "json",
            async: true,
            data:JSON.stringify({
                user_id:user_id
            }),
            success:function (result) {
                if (result){
                    var jsonMsg={
                        type:1,
                        id:user_id
                    }
                    socket.send(JSON.stringify(jsonMsg));
                    $("#_j_msg_panel").hide(250);
                }
            },
            error:function (e) {
                console.log(e);
            }

        });
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
                        $("#_j_msg_panel ul").prepend('<li class=""><a href="'+ message.url +'" target="_self" onclick="checkout('+ message.mid +')" style="text-decoration: none; word-wrap: break-word;" >'+ message.msg +'</a></li>')
                        $("#_j_msg_panel").show(250);
                    });
                    $("#_j_msg_panel ul").append('<li class="" style="height: 12px"><a onclick="checkAll()" style="height: 12px;font-size: 10px;line-height: 12px;color: #666;position: relative;">全部标记为已读</a></li>');
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
    });

})

