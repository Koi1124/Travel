var joinUrl = "/together/join";
var gender = "1";


$(function () {

    if (user==rUserId){
        $(".invisible").text(telephone);
    }


    $(".radio").click(function () {
        $(this).closest(".gender").find(".radio").attr("class", "radio _j_add_gender");
        $(this).attr("class", "radio on _j_add_gender");
        gender = $(this).attr("data-gender");
    })


    //判断用户之前有没有提交过加入信息
    $.ajax({
        type: "post",
        url: "/together/check_join",
        contentType: "application/json",
        data:JSON.stringify({
            uid:$("#user_id").val(),
            tid:pid
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            if (result != null) {
                $(".invisible").text(telephone);
                $("._j_add_phone").attr("value", result.phone);
                $("._j_together_men").attr("value", result.count);
                $("._j_together_names").attr("value", result.list);
                $("._j_together_remark").attr("value", result.addition);
                if (result.sex == "2") $(".radio[data-gender='2']").click();
                $("#join_submit").text("修改信息");
                if (result.status == "1") {
                    $("#join_submit").unbind();
                    $("#join_submit").click(function () {
                        logError("请联系发起人以修改信息");
                    });
                }
                else {
                    if (result.status == "2") $("#join_submit").text("重新报名");
                    joinUrl = "/together/join_update";
                }
            }
        },
        error: function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });
});

function clickSubmit() {
    if ($("._j_add_phone").val() == "") {
        logError("联系方式不能为空！");
        return;
    }
    if ($("._j_together_men").val() == "") {
        $("._j_together_men").attr("value", "0");
    }
    else if (!IsNum($("._j_together_men").val())) {
        logError("请输入正确的同行人数！");
        return
    }
    var data = {
        tid: pid,
        phone: $("._j_add_phone").val(),
        count: $("._j_together_men").val(),
        list: $("._j_together_names").val(),
        addition: $("._j_together_remark").val(),
        sex: gender,
        uid: userId,
        rUserId: rUserId,
        title: title
    };

    // 报名表信息
    $.ajax({
        type: "post",
        url: joinUrl,
        contentType: "application/json",
        data:JSON.stringify(data),
        dataType: "json",
        async: true,
        success: function (result) {
            if (result) {
                joinUrl = "/together/join_update";
                $("#join_submit").text("修改信息");
                logSuccess("提交成功");
            }
            else {
                logError("网络故障");
            }
        },
        error: function (e) {
            logError("网络故障，请稍后再试");
            console.log(e);
        }
    });

}

//判断是否为数字
function IsNum(s) {
    if (s!=null && s!="")
    {
        return !isNaN(s);
    }
    return false;
}

function login(e) {
    $(e).click(function () {
        logError("请先登录");
    })
}

function initClick() {

    if (user!=null){
        initLetter();
        initCollect();
        initFollow();
        initApp();
    }else {
        login(".btn-privateLetter");
        login(".btn-collect");
        login(".btn-follow");
        login("#join_submit");
    }
}
function initLetter() {
    // 私信点击事件
    $(".btn-privateLetter").click(function () {
        var id=$(".btn-privateLetter").attr("data-to_uid");
        id=Number(id);
        var url="/letter/detail/"+id;
        window.location.replace(url);
    });
}

function initCollect() {
    // 结伴关注点击事件
    $(".btn-collect").click(function () {
        var collectNum=$(".total span:eq(2)").text();
        var userId=$("#user_id").val();
        var data={
            userId:userId,
            collectId:pid,
            rUserId:rUserId,
            title:title,
            type:"5"
        };
        if ($(this).attr("class") == "btn-collect _j_together_care") {
            $.ajax({
                type: "post",
                url: "/collect",
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                async: true,
                success:function (result) {
                    if (result) {
                        spop({
                            template: '成功关注',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $(".cell-collect span").text("已关注结伴");
                        $(".btn-collect").find("i").animate({'margin-bottom':"5px"}, function () {
                            $(".btn-collect").find("i").animate({'margin-bottom':"0px"});
                        });
                        $(".btn-collect").attr("class","btn-collect on");
                        $(".total span:eq(2)").text(Number(collectNum)+1);
                    }
                    else {
                        spop({
                            template: '网络故障，请稍后再试',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error: function (e) {
                    console.log(e);
                    spop({
                        template: '网络故障，请稍后再试',
                        position  : 'top-center',
                        style: 'error',
                        autoclose: 1500
                    });
                }
            });
        }
        else {
            $.ajax({
                type: "post",
                url: "/uncollect",
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                async: true,
                success:function (result) {
                    if (result) {
                        spop({
                            template: '成功取消关注',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $(".cell-collect span").text("关注此结伴");
                        $(".btn-collect").find("i").animate({'margin-bottom':"5px"}, function () {
                            $(".btn-collect").find("i").animate({'margin-bottom':"0px"});
                        });
                        $(".btn-collect").attr("class","btn-collect _j_together_care");
                        $(".total span:eq(2)").text(Number(collectNum)-1);
                    }
                    else {
                        spop({
                            template: '网络故障，请稍后再试',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    });
}

function initFollow() {
    // 用户关注点击事件
    $(".btn-follow").click(function () {
        var isFollow=$(".btn-follow span:eq(0)").text();
        var data;
        data={
            userId:rUserId,
            followerId:user
        }
        console.log(data);
        if (isFollow=="加关注") {
            $.ajax({
                type: "post",
                url: "/follow",
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                async: true,
                success:function (result) {
                    if (result) {
                        spop({
                            template: '成功关注',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $(".follow").remove();
                        $(".btn-follow").append("<span class=\"followed\">已关注</span>");
                        $(".btn-follow").append("<span class=\"unfollow\">取消关注</span>");
                    }
                    else {
                        spop({
                            template: '网络故障，请稍后再试',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }
        else {
            $.ajax({
                type: "post",
                url: "/unfollow",
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: "json",
                async: true,
                success:function (result) {
                    if (result) {
                        spop({
                            template: '取消关注',
                            position  : 'top-center',
                            style: 'success',
                            autoclose: 1500
                        });
                        $(".followed").remove();
                        $(".unfollow").remove();
                        $(".btn-follow").append("<span class=\"follow\"><i></i>加关注</span>");
                    }
                    else {
                        spop({
                            template: '网络故障，请稍后再试',
                            position  : 'top-center',
                            style: 'error',
                            autoclose: 1500
                        });
                    }
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }
    })
}

function initApp() {
    $("#join_submit").click(function () {
        clickSubmit();
    });
}

$(document).ready(function () {
    $(".cell-join").click(function () {
        var scroll_offset = $(".mod-joinform").offset(); //得到pos这个div层的offset，包含两个值，top和left
        $("body,html").animate({
            scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
        },400);
    });

    initClick();



    function doneOperate(aid) {
        $(".bd").each(function () {
            if ($(this).attr("data-id")==aid)
            {
                $(this).hide(300);
            }
        })
    }


    // 同意申请
    pass=function (aid,uid) {
        $.ajax({
            type: "post",
            url: "/together/passApp",
            contentType: "application/json",
            dataType: "json",
            async: true,
            data:JSON.stringify({
                rUserId:uid,
                pid:together_id,
                title:title,
                aid: aid
            }),
            success:function (result) {
                if (result!=null){
                    spop({
                        template: '已同意申请',
                        position  : 'top-center',
                        style: 'success',
                        autoclose: 1500
                    });
                    $(".bd").each(function () {
                        if ($(this).attr("data-id")==aid)
                        {
                            $(this).find(".item").last().hide(300);
                        }
                    });
                    var origin_num=$("#_apply_num em").text();
                    var update_num=Number(origin_num)+1;
                    $("#_apply_num em").text(update_num);
                    $("#_apply_user_list").append('<li>\n' +
                        '                                <a class="avatar" href="#" target="_blank"><img src="'+ result.pic +'" style="width: 68px;height: 68px;"></a>\n' +
                        '                                <a class="name" href="#" target="_blank"><p>'+ result.name +'</p></a>\n' +
                        '                          </li>');
                }
                else {
                    spop({
                        template: '网络故障，请稍后再试',
                        position  : 'top-center',
                        style: 'error',
                        autoclose: 1500
                    });
                }
            },
            error:function (e) {
                console.log(e);
                spop({
                    template: '网络故障，请稍后再试',
                    position  : 'top-center',
                    style: 'error',
                    autoclose: 1500
                });
            }
        })
    }

    // 拒绝申请
    reject=function (aid,uid) {
        $.ajax({
            type: "post",
            url: "/together/rejectApp",
            contentType: "application/json",
            dataType: "json",
            async: true,
            data:JSON.stringify({
                rUserId:uid,
                pid:together_id,
                title:title,
                aid: aid
            }),
            success:function (result) {
                if (result){
                    spop({
                        template: '已拒绝申请',
                        position  : 'top-center',
                        style: 'success',
                        autoclose: 1500
                    });
                    doneOperate(aid);
                } else {
                    spop({
                        template: '网络故障，请稍后再试',
                        position  : 'top-center',
                        style: 'error',
                        autoclose: 1500
                    });
                }
            },
            error:function (e) {
                console.log(e);
                spop({
                    template: '网络故障，请稍后再试',
                    position  : 'top-center',
                    style: 'error',
                    autoclose: 1500
                });
            }
        });
    }



})




//关注结伴
function star(data) {

    $.ajax({
        type: "post",
        url: "/collect",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        async: true,
        success:function (result) {
            if (result) {
                spop({
                    template: '成功关注',
                    position  : 'top-center',
                    style: 'success',
                    autoclose: 1500
                });
                $(".cell-collect span").text("已关注结伴");
                $(".btn-collect").find("i").animate({'margin-bottom':"5px"}, function () {
                    $(".btn-collect").find("i").animate({'margin-bottom':"0px"});
                });
                $(".btn-collect").attr("class","btn-collect on");
                $(".total span:eq(2)").text(Number(collectNum)+1);
            }
            else {
                spop({
                    template: '网络故障，请稍后再试',
                    position  : 'top-center',
                    style: 'error',
                    autoclose: 1500
                });
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}

//取消关注
function starClear(data) {
    $.ajax({
        type: "post",
        url: "/uncollect",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "json",
        async: true,
        success:function (result) {
            if (result) {
                spop({
                    template: '成功取消关注',
                    position  : 'top-center',
                    style: 'success',
                    autoclose: 1500
                });
                $(".cell-collect span").text("关注此结伴");
                $(".btn-collect").find("i").animate({'margin-bottom':"5px"}, function () {
                    $(".btn-collect").find("i").animate({'margin-bottom':"0px"});
                });
                $(".btn-collect").attr("class","btn-collect _j_together_care");
                $(".total span:eq(2)").text(Number(collectNum)-1);
            }
            else {
                spop({
                    template: '网络故障，请稍后再试',
                    position  : 'top-center',
                    style: 'error',
                    autoclose: 1500
                });
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}
