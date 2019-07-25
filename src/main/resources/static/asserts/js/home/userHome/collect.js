$(function () {
    deleteCollect=function (cid) {
        $.ajax({
            type: "post",
            url: "/deleteCollect",
            contentType: "application/json",
            data:JSON.stringify({
                collectId:cid
            }),
            dataType: "json",
            async: true,
            success:function (result) {
                if (result){
                    spop({
                        template: '已取消收藏',
                        position  : 'top-center',
                        style: 'success',
                        autoclose: 1500
                    });
                    $("._collect_item").each(function () {
                        if ($(this).attr("data-id")==cid) {
                            var e = $(this);
                            $(this).hide(300, function () {
                                e.remove();
                            });
                        }
                    })
                }else {
                    spop({
                        template: '不存在此收藏或已取消！',
                        position  : 'top-center',
                        style: 'error',
                        autoclose: 1500
                    });
                }
            },
            error:function (e) {
                spop({
                    template: '网络故障，请稍后再试！',
                    position  : 'top-center',
                    style: 'error',
                    autoclose: 1500
                });
                console.log(e);
            }
        })
    }

})