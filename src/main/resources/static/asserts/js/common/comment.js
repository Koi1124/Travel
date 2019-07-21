var itemCount = 8;
var userId = -1;
var userName = "";
var userLogo = "";
var pid = 0;

$(function () {
    getComment(1);
    initPagination();
    if (type=="2"||type=="3") {
        rUserId=null;
    }

});

function getComment(page) {
    var path = window.location.pathname;
    pid = path.substr(path.lastIndexOf("/") + 1).split(".")[0];
    console.log(pid);
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json",
        data:JSON.stringify({
            start:(page - 1) * itemCount,
            pageCount:itemCount,
            type:type,
            id:pid,
            uid:$("#user_id").val()
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            userId = $("#user_id").val();
            userName = $("#user_name").val();
            userLogo = $("#user_pic").val();
            console.log(result);
            setComment(result);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

var replyStyle = 0;
var replyId = 0;
var replyName = 0;
var commentId = 0;
var r_content=null;
function clickReply(e) {
    var li = $(e).closest("li");
    var input = $(e).closest(".rev-item").find(".reply-form");

    if (li.attr("c_tag") == "comment") {
        //直接回复评论
        if (input.attr("reply") != "0") {
            input.find("textarea").attr("value", "");
            input.attr("reply", "0");
            input.find("textarea").attr("placeholder", "");
        }
        replyStyle = 0;
        commentId = li.attr("c_id");
        replyName = "";
        r_content = li.find("p").text();
    }
    else {
        //回复回复
        if (input.attr("reply") != li.attr("r_id")) {
            input.find("textarea").attr("value", "");
            input.attr("reply", li.attr("r_id"));
            input.find("textarea").attr("placeholder", "回复" + li.attr("u_name"));
        }
        replyStyle = 1;
        replyId = li.attr("u_id");
        commentId = li.attr("c_id");
        replyName = li.attr("u_name");
        r_content = li.find("haha").text().replace("：","");
    }
    input.show(200);
    input.find("textarea").focus();
}

//评论回复按钮
function clickReplySubmit(e) {
    if (user==null) {
        logError("请先登录");
        return;
    }


    //检测非空
    if ($(e).prev("textarea").val() == "") {
        logError("请输入内容");
        return;
    }

    var data;
    if (replyStyle == 0) {
        var temp=$(e).closest("li").attr("u_id");
        var rid=Number(temp);
        //回复评论
        data = {
            commentId:commentId,
            replyId:-1,
            rUserId: rid,
            content:$(e).prev("textarea").val(),
            userId:$("#user_id").val(),
            title:r_content,
            pid:pid,
            type:type
        };
    }
    else {
        //回复回复
        data = {
            commentId:commentId,
            replyId:replyId,
            rUserId:replyId,
            content:$(e).prev("textarea").val(),
            userId:$("#user_id").val(),
            title:r_content,
            pid:pid,
            type:type
        }
    }
    $.ajax({
        type: "post",
        url: "/addReply",
        contentType: "application/json",
        data:JSON.stringify(data),
        dataType: "json",
        async: true,
        success: function (result) {
            if (result != null) {
                var c = $(e).prev("textarea").attr("value", "");
                logSuccess("发表成功");
                //添加reply至页面
                var list = $(e).closest(".reply-form").prev();
                var str = "<li style=\"display:block\" u_id=" + $("#user_id").val() +
                    "          c_tag='reply' r_id=" + result + " c_id=" + commentId + " u_name=" + userName + ">" +
                    "    <a href=\"/u/"+ userId +"/note \" target=\"_blank\">" +
                    "        <img src=" + userLogo + " width=\"16\" height=\"16\">" + userName +
                    "    </a> ";
                if (replyName != "") {
                    str += " 回复 <a u_id=" + replyId +">" + replyName + "</a>"
                }
                str += "<haha> ：" + data.content+"</haha>\n"+
                    "    <a class=\"_j_reply re_reply\" onclick='clickReply(this)' title=\"添加回复\">回复</a>\n" +
                    "    <a class='_j_reply re_reply' style='color: red' onclick='clickDelete(this)' title='删除'>删除</a>\n" +
                    "    <br><span class=\"time\">刚刚</span>" +
                    "</li>";
                var replyLi = $(str);
                $(list).append(replyLi);
                replyLi.show(200);
            }
            else {
                logError('网络故障，请稍后再试');
            }

        },
        error: function (e) {
            console.log(e);
        }
    });
}

function clickDelete(e) {
    var li = $(e).closest("li");

    if (li.attr("c_tag") == "comment") {
        //删除评论
        $.ajax({
            type: "post",
            url: "/deleteComment",
            contentType: "application/json",
            data:JSON.stringify({
                id:li.attr("c_id")
            }),
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    logSuccess("删除成功");
                    li.hide(200);
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
    else {
        //删除回复
        $.ajax({
            type: "post",
            url: "/deleteReply",
            contentType: "application/json",
            data:JSON.stringify({
                id:li.attr("r_id")
            }),
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    logSuccess("删除成功");
                    li.hide(200, function () {
                        li.remove();
                    });
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
}

function clickThumbsUp(e) {
    if (user==null) {
        logError("请先登录");
        return;
    }
    var temp=$(e).closest("li").attr("u_id");
    var rid=Number(temp);
    var data = {
        pid: $(e).closest("li").attr("c_id"),
        rUserId: rid,
        title: $(e).closest("li").find("p").text(),
        jump_type: type,
        type: "3",
        uid: userId,
        jump_pid:pid
    };
    if ($(e).attr("class") == "useful") {
        console.log(data);
        $.ajax({
            type: "post",
            url: "/thumbsUp",
            contentType: "application/json",
            data:JSON.stringify(data),
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    logSuccess("点赞成功");
                    $(e).attr("class", "useful on");
                    var sp = $(e).find("span");
                    sp.text((sp.text() == "" ? 1 : parseInt(sp.text()) + 1));
                    sp.attr("style", "color:red");

                    $(e).find("i").animate({'margin-right':'10px'}, 100, function() {
                        $(e).find("i").animate({'margin-right': '5px'});
                    });
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
    else {
        $.ajax({
            type: "post",
            url: "/thumbsDown",
            contentType: "application/json",
            data:JSON.stringify(data),
            dataType: "json",
            async: true,
            success: function (result) {
                if (result) {
                    logSuccess("取消成功");
                    $(e).attr("class", "useful");
                    var sp = $(e).find("span");
                    sp.text((sp.text() == "1" ? "" : parseInt(sp.text()) - 1));
                    sp.attr("style", "");
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
}

function clickExtend(e) {
    if ($(e).attr("extend") == 1) {
        $(e).closest("li").find("li").hide(200);
        $(e).attr("extend", "0");
        $(e).text("展开");
    }
    else {
        $(e).closest("li").find("li").show(200);
        $(e).attr("extend", "1");
        $(e).text("收起");
    }
}

function clickCommentSubmit() {
    if (user==null) {
        logError("请先登录");
        return;
    }

    if ($("._j_comment_content").val() == "") {
        logError("请先填写留言内容");
        return;
    }

    $.ajax({
        type: "post",
        url: "/addComment",
        contentType: "application/json",
        data:JSON.stringify({
            userId:userId,
            type:type,
            pid:pid,
            title:title,
            rUserId:rUserId,
            content:$("._j_comment_content").val(),
            score:0
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            if (result != null) {
                logSuccess("发表成功");
                var c = $("<li class='rev-item comment-item clearfix' c_id=" + result + " c_tag='comment'" +
                    "           u_id=" + userId + " u_name=" + userName + " style='display: none'>" +
                    "    <div class='user'><a class='avatar' target='_blank' href='/u/"+ userId +"/note'>" +
                    "        <img src=" + userLogo + " width='48' height='48'></a></div>" +
                    "    <a class='useful' title='点赞' onclick='clickThumbsUp(this)'><i></i>" +
                    "        <span class='useful-num'></span></a>" +
                    "    <a class='name' target='_blank' href='/u/"+ userId +"/note'>" + userName + "</a>" +
                    "    <p class='rev-txt'>" + $("._j_comment_content").val() + "</p>" +
                    "    <div class='info clearfix'>" +
                    "        <a class='btn-comment _j_comment' onclick='clickReply(this)' title='评论'" +
                    "                 style='text-decoration:none;'>评论</a>" +
                    //删除按钮
                    "        <a class='btn-comment _j_comment' title='删除' onclick='clickDelete(this)' style='padding:0 5px;color:red'>删除</a>" +
                    "        <span class='time'>刚刚</span>" +
                    "    </div>" +
                    "    <div class='comment add-reply'>" +
                    "        <ul class='more_reply_box comment_list'>" +
                    "        </ul>" +
                    "        <div class='add-comment reply-form' reply='0' style='display: none'>" +
                    "            <textarea class='comment_reply' style='overflow: hidden; color: rgb(204, 204, 204);float: left;'></textarea>" +
                    "            <a class='btn btn_submit_reply' onclick='clickReplySubmit(this)' style='float: right;text-align:center;padding: 0;wid 15%;height: 25px;font-size: 14px;'>" +
                    "                回复" +
                    "            </a>" +
                    "        </div>" +
                    "    </div>" +
                    "</li>");
                $("._j_comment_content").attr("value", "");
                $("#comment-list").prepend(c);
                c.find(".comment_reply").blur(function () {
                    setTimeout(function () {
                        $(".reply-form").hide(200);
                    }, 100);
                })
                c.show(200);
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

function setComment(data) {
    $("#comment-list").empty();
    $(data).each(function (i, comment) {
        var c = $("<li class='rev-item comment-item clearfix' c_id=" + comment.remarkId + " c_tag='comment'" +
            "           u_id=" + comment.remarkerId + " u_name=" + comment.remarkerName + ">" +
            "    <div class='user'><a class='avatar' href='/u/"+ comment.remarkerId +"/note' target='_blank'>" +
            "        <img src=" + comment.remarkerPic + " width='48' height='48'></a></div>" +
            "    <a class='useful" + (comment.thumbsId==null?"":" on") + "' title='点赞' onclick='clickThumbsUp(this)'><i></i>" +
            "        <span class='useful-num' style='color:" + (comment.thumbsId==null?"#666":"red") + "'>" + (comment.thumbsUpCount==null?"":comment.thumbsUpCount) + "</span>" +
            "    </a>" +
            "    <a class='name' href='/u/"+ comment.remarkerId +"/note' target='_blank'>" + comment.remarkerName + "</a>" +
            "    <p class='rev-txt'>" + comment.content + "</p>" +
            "    <div class='info clearfix'>" +
            "        <a class='btn-comment _j_comment' onclick='clickReply(this)' title='评论'" +
            "                 style='text-decoration:none;'>评论</a>" +
            //展开\删除按钮
            "        <span class='time'>" + comment.time + "</span>" +
            "    </div>" +
            "    <div class='comment add-reply'>" +
            "        <ul class='more_reply_box comment_list'>" +
            //子评论
            "        </ul>" +
            "        <div class='add-comment reply-form' reply='0' style='display: none'>" +
            "            <textarea class='comment_reply' style='overflow: hidden; color: rgb(204, 204, 204);float: left;'></textarea>" +
            "            <a class='btn btn_submit_reply' onclick='clickReplySubmit(this)' style='float: right;text-align:center;padding: 0;wid 15%;height: 25px;font-size: 14px;'>" +
            "                回复" +
            "            </a>" +
            "        </div>" +
            "    </div>" +
            "</li>");
        if (comment.reply.length > 2) {
            $(c).find("._j_comment").after("<a class='btn-comment _j_comment' title='展开' onclick='clickExtend(this)' extend='0' style='padding:0 5px;'>展开剩下" +
                (comment.reply.length - 2) + "条</a>")
        }
        if (comment.remarkerId == userId) {
            $(c).find(".time").before("<a class='btn-comment _j_comment' title='删除' onclick='clickDelete(this)' style='padding:0 5px;color:red'>删除</a>");
        }
        $(comment.reply).each(function (j, r) {
            var str = "<li style='display:" + (j>1?"none":"block") + "' u_id=" + r.replierId +
                "          c_tag='reply' c_id=" + comment.remarkId + " u_name=" + r.replierName + " r_id=" + r.replyId +
                "    ><a onclick='' href='/u/"+ r.replierId +"/note' target='_blank'>" +
                "        <img src=" + r.replierPic + " width='16' height='16'>" + r.replierName +
                "    </a> ";
            if (r.respondedName != "") {
                str += " 回复 <a href='/u/"+ r.respondedId +"/note' u_id=" + r.respondedId +" >" + r.respondedName + "</a>"
            }
            str += "<haha> ：" + r.content + "</haha>" +
                "    <a class='_j_reply re_reply' onclick='clickReply(this)' title='添加回复'>回复</a>\n";
            if (r.replierId == userId) {
                str += "<a class='_j_reply re_reply' style='color: red;' onclick='clickDelete(this)' title='删除'>删除</a>\n";
            }
            str+= "<br><span class='time'>" + r.time + "</span></li>";
            $(c).find("ul").append(str);
        });
        $("#comment-list").append(c);
        $(".comment_reply").blur(function () {
            setTimeout(function () {
                $(".reply-form").hide(200);
            }, 100);
        })
    });
}

function initPagination() {
    $("#pagination").pagination({
        currentPage: 1,// 当前页数
        totalPage: Math.ceil($("#comment_count").val() / itemCount),// 总页数
        isShow: true,// 是否显示首尾页
        count: 5,// 显示个数
        homePageText: "首页",// 首页文本
        endPageText: "尾页",// 尾页文本
        prevPageText: "上一页",// 上一页文本
        nextPageText: "下一页",// 下一页文本
        callback: function(current) {
            // 回调,current(当前页数)
            getComment(current);

            var scroll_offset = $("#comment-list").offset(); //得到pos这个div层的offset，包含两个值，top和left
            $("body,html").animate({
                scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
            },200);
        }
    });
}

function logSuccess(msg) {
    spop({
        template: msg,
        position  : 'top-center',
        style: 'success',
        autoclose: 1500
    });
}

function logError(msg) {
    spop({
        template: msg,
        position  : 'top-center',
        style: 'errors',
        autoclose: 3000
    });
}