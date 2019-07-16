var itemL = '<div class="item-time">'+ curentTime() +'</div>' +
    '       <div class="item item-left">\n' +
    '            <div class="img-head"><img src="'+ toClientPic +'" alt="头像"></div>\n' +
    '            <div class="content">\n' +
    '                CONTENT-LEFT\n' +
    '            </div>\n' +
    '        </div>';
var itemR = '<div class="item-time">'+ curentTime() +'</div>' +
    '           <div class="item item-right">\n' +
    '            <div class="img-head"><img src="'+ user_logo +'" alt="头像"></div>\n' +
    '            <div class="content">\n' +
    '                CONTENT-RIGHT\n' +
    '            </div>\n' +
    '        </div>';
var voiceTextDiv = null;
var ws = null;
//获取当前网址，如： http://localhost:80/ybzx/index.jsp  
var curPath=window.document.location.href;
//获取主机地址之后的目录，如： ybzx/index.jsp  
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:80  
var localhostPaht=curPath.substring(0,pos);

var wsUrl = (localhostPaht+"/socket/"+user_id).replace("http","ws");//websocket服务端地址


$(function () {
    //转文本框
    voiceTextDiv = document.getElementById("voiceText");


    //连接websocket 对接时用
    if ('WebSocket' in window) {
        ws = new WebSocket(wsUrl);
    } else {
        alert('当前浏览器 Not support WebSocket');
    }
    //连接发生错误的回调方法
    ws.onerror = function () {
        console.log("Socket发生了错误");
    };
    //连接成功建立的回调方法
    ws.onopen = function () {
        console.log("Socket 已打开");
    };
    //接收到消息的回调方法
    ws.onmessage = function (msg) {
        var info=msg.data.split(",");
        output('left',info[0]);
    };
    //连接关闭的回调方法
    ws.onclose = function () {
        console.log("Socket已关闭");
    };
    //监听窗口关闭事件，当窗口关闭时，主动去关闭ws连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    };

    //关闭WebSocket连接
    function closeWebSocket() {
        ws.close();
    }
});

// 得出现在时间并格式化
function curentTime()
{
    var now = new Date();

    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日

    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();           //秒

    var clock = year + "-";

    if(month < 10)
        clock += "0";

    clock += month + "-";

    if(day < 10)
        clock += "0";

    clock += day + " ";

    if(hh < 10)
        clock += "0";

    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";

    if (ss < 10) clock += '0';
    clock += ss;
    return(clock);
}


$(function () {
    $("._j_send_msg").click(function () {
        var message=$("#send_message").val();
        var jsonMsg={
            "message":message,
            "To":toClientId
        }
        ws.send(JSON.stringify(jsonMsg));
        output('right',message);
        $("#send_message").val("");
    })
})


//输出文本
function output(type,content) {
    var html;
    if (type == 'left') {
        html = itemL.replace("CONTENT-LEFT", content);
    } else if (type == 'right') {
        html = itemR.replace("CONTENT-RIGHT", content);
    }
    var item = $(html);
    $("#voiceText").append(item);
    // $(item).hide();
    // $(item).show(250);
    voiceTextDiv.scrollTop = voiceTextDiv.scrollHeight;
}