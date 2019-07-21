var statusChange = true;

function changeStatus(status)
{
    if (statusChange)
    $.ajax({
        type: "post",
        url: "http://localhost:8080/admin/ab05/changeState",
        data: JSON.stringify({state: status, id: together_id}),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (data) {
            logSuccess('审核成功,1秒后自动关闭窗口');
            statusChange = false;
            setTimeout(function () {
                xadmin.close();
                xadmin.father_reload();
            }, 1000);
        },
        error: function (e) {
            logError('审核失败！');
        }
    });
}