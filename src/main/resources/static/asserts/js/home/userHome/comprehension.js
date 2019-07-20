$(function () {

    $("#_follow_user").click(function () {
        if ($(this).attr("hasFollowed")=='0') {
            $.ajax({
                type: "post",
                url: "/follow",
                contentType: "application/json",
                dataType: "json",
                async: true,
                data:JSON.stringify({
                    userId:uid,
                    followerId:myId
                }),
                success:function (result) {
                    if (result) {
                        spop({
                            template: '关注成功',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $("#_follow_user").addClass("MAvaAttentioned");
                        $("#_follow_user").attr("hasFollowed","1");
                    }else {
                        spop({
                            template: '已关注用户或用户不存在',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error:function (e) {
                    console.log(e);
                    spop({
                        template: '网络故障，请稍后再试！',
                        position  : 'top-center',
                        style: 'error',
                        autoclose: 1500
                    });
                }
            })
        }else {
            $.ajax({
                type: "post",
                url: "/unfollow",
                contentType: "application/json",
                dataType: "json",
                async: true,
                data:JSON.stringify({
                    userId:uid,
                    followerId:myId
                }),
                success:function (result) {
                    if (result) {
                        spop({
                            template: '关注取消',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $("#_follow_user").removeClass("MAvaAttentioned");
                        $("#_follow_user").attr("hasFollowed","0")
                    }else {
                        spop({
                            template: '已取消关注用户或用户不存在',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error:function (e) {
                    console.log(e);
                    spop({
                        template: '网络故障，请稍后再试！',
                        position  : 'top-center',
                        style: 'error',
                        autoclose: 1500
                    });
                }
            })
        }
    })




    $("._collect_choose").click(function () {
        $(".dropdown-collect").show(200);
        console.log($(this).is(":focus"));
    })

    $("._collect_choose").blur(function () {
        setTimeout(function(){
            $(".dropdown-collect").hide(200);
        }, 250);
    })
})