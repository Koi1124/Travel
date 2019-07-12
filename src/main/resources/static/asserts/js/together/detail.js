
var page = 1;
var itemCount = 8;
$(function () {
    var path = window.location.pathname;
    path = path.substr(path.lastIndexOf("/")).split(".")[0] + "/comment";
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        data:JSON.stringify({
            start:(page - 1) * itemCount,
            pageCount:itemCount,
            type:5
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            setComment(result)
        },
        error: function (e) {
            console.log(e);
        }
    });
});

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

var replyStyle = 0;
var replyId = 0;
var replyName = 0;
var commentId = 0;
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
    }
    else {
        //回复回复
        if (input.attr("reply") != li.attr("index")) {
            input.find("textarea").attr("value", "");
            input.attr("reply", li.attr("index"));
            input.find("textarea").attr("placeholder", "回复" + li.attr("u_name"));
        }
        replyStyle = 1;
        replyId = li.attr("u_id");
        commentId = li.attr("c_id");
        replyName = li.attr("u_name");
    }
    input.show(200);
    input.find("textarea").focus();
}

//评论回复按钮
function clickReplySubmit(e) {
    //检测非空
    if ($(e).prev("textarea").val() == "") {
        return;
    }

    var data;
    if (replyStyle == 0) {
        //回复评论
        data = {
            commentId:commentId,
            replyId:-1,
            content:$(e).prev("textarea").val(),
            userId:$("#user_id").val()
        };
    }
    else {
        //回复回复
        data = {
            commentId:commentId,
            replyId:replyId,
            content:$(e).prev("textarea").val(),
            userId:$("#user_id").val()
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
            if (result) {
                var c = $(e).prev("textarea").attr("value", "");
                spop({
                    template: '发表成功',
                    position  : 'top-center',
                    style: 'success',
                    autoclose: 1500
                });
                //添加reply至页面
                var list = $(e).closest(".reply-form").prev();
                var j = list.children("li").length;
                var str = "<li style=\"display:block\" index=" + (j + 1) + " u_id=" + $("#user_id").val() +
                    "          c_tag='reply' c_id=" + commentId + " u_name=" + $("#user_name").val() + ">" +
                    "    <a onclick=\"\" target=\"_blank\">" +
                    "        <img src=" + $("#user_pic").val() + " width=\"16\" height=\"16\">" + $("#user_name").val() +
                    "    </a> ";
                if (replyName != "") {
                    str += " 回复 <a u_id=" + replyId +">" + replyName + "</a>"
                }
                str += " ：" + data.content +
                    "    <a class=\"_j_reply re_reply\" onclick='clickReply(this)' title=\"添加回复\">回复</a>\n" +
                    "    <br><span class=\"time\">刚刚</span>" +
                    "</li>";
                var replyLi = $(str);
                $(list).append(replyLi);
                replyLi.show(200);
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


function clickThumbsUp(e) {
    //TODO 点赞写入数据库
    if ($(e).attr("class") == "useful") {
        $(e).attr("class", "useful on");
        var sp = $(e).find("span");
        console.log(sp.text());
        sp.text((sp.text() == "" ? 1 : parseInt(sp.text()) + 1));
        sp.attr("style", "color:red");

        $(e).find("i").animate({'margin-right':'10px'}, 100, function() {
            $(e).find("i").animate({'margin-right': '5px'});
        });
    }
    else {
        $(e).attr("class", "useful");
        var sp = $(e).find("span");
        sp.text((sp.text() == "1" ? "" : parseInt(sp.text()) - 1));
        sp.attr("style", "");
    }
}

function setComment(data) {
    $(data).each(function (i, comment) {
        var c = $("<li class=\"rev-item comment-item clearfix\" c_id=" + comment.remarkId + " c_tag='comment'" +
            "           u_id=" + comment.remarkerId + " u_name=" + comment.remarkerName + ">" +
            "    <div class=\"user\"><a class=\"avatar\" target=\"_blank\">" +
            "        <img src=" + comment.remarkerPic + " width=\"48\" height=\"48\"></a></div>" +
            "    <a class=\"useful\" title=\"点赞\" onclick='clickThumbsUp(this)'><i></i>" +
            "        <span class=\"useful-num\">" + comment.thumbsUpCount + "</span>" +
            "    </a>" +
            "    <a class=\"name\" target=\"_blank\">" + comment.remarkerName + "</a>" +
            "    <p class=\"rev-txt\">" + comment.content + "</p>" +
            "    <div class=\"info clearfix\">" +
            "        <a class=\"btn-comment _j_comment\" onclick='clickReply(this)' title=\"评论\"" +
            "                 style=\"text-decoration:none;\">评论</a>" +
            //展开按钮
            "        <span class=\"time\">" + comment.time + "</span>" +
            "    </div>" +
            "    <div class=\"comment add-reply\">" +
            "        <ul class=\"more_reply_box comment_list\">" +
            //子评论
            "        </ul>" +
            "        <div class=\"add-comment reply-form\" reply='0' style=\"display: none\">" +
            "            <textarea class=\"comment_reply\" style=\"overflow: hidden; color: rgb(204, 204, 204);float: left;\"></textarea>" +
            "            <a class=\"btn btn_submit_reply\" onclick='clickReplySubmit(this)' style=\"float: right;text-align:center;padding: 0;wid 15%;height: 25px;font-size: 14px;\">" +
            "                回复" +
            "            </a>" +
            "        </div>" +
            "    </div>" +
            "</li>");
        if (comment.reply.length > 2) {
            $(c).find("._j_comment").after("<a class=\"btn-comment _j_comment\" title=\"展开\" onclick=\"clickExtend(this)\" extend=\"0\" style=\"padding:0 5px;\">展开剩下" +
                (comment.reply.length - 2) + "条</a>")
        }
        $(comment.reply).each(function (j, r) {
            var str = "<li style=\"display:" + (j>1?"none":"block") + "\" index=" + (j + 1) + " u_id=" + r.replierId +
                "          c_tag='reply' c_id=" + comment.remarkId + " u_name=" + r.replierName + ">" +
                "    <a onclick=\"\" target=\"_blank\">" +
                "        <img src=" + r.replierPic + " width=\"16\" height=\"16\">" + r.replierName +
                "    </a> ";
            if (r.respondedName != "") {
                str += " 回复 <a u_id=" + r.respondedId +">" + r.respondedName + "</a>"
            }
            str += " ：" + r.content +
                "    <a class=\"_j_reply re_reply\" onclick='clickReply(this)' title=\"添加回复\">回复</a>\n" +
                "    <br><span class=\"time\">" + r.time + "</span>" +
                "</li>";
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
        currentPage: page,// 当前页数
        totalPage: totalPage,// 总页数
        isShow: true,// 是否显示首尾页
        count: 10,// 显示个数
        homePageText: "首页",// 首页文本
        endPageText: "尾页",// 尾页文本
        prevPageText: "上一页",// 上一页文本
        nextPageText: "下一页",// 下一页文本
        callback: function(current) {
            // 回调,current(当前页数)
            page = current;
            togetherSearch(id);

            var scroll_offset = $(".filter-bar").offset(); //得到pos这个div层的offset，包含两个值，top和left
            $("body,html").animate({
                scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
            },200);
        }
    });
}