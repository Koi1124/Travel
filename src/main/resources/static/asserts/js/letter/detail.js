//获取当前网址，如： http://localhost:80/ybzx/index.jsp  
var curPath=window.document.location.href;
//获取主机地址之后的目录，如： ybzx/index.jsp  
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:80  
var localhostPaht=curPath.substring(0,pos);

var socket;
if(typeof(WebSocket) == "undefined") {
    console.log("您的浏览器不支持WebSocket");
}else{
    console.log("您的浏览器支持WebSocket");
    $("#s_id").onload();
    console.log($("#s_id").val());
    //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
    //等同于socket = new WebSocket("ws://localhost:8083/checkcentersys/websocket/20");
    socket = new WebSocket((localhostPaht+"/socket/"+$("#s_id").val()).replace("http","ws"));
    //打开事件
    socket.onopen = function() {
        console.log("Socket 已打开");
        //socket.send("这是来自客户端的消息" + location.href + new Date());
    };
//获得消息事件
    socket.onmessage = function(msg) {
        console.log(msg.data);
        alert(msg.data);
        //发现消息进入    开始处理前端触发逻辑
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
    
    function send() {
        var message=document.getElementById("message").value;
        var To=document.getElementById("t_id").value;
        var jsonMsg={
            "message":message,
            "To":To
        }
        socket.send(JSON.stringify(jsonMsg));
        document.getElementById("_chat").innerHTML+=message;
    }
}
