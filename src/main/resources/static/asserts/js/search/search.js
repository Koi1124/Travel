var key_word;

$(function () {
    keyword();

    $(".poi-nav").click(function () {
        $("._j_select_type a").removeClass("on");
        $(this).addClass("on");
        key_word=$("#_j_search_input").val();
        initSight();
    });

    $(".notes-nav").click(function () {
        $("._j_select_type a").removeClass("on");
        $(this).addClass("on");
        key_word=$("#_j_search_input").val();
        initNote();
    });

    $(".together-nav").click(function () {
        $("._j_select_type a").removeClass("on");
        $(this).addClass("on");
        key_word=$("#_j_search_input").val();
        initTogether();
    })


    $("#_j_search_button").click(function () {
        key_word=$("#_j_search_input").val();
        if (key_word==null||key_word==''){
            logError("请输入关键字");
        }
        $(".on").each(function () {
            if ($(this).hasClass("poi-nav")) {
                initSight();
            }
            if ($(this).hasClass("notes-nav")) {
                initNote();
            }
            if ($(this).hasClass("together-nav")) {
                initTogether();
            }
        })
    })
});



function keyword() {
    var origin=$(".att-list").html();
    var key=$("#_j_search_input").val();
    var reg=new RegExp(""+key+"","g");
    var result=origin.replace(reg,'<span class="sr-keyword">'+ key +'</span>');
    $(".att-list").html(result);
}

function initNote() {
    $.ajax({
        type: "post",
        url: "/search/innerNote",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            key_word:key_word
        }),
        success:function (result) {
            $(".att-list").empty()
            $(".att-list").append('<p>共有'+ result.length +'条结果</p>');
            $(".att-list").append('<ul></ul>');
            $(result).each(function (i,item) {
                $(".att-list ul:eq(0)").append('<li>\n' +
                    '                            <div class="clearfix">\n' +
                    '                                <div class="flt1"><a href="/note/'+ item.nid +'" target="_blank" class="_j_search_link">\n' +
                    '                                    <img src="'+ item.npic +'" style="width:150px;height:90px;overflow: hidden"></a></div>\n' +
                    '                                <div class="ct-text ">\n' +
                    '                                    <h3>\n' +
                    '                                        <a href="/note/'+ item.nid +'" target="_blank" class="_j_search_link"><hhh>'+ item.nname +'</hhh></a>\n' +
                    '                                    </h3>\n' +
                    '                                    <p class="seg-desc" style="overflow:hidden;">'+ item.nintro +'</p>\n' +
                    '                                    <ul class="seg-info-list clearfix">\n' +
                    '                                        <li>\n' +
                    '                                            <a href="/c/'+ item.cid +'" target="_blank" class="_j_search_link"><hhh>'+ item.cname +'</hhh></a>\n' +
                    '                                        </li>\n' +
                    '                                        <li style="padding-top: 2px">\n' +
                    '                                            '+ item.time +' \n' +
                    '                                        </li>\n' +
                    '                                        <li>\n' +
                    '                                           评论数:'+ item.commentCount +' \n' +
                    '                                        </li>\n' +
                    '                                        <li>收藏数:'+ item.starCount +'</li>\n' +
                    '                                    </ul>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </li>')
            });
            keyword();
        },
        error:function (e) {
            console.log(e);
        }
    })
}

function initSight() {
    $.ajax({
        type: "post",
        url: "/search/innerSight",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            key_word:key_word
        }),
        success:function (result) {
            $(".att-list").empty()
            $(".att-list").append('<p>共有'+ result.length +'条结果</p>');
            $(".att-list").append('<ul></ul>');
            $(result).each(function (i,item) {
                $(".att-list ul:eq(0)").append('<li>\n' +
                    '                            <div class="clearfix">\n' +
                    '                                <div class="flt1"><a href="/sight/'+ item.sid +'" target="_blank" class="_j_search_link">\n' +
                    '                                    <img src="'+ item.spic +'" style="width:150px;height:90px;overflow: hidden;"></a></div>\n' +
                    '                                <div class="ct-text ">\n' +
                    '                                    <h3>\n' +
                    '                                        <a href="/sight/'+ item.sid +'" target="_blank" class="_j_search_link"><hhh>'+ item.sname +'</hhh></a>\n' +
                    '                                    </h3>\n' +
                    '                                    <ul class="seg-info-list clearfix">\n' +
                    '                                        <li>\n' +
                    '                                            <a href="/c/+'+ item.cid +'"><hhh>'+ item.cname +'</hhh></a>\n' +
                    '                                        </li>\n' +
                    '                                        <li>\n' +
                    '                                            评论数:'+ item.commentCount +' \n' +
                    '                                        </li>\n' +
                    '                                        <li>收藏数:'+ item.starCount +'</li>\n' +
                    '                                    </ul>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </li>')
            });
            keyword();
        },
        error:function (e) {
            console.log(e);
        }
    })
}

function initTogether() {
    $.ajax({
        type: "post",
        url: "/search/innerTogether",
        contentType: "application/json",
        dataType: "json",
        async: true,
        data:JSON.stringify({
            key_word:key_word
        }),
        success:function (result) {
            $(".att-list").empty()
            $(".att-list").append('<p>共有'+ result.length +'条结果</p>');
            $(".att-list").append('<ul></ul>');
            $(result).each(function (i,item) {
                $(".att-list ul:eq(0)").append('<li>\n' +
                    '                            <div class="clearfix">\n' +
                    '                                <div class="ct-text ">\n' +
                    '                                    <h3>\n' +
                    '                                        <a href="/together/company/detail/'+ item.tid +'.html" target="_blank" class="_j_search_link">' +
                    '                                        <hhh>'+ item.tname +'</hhh></a>\n' +
                    '                                    </h3>\n' +
                    '                                    <ul class="seg-info-list clearfix">\n' +
                    '                                        <li>\n' +
                    '                                            目的地:'+ item.mdd +' \n' +
                    '                                        </li>\n' +
                    '                                        <li>出发日期:'+ item.time +'</li>\n' +
                    '                                        <li>\n' +
                    '                                            评论数:'+ item.commentCount +'\n' +
                    '                                        </li>\n' +
                    '                                        <li>收藏数:'+ item.starCount +'</li>\n' +
                    '                                    </ul>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                        </li>')
            });
            keyword();
        },
        error:function (e) {
            console.log(e);
        }
    })
}