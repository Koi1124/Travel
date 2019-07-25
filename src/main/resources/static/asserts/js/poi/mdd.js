function searchMdd() {
    if (testBlank($("#poi-input").val()))
    {
        logError("请输入内容");
        return;
    }
    $.ajax({
        type: "post",
        url: "/mdd/search",
        contentType: "application/json",
        data:JSON.stringify({
            name:$("#poi-input").val()
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            if (result != null) {
               window.location.href = "/c/" + result;
            }
            else {
                logError("未找到目的地");
            }
        },
        error: function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });
}

function testBlank(str) {
    str = str.replace(/\s+/g, "");
    var reg = /\n/g;
    str = str.replace(reg, "");
    return str.length == 0;
}