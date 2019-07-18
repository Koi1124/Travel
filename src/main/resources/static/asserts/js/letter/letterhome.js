$(function () {
    // 只查看未读信息
    $("#_check_unread").click(function () {
        if ($(this).attr("data-type")==0){
            $(this).attr("data-type",1);
            $(this).addClass("on");
            $(".item").each(function () {
                if ($(this).attr("data-state")==1){
                    $(this).hide(400);
                }
            })
        }else {
            $(this).attr("data-type",0);
            $(this).removeClass("on");
            $(".item").each(function () {
                if ($(this).attr("data-state")==1){
                    $(this).show(400);
                }
            })
        }
    });

    // 移除聊天栏
    removeChat=function (letter_id) {
        $.ajax({
            type: "post",
            url: localhostPaht+"/letter/removeChatBar",
            contentType: "application/json",
            dataType: "json",
            async: true,
            data:JSON.stringify({
                letter_id:letter_id
            }),
            success:function (result) {
                if (result) {
                    $(".item").each(function () {
                        if ($(this).attr("data-item")==letter_id){
                            $(this).hide(400);
                        }
                    })
                }
            },
            error:function (e) {
                console.log(e);
            }
        })
    };

    read=function (letter_id) {
        $.ajax({
            type: "post",
            url: localhostPaht+"/letter/readLetter",
            contentType: "application/json",
            dataType: "json",
            async: true,
            data:JSON.stringify({
                letter_id:letter_id
            }),
            success:function (result) {
                if (result){
                    console.log("已读成功");
                }else {
                    console.log("已读失败");
                }
            },
            error:function (e) {
                console.log(e);
            }
        });
    }
})