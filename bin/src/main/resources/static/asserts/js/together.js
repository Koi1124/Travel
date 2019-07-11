var data = new Array();
var page = 1;
var totalPage = 10;
var id = 1;
var itemCount = 8;
//获取当前网址，如： http://localhost:80/ybzx/index.jsp  
var curPath=window.document.location.href;
//获取主机地址之后的目录，如： ybzx/index.jsp  
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:80  
var localhostPaht=curPath.substring(0,pos);


$(document).ready(function() {
    //8个结伴目的地推荐
    $.ajax({
        type: "post",
        url: localhostPaht+"/together/mdd_top",
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            $(result).each(function (i, mdd) {
                $("#together-mdd-box").append("<li class=\"item\">\n" +
                    "    <a class=\"_j_hot_mdd\" data-mddname=''" + mdd.name + " data-mddid:" + mdd.id + ">\n" +
                    "    <div class=\"image\"><img src=" + mdd.src + " style=\"width: 220px;height: 220px;\"></div>\n" +
                    "    <div class=\"bg\"></div>\n" +
                    "    <div class=\"txt\">\n" +
                    "    <strong>" + mdd.name + "</strong>\n" +
                    "    <p>发起 " + mdd.count + " 个结伴<br>" + mdd.star + " 人关注<br>" + mdd.app + " 人报名</p>\n" +
                    "    </div>\n" +
                    "    </a>\n" +
                    "</li>\n");
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

function initDropBar() {
    $("#_j_together_mdd_search").focus(function () {
        $("#drop-place").show(250);
    });
    $("#_j_together_mdd_search").blur(function () {
        setTimeout(function(){
            $("#drop-place").hide(250);
        }, 250);
    });
    $("._j_time_input").focus(function () {
        $("#drop-date").show(250);
    });
    $("._j_time_input").blur(function () {
        setTimeout(function(){
            $("#drop-date").hide(250);
        }, 250);
    });
}

//根据json数据设置地点选择框
function setData(json) {
    $(json).each(function (i, map) {
        $(".col1").append("<li class=\"_j_country_select\" data-index=" + i + " data-name=" + map["name"] +
            "><a>" + map["name"] + "<i class=\"icon-arrow\"></i></a></li>");
        var list = new Array();
        for (var key in map) {
            if (key != "name") {
                list[key] = map[key];
            }
        }
        data[i] = list;

    });

    //col1框中数据点击事件
    $("._j_country_select").mouseover(function () {
        $(".col2").empty();
        initCol2($(this).attr("data-index"));
        $(".on").attr("class", "_j_country_select");
        $(this).attr("class", "on");
    });





}

//初始化col2
function initCol2(col1) {
    $(data[col1]).each(function (i, item) {
        $(".col2").append("<ul class=\"_j_city_select\" data-mddid=" + item.id + " data-name=" + item.name +
            "       ><a><span class=\"num\"><em>" + item.count + "</em>结伴</span>" + item.name + "</a></ul>");
    });

    $("._j_city_select").click(function () {
        page = 1;
        togetherSearch($(this).attr("data-mddid"));
    });

}

//结伴搜索
function togetherSearch(mid) {
    $.ajax({
        type: "post",
        url: localhostPaht+"/together/company_search",
        contentType: "application/json",
        data:JSON.stringify({
            page:page,
            itemCount:itemCount,
            id:mid
        }),
        dataType: "json",
        async: true,
        success: function (result) {
            if (id != mid) {
                id = mid;
                page = 1;
                totalPage = Math.ceil(result.count / itemCount);
                initPagination();
            }
            $("._j_together_list").empty();
            setCompany(result.info);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

//搜索出结伴之后写入页面
function setCompany(data) {
    $(data).each(function (i, item) {
        $("._j_together_list").append("<li class=\"item\">\n" +
            "                <a class=\"company-item\" together-id=" + item.id + " target=\"_blank\" star=" + item.star + "  authorName="+ item.authorName +" authorPic="+ item.authorPic +" mddPic="+ item.mddPic +"  onclick='companyClick(this)'>\n" +
            "                        <div class=\"image\">\n" +
            "                            <img src=" + item.mddPic + " style=\"width: 200px;height: 130px;\">\n" +
            "                            <div class=\"after\"><b>" + item.days +
            "                            </b>天后</div><hr>" +
            "                        </div>" +
            "                        <h3 class=\"title\">" + item.name +
            "                        </h3><div class=\"desc\">" + item.intro +
            "                        ...</div><div class=\"user clearfix\">\n" +
            "                <span class=\"avatar\"><img src=" + item.authorPic + " width=\"48\"  >" +
            "                </span>" +
            "                            <span class=\"name\" style='font-size: 18px' > " + item.authorName +
            "                          </span>\n</div>\n" +
            "                        <div class=\"attention\"><i class=\"icon-arrow\"></i>已有<b>" + item.star + "</b>人关注</div>\n" +
            "                    </a>" +
            "                </li>");
    });
}

function companyClick(c) {
    var form = $("<form method='post'></form>");
    form.attr({"action": localhostPaht+"/together/company/detail/"+$(c).attr("together-id")+".html"});
    var args = {id:$(c).attr("together-id"),
        name:$(c).children("h3").get(0).innerText,
        star:$(c).attr("star"),
        authorName:$(c).attr("authorName"),
        authorPic:$(c).attr("authorPic"),
        mddPic:$(c).attr("mddPic")};
    for (var arg in args)
    {
        var input = $("<input type='hidden'>");
        input.attr({"name": arg});
        input.val(args[arg]);
        form.append(input);
    }
    $("html").append(form);
    form.submit();
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