//获取当前网址，如： http://localhost:80/ybzx/index.jsp  
var curPath=window.document.location.href;
//获取主机地址之后的目录，如： ybzx/index.jsp  
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:80  
var localhostPaht=curPath.substring(0,pos);


// [{remarkerId=1, thumbsUpCount=2, remarkerName=user1, remarkerPic=user1.png, time=2019-07-09 11:26:48.0, remarkId=1, content=插入评论测试},
// reply=[{replierName=user2, thumbsUpCount=3, replierPic=user2.png, time=2019-07-09 14:23:26.0, replierId=2, content=回复评论测试1}, {replierName=user3, thumbsUpCount=1, replierPic=user3.png, time=2019-07-09 14:24:07.0, replierId=3, content=回复评论测试2}]]

$(document).ready(function() {
    //8个结伴目的地推荐
    $.ajax({
        type: "post",
        url: localhostPaht+"/together/company/detail_comment",
        contentType: "application/json",
        data:JSON.stringify({
            page:page,
            itemCount:itemCount,
            id:mid
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            $(result).each(function (i, mdd) {
                $("#comment-list").append("<div class=\"vc_comment\">\n" +
                    "    <div class=\"avatar\"><a href=\"/u/84295171.html\" target=\"_blank\"><img src=\"https://n4-q.mafengwo.net/s13/M00/93/12/wKgEaVzO1C2AfnLYAAXsoPdWevY98.jpeg?imageMogr2%2Fthumbnail%2F%21120x120r%2Fgravity%2FCenter%2Fcrop%2F%21120x120%2Fquality%2F90\" style=\"width: 48px;height: 48px;\"></a></div>\n" +
                    "    <div class=\"comm_con\">\n" +
                    "    <dl>\n" +
                    "    <dt class=\"clearfix\">\n" +
                    "    <div class=\"comm_info\">\n" +
                    "    <a target=\"_blank\" class=\"comm_name\" href=\"/u/84295171.html\">chao(北京)</a>\n" +
                    "    <a target=\"_blank\" class=\"comm_grade\" href=\"/u/84295171.html\">LV.10</a>\n" +
                    "    <br>\n" +
                    "    <span class=\"comm_time\">2019-07-05 21:35:34</span>\n" +
                    "    </div>\n" +
                    "    <div class=\"comm_reply\">\n" +
                    "    <a class=\"_j_replay\" data-cid=\"29215702\" data-name=\"chao\" data-uid=\"84295171\">回复</a>\n" +
                    "    <a class=\"_j_cited\" data-cid=\"29215702\" data-name=\"chao\">引用</a>\n" +
                    "    <a data-japp=\"report\" reply_id=\"29215702\" data-busi-id=\"cid:29215702\" data-app=\"together.comment\">举报</a>\n" +
                    "    </div>\n" +
                    "    </dt>\n" +
                    "    <dd>\n" +
                    "\n" +
                    "\n" +
                    "    <div class=\"comm_word\">\n" +
                    "    <p>加个微信好嘛， 拉你进新疆旅游群，可找队友和路线交流~ 199706175</p>\n" +
                    "</div>\n" +
                    "</dd>\n" +
                    "</dl>\n" +
                    "</div>\n" +
                    "</div>");
            });
        },
        error: function (e) {
            console.log(e);
        }
    });

    //省市搜索
    $.ajax({
        type: "post",
        url: localhostPaht+"/together/mdd_search",
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            setData(result);
        },
        error: function (e) {
            console.log(e);
        }
    });


    initPagination(totalPage);
    togetherSearch(1);
    initDropBar();
});