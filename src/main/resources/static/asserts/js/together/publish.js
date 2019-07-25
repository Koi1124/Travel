var images = new Array();
var mddVal="";

$(document).ready(function() {
    $.ajax({
        url: "/together/mdd_info",
        type: "post",
        dataType: "json",
        async: true,
        success: function (data) {
            var avaliableTags = eval(data);
            $("#_j_go_mdd").autocomplete({
                minLength:0,
                source: avaliableTags,
                message:{
                    results: function(){
                    },
                    noResults: ''
                },
                focus: function () {
                    return false;
                },
                select:function (event,ui) {
                    $("._j_go_mdd_list").addClass("have");
                    $("#_j_go_mdd").addClass("have");
                    if ($("._j_go_mdd_list span").attr("data-mddid")!=ui.item.id){
                        $("._j_go_mdd_list").prepend('<span class="tag-place _j_mdd_remove _j_go_mdd_add" style="height: 26px" data-mddid="'+ ui.item.id +'">'+ ui.item.value +'<i>×</i></span>');
                        mddVal=mddVal+ui.item.id+";";
                        $("#go_mdd_id").val(mddVal);
                    }
                    initRemoveMddTag();
                    $("#_j_go_mdd").val("");
                    // var terms = split(this.value);
                    // var temp=split(this.id);
                    // terms.pop();
                    // temp.pop();
                    // terms.push(ui.item.value);
                    // temp.push(ui.item.id);
                    // terms.push("");
                    // temp.push("");
                    // this.value=terms.join(";");
                    // this.id=temp.join(";");





                    return false;
                }
            })

            // $("#_j_from_mdd").autocomplete({
            //     source:avaliableTags,
            //     message:{
            //         results: function(){
            //         },
            //         noResults: ''
            //     },
            //     select:function (event,ui) {
            //         $("#from_mdd_id").val(ui.item.id);
            //     }
            // })
        },
        error: function (e) {
            console.log(e);
        }
    });






    $("#date-input").calendar();
    $("#date-inputCalendar").css({position:'relative',top:'-695px',left:'415px'});

    $("._j_submit").click(function () {
        if (uid == null) {
            pleaseLogin("发表结伴");
        }
        else {
            togetherSubmit();
        }
    });
});



function initRemoveMddTag() {
    $("._j_mdd_remove").click(function () {
        var mddid=$(this).attr("data-mddid");
        var arr=mddVal.split(";");
        var update="";
        $(arr).each(function (i,item) {
            if (item!=mddid&&item!="") {
                update=update+item+";";
            }
        })
        mddVal=update;
        $("#go_mdd_id").val(mddVal);
        $(this).remove();
        if (!$("._j_go_mdd_list:has(span)").length) {
            $("._j_go_mdd_list").removeClass("have");
            $("#_j_go_mdd").removeClass("have");
        }
    })
}


function togetherSubmit() {
    images = [];
    if (testBlank($("._j_title").val())) {
        logError("标题不能为空");
    }
    else if (testBlank($("._j_phone").val()) && testBlank($("._j_qq").val()) &&
        testBlank($("._j_weixin").val())) {
        logError("至少要有一种联系方式");
    }
    else if (testBlank($("#go_mdd_id").val())) {
        logError("请按照要求输入目的地");
    }
    else if (testBlank($("#_j_from_mdd").val())) {
        logError("请输入出发地");
    }
    else if (testBlank($("#date-input").val())) {
        logError("请选择出发时间");
    }
    else if (!testNumGt0($("._j_count_day").val())) {
        logError("请输入正确的天数");
    }
    else if (!testNumGt0($("._j_hope_num").val())) {
        logError("请输入正确的人数");
    }
    else if (testBlank($("._j_description").val())) {
        logError("请输入结伴描述");
    }
    else {
        var phone = "";
        if (!testBlank($("._j_phone").val())) phone += ' 手机号:' + $("._j_phone").val();
        if (!testBlank($("._j_qq").val())) phone += ' QQ:' + $("._j_qq").val();
        if (!testBlank($("._j_weixin").val())) phone += ' 微信:' + $("._j_weixin").val();

        var content = $("._j_description").val();
        var reg = /\n\n+/g;
        content = content.replace(reg, "<br/><br/>");
        reg = /\n/g;
        content = content.replace(reg, "<br/>");

        $(".up-img").each(function (i, item) {
            images[i] = item.src;
        });

        var data = {
            aaa101:uid,
            aab502:$("._j_title").val(),
            aab503:$("#date-input").val(),
            aab504:$("#_j_from_mdd").val(),
            aab505:$("._j_count_day").val(),
            aab506:$("._j_hope_num").val(),
            aab507:phone,
            aab508:content,
            mdds:$("#go_mdd_id").val(),
            images:images
        }

        console.log(data);

        $.ajax({
            url: "/together/addCompany",
            type: "post",
            dataType: "json",
            contentType: "application/json;charset-UTF-8",
            data:JSON.stringify(data),
            async: true,
            success: function (result) {
                if (result) {
                    $("._j_submit").attr("disabled", "true");
                    $("._j_submit").unbind();
                    $("._j_submit").text("已发表成功");
                    logSuccess("发布成功");
                    setTimeout(function () {
                        window.location.href="/together";
                    }, 1500);

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

function testBlank(str) {
    str = str.replace(/\s+/g, "");
    var reg = /\n/g;
    str = str.replace(reg, "");
    return str.length == 0;
}

function testNumGt0(str) {
    if (str == "0")
        return false;
    var re = /^[0-9]+$/ ;
    return re.test(str);
}

function extractLast( term ) {
    return split( term ).pop();
};

function split( val ) {
    return val.split( /;\s*/ );
}


