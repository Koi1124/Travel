var id = -1;
var s_type=2;
var page = 1;
var totalPage = 10;
var itemCount = 10;
var index=0;

$(document).ready(function () {
    setInterval(slidePic,5000);
    clickSlideBar();

    getNotes(0,s_type);

    selectSeachBar();
    initSubmit();

    // 热门和最新
    $(".tn-nav-hot").click(function () {
        $("._j_gs_tab").removeClass("active");
        $(".tn-nav-hot").addClass("active");
        s_type=2;
        getNotes(id,s_type);
        scroll();
    });
    $(".tn-nav-new").click(function () {
        $("._j_gs_tab").removeClass("active");
        $(".tn-nav-new").addClass("active");
        s_type=1;
        getNotes(id,s_type);
        scroll();
    });



});

function slidePic() {
    ++index;
    if (index>4){
        index=0;
    }
    $(".show-image li").fadeOut(1000);
    $(".show-nav li").removeClass("active_select");
    $($(".show-nav li")[index]).addClass("active_select");
    $($(".show-image li")[index]).fadeIn(1000);
}

function clickSlideBar() {
    $(".show-nav li").click(function () {
        index=$(".show-nav li").index($(this));
        $(".show-nav li").removeClass("active_select");
        $(this).addClass("active_select");
        $(".show-image li").fadeOut(1000);
        $($(".show-image li")[index]).fadeIn(1000);
    })
}


function selectSeachBar() {
    $("#_j_index_search_tab ul li").click(function () {
        $("#_j_index_search_tab ul li").removeClass("tab-selected");
        $(this).addClass("tab-selected");
        switch ($(this).attr("data-index")) {
            case "1":
                $("#_j_index_search_bar_mdd form").attr("action","/search/sight");
                break;
            case "2":
                $("#_j_index_search_bar_mdd form").attr("action","/search/note");
                break;
            case "3":
                $("#_j_index_search_bar_mdd form").attr("action","/search/together");
                break;
        }
    })
}

function initSubmit() {
    $("#_j_index_search_btn_mdd a").click(function () {
        if ($("#_j_index_search_input_mdd").val()==""||$("#_j_index_search_input_mdd").val()==null||$("#_j_index_search_input_mdd").val()==" ") {
            logError("请输入关键字");
            $("#_j_index_search_input_mdd").val("");
        }else {
            $("#_j_index_search_bar_mdd form").submit();
        }
    })
}



function setNotes(data) {
    $(data).each(function (i,item) {
        $(".tn-list").append('<div class="tn-item clearfix">\n' +
            '                                <div class="tn-image">\n' +
            '                                    <a href="/note/'+ item.id +'" target="_self">\n' +
            '                                        <img class="" src="'+ item.coverPic +'" style="display: inline;">\n' +
            '\n' +
            '\n' +
            '                                    </a>\n' +
            '                                </div>\n' +
            '                                <div class="tn-wrapper">\n' +
            '                                    <dl style="overflow:hidden">\n' +
            '                                        <dt>\n' +
            '                                            <a href="/note/'+ item.id +'" target="_self">'+ item.title +'</a>\n' +
            '                                        </dt>\n' +
            '                                        <dd>\n' +
            '                                            <a href="/note/'+ item.id + '" target="_self"> \n' +
            '                                                '+ item.content +'</a>\n' +
            '                                        </dd>\n' +
            '                                    </dl>\n' +
            '                                    <div class="tn-extra">\n' +
            '                                        <span class="tn-ding">\n' +
            '                                            <a class="btn-ding" href="javascript:;" data-japp="articleding" data-iid="'+ item.id +'" data-vote="163" rel="nofollow"></a>\n' +
            '                                            <em>'+ item.thumbsUpCount +'</em>\n' +
            '                                        </span>\n' +
            '                                        <span class="tn-place"><i></i><a href="javascript:void(0);" class="_j_gs_item" rel="nofollow" data-name="'+ item.mddName +'" data-objid="'+ item.mddId +'" data-type="2">'+ item.mddName +'</a>，by</span>\n' +
            '                                        <span class="tn-user">\n' +
            '                                            <a href="/u/'+ item.authorId +'/note" target="_self" rel="nofollow">\n' +
            '                                                <img src="'+ item.authorPic +'" style="width:15px;padding-bottom:1px">\n' +
            '                                                '+ item.authorName +'\n' +
            '                                            </a>\n' +
            '                                        </span>\n' +
            '                                        <span class="tn-nums"><i></i>'+ item.view +'/'+ item.commentCount +'</span>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>');
    })

    $("._j_gs_item").click(function () {
        var mid=$(this).attr("data-objid");
        var name=$(this).attr("data-name");
        if ($(this).attr("data-name")!=$("._j_gs_tag").text()){
            $("._j_tag_choose_container").append('<a class="tn-tag _j_gs_tag" data-type="2" data-objid="'+ id +'" href="javascript:void(0);">'+ name +'<i></i></a>');
            getNotes(mid,s_type);
        }
        scroll();
    });

    $("._j_tag_choose_container i").click(function () {
        var mid=0;
        $("._j_tag_choose_container a").remove();
        getNotes(mid,s_type);
        scroll();
    })
}


function scroll() {
    var scroll_offset = $(".zy-wrapper").offset(); //得到pos这个div层的offset，包含两个值，top和left
    $("body,html").animate({
        scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
    },400);
}

function getNotes(mddId,type) {
    // 主页游记列表
    $.ajax({
        type: "post",
        url: "/displayNotes",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            page:page,
            itemCount:itemCount,
            mddId:mddId,
            type:type
        }),
        success:function (result) {
            if (id != mddId){
                id = mddId;
                s_type=type;
                page = 1;
                totalPage =Math.ceil(result.count / itemCount);
                initPagination();
            }
            $(".tn-list").empty();
            setNotes(result.noteInfo);
        },
        error:function (e) {
            console.log(e);
        }
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
            scroll();
            page = current;
            getNotes(id,s_type)
        }
    });
}