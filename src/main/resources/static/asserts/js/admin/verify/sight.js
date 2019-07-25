$(function () {
    loadCity();

    $("#file").change(function () {
        var file = $("#file").prop('files')[0];
        var reader = new FileReader();
        //创建文件读取相关的变量
        var imgFile;
        //为文件读取成功设置事件
        reader.onload=function(e) {
            imgFile = e.target.result;
            $("#poi-pic").attr("src", imgFile);
        };
        reader.readAsDataURL(file);
    });
});

function clickSubmit() {
    var site = null;
    var phone = null;
    var time = null;
    if (testBlank($("#poi-name").val())) {
        logError("景点名不能为空");
        return;
    }
    if (testBlank($("#poi-hidden").val())) {
        logError("城市不能为空");
        return;
    }
    if (testBlank($("#poi-pic").attr("src"))) {
        logError("图片不能为空");
        return;
    }
    if (testBlank($("#poi-summary").val())) {
        logError("景点总结不能为空");
        return;
    }
    if (!testBlank($("#poi-site").val())) {
        site = $("#poi-site").val();
    }
    if (!testBlank($("#poi-phone").val())) {
        phone = $("#poi-phone").val();
    }
    if (!testBlank($("#poi-time").val())) {
        time = $("#poi-time").val();
    }
    if (testBlank($("#poi-traffic").val())) {
        logError("交通路线不能为空");
        return;
    }
    if (testBlank($("#poi-ticket").val())) {
        logError("门票不能为空");
        return;
    }
    if (testBlank($("#poi-openTime").val())) {
        logError("开放时间不能为空");
        return;
    }
    if (testBlank($("#poi-location").val())) {
        logError("位置不能为空");
        return;
    }
    if (testBlank($("#poi-longitude").val())) {
        logError("经度不能为空");
        return;
    }
    if (testBlank($("#poi-latitude").val())) {
        logError("纬度不能为空");
        return;
    }
    console.log(JSON.stringify({
        image:$("#poi-pic").attr("src"),
        name:$("#poi-name").val(),
        pid:$("#poi-hidden").val(),
        summary:$("#poi-summary").val(),
        site:site,
        phone:phone,
        time:time,
        traffic:$("#poi-traffic").val(),
        ticket:$("#poi-ticket").val(),
        openTime:$("#poi-openTime").val(),
        location:$("#poi-location").val(),
        longitude:$("#poi-longitude").val(),
        latitude:$("#poi-latitude").val(),
    }));
    var url = "/admin/addSight";
    var msg = "添加";
    if (sid != null) {
        msg = "修改";
        url = "/admin/updateSight";
    }
    $.ajax({
        url: url,
        type: "post",
        data:JSON.stringify({
            sid:sid,
            image:$("#poi-pic").attr("src"),
            name:$("#poi-name").val(),
            pid:$("#poi-hidden").val(),
            summary:$("#poi-summary").val(),
            site:site,
            phone:phone,
            time:time,
            traffic:$("#poi-traffic").val(),
            ticket:$("#poi-ticket").val(),
            openTime:$("#poi-openTime").val(),
            location:$("#poi-location").val(),
            longitude:$("#poi-longitude").val(),
            latitude:$("#poi-latitude").val(),
        }),
        dataType: "json",
        contentType: "application/json",
        async: true,
        success: function (data) {
            if (data) {
                logSuccess(msg + "成功，1秒后自动关闭");
                setTimeout(function () {
                    xadmin.close();
                    xadmin.father_reload();
                }, 1000);
            }
            else {
                logError("网络故障，请稍后再试");
            }
        },
        error: function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });

}

function loadCity() {
    $.ajax({
        url: "/together/mdd_info",
        type: "post",
        dataType: "json",
        async: true,
        success: function (data) {
            var tags = eval(data);
            $("#poi-pname").autocomplete({
                source:tags,
                message:{
                    results: function(){
                    },
                    noResults: ''
                },
                select:function (event,ui) {
                    $("#poi-hidden").val(ui.item.id);
                }
            });
        },
        error: function (e) {
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