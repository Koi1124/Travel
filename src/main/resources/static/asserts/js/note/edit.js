var activeText = null;//当前激活的文本框
var curCurPos = 0;//当前的光标位置
var activeTitle = null;
var index = 1;//title编号，用来右侧栏跳转

var base64 = new Array();
var picCount = 0;
var picLoad = 0;
var poiId = null;

var titleBox = "<div class='article_title _j_data_item'>\n" +
    "    <h2 class='t9' style='display: none'><span>武汉</span></h2>\n" +
    "    <span class='handle _j_actionarea'>\n" +
    "        <a role='button' class='edit _j_paragraph_title_edit' title='编辑'>编辑</a>\n" +
    "        <a role='button' class='delete _j_paragraph_title_delete _j_item_remover' title='删除'>删除</a>\n" +
    "    </span>\n" +
    "</div>";

var textBox = "<div>\n" +
    "    <textarea cols='30' rows='1' class='textarea' style='overflow: hidden; height: 48px;'></textarea>\n" +
    "</div>";

var picBox = "<div class='add_pic _j_data_item'>\n" +
    "    <a role='button' class='pic_edit'>\n" +
    "        <img src=''\n" +
    "            width='680'>\n" +
    "        <span class='loading'></span>\n" +
    "        <span class='turn '>\n" +
    "            <strong class='go_set_page'>设置为封面</strong>\n" +
    "            <i class='close'></i>\n" +
    "        </span>\n" +
    "    </a>\n" +
    "</div>";

//添加标题（侧栏）点击事件
function clickTitle() {
    curCurPos = activeText.getCurPos();
    activeTitle = null;
    $(".a-inptxt").val("");
    $("#mask").fadeIn(250);
    $(".add-panel-title").fadeIn(250);
}

//添加标题
function addTitle() {
    if (testBlank($(".a-inptxt").val())) {
        logError("小标题不能为空");
        return;
    }
    if (activeTitle == null) {
        //添加标题
        var text = activeText.val().substr(curCurPos);
        activeText.val(activeText.val().substr(0, curCurPos));

        var title = $(titleBox);
        title.attr("id", "title-" + index);
        index++;
        title.find("h2 span").text($(".a-inptxt").val());
        $(".a-inptxt").val("");
        $("#mask").fadeOut(250);
        $(".add-panel-title").fadeOut(250);

        activeText.closest("div").after(title);
        var newText = $(textBox);
        newText.find("textarea").txtaAutoHeight();
        newText.find("textarea").val(text);
        newText.find("textarea").focus(function () {
            activeText = newText.find("textarea");
        });
        title.closest("div").after(newText);
        setAutoHeight(activeText);
        setAutoHeight(newText.find("textarea"));
        title.find("h2").show(250);
        //添加点击事件
        title.find("._j_paragraph_title_delete").click(function () {
            title.hide(250, function () {
                title.prev().find("textarea").val(title.prev().find("textarea").val() + "\n" + title.next().find("textarea").val());
                setAutoHeight(title.prev().find("textarea"));
                title.next().remove();
                title.remove();
                refreshTitle();
            });
        });
        title.find("._j_paragraph_title_edit").click(function () {
            activeTitle = title;
            $(".a-inptxt").val(title.find("h2 span").text());
            $("#mask").fadeIn(250);
            $(".add-panel-title").fadeIn(250);
            refreshTitle();
        });
    }
    else {
        //修改标题
        activeTitle.find("h2 span").text($(".a-inptxt").val());
        $(".a-inptxt").val("");
        $("#mask").fadeOut(250);
        $(".add-panel-title").fadeOut(250);
        activeTitle = null;
    }

    refreshTitle();
};

//设置头图
function setTopImage() {
    var file = $("#top_image_input").get(0).files[0];
    var reader = new FileReader();
    //创建文件读取相关的变量
    var imgFile;
    //为文件读取成功设置事件
    reader.onload=function(e) {
        imgFile = e.target.result;
        var image = new Array();
        image.push(imgFile);
        $.ajax({
            type: "post",
            url: "/note/uploadImg",
            data: JSON.stringify({images:image}),
            contentType: "application/json",
            dataType: "json",
            async: true,
            success: function (result) {
                $("#top_image").attr("src", result[0]);
                $(".set_page").hide();
                $(".set_btn").show();
            },
            error: function (e) {
                logError("网络故障，请稍后再试");
                console.log(e);
            }
        });

    };
    reader.readAsDataURL(file);
}

//图片选择之后上传
function selectPic() {
    $("#loading").fadeIn(250);
    var fileList = document.getElementById("file").files;

    picCount = fileList.length;
    picLoad  = 0;
    base64 = [];
    for (var i = 0; i < fileList.length; i++) {
        var reader = new FileReader();
        //创建文件读取相关的变量
        var imgFile;
        //为文件读取成功设置事件
        reader.onload=function(e) {
            imgFile = e.target.result;
            base64.push(imgFile);
            picLoad++;
            if (picLoad == picCount) {
                $.ajax({
                    type: "post",
                    url: "/note/uploadImg",
                    data: JSON.stringify({images:base64}),
                    contentType: "application/json",
                    dataType: "json",
                    async: true,
                    success: function (result) {
                        $("#loading").fadeOut(250);
                        addPic(result);
                    },
                    error: function (e) {
                        logError("网络故障，请稍后再试");
                        console.log(e);
                    }
                });
            }
        };
        reader.readAsDataURL(fileList[i]);
    }
}

//图片添加
function addPic(data) {
    $(data).each(function (i, item) {
        var pic = $(picBox);
        pic.find("img").attr("src", item);
        activeText.closest("div").after(pic);
        var newText = $(textBox);
        pic.after(newText);
        newText.find("textarea").txtaAutoHeight();
        newText.find("textarea").focus(function () {
            activeText = newText.find("textarea");
        });
        activeText = newText.find(".textarea");

        //点击事件
        pic.find(".go_set_page").click(function () {
            $("#top_image").attr("src", pic.find("img").attr("src"));
            $(".set_page").hide();
            $(".set_btn").show();
        });
        pic.find("i").click(function () {
            pic.hide(250, function () {
                pic.prev().find("textarea").val(pic.prev().find("textarea").val() + "\n" + pic.next().find("textarea").val());
                setAutoHeight(pic.prev().find("textarea"));
                pic.next().remove();
                pic.remove();
            });
        });
    });
}

//刷新右边侧栏
function refreshTitle() {
    $(".catalog_content").empty();
    $("._j_content_box").find(".article_title").each(function (i, item) {
        var li = $("<li to-id=" + $(item).attr("id") + ">\n" +
            "    <span class='catalog_num'>" + (i + 1) + "/</span>\n" +
            "    <a role='button' title=" + $(item).find("h2 span").text() +
            "        class='catalog_line _j_cataloglink'>" + $(item).find("h2 span").text() + "</a>\n" +
            "</li>");
        li.click(function () {
            $("html,body").animate({scrollTop: $("#" + li.attr("to-id")).offset().top}, 200);
        });
        $(".catalog_content").append(li);
    });
}

//textarea自适应高度
function setAutoHeight(elem) {
    var $obj = $(elem);
    return $obj.css({ height: $obj.attr('initAttrH'), 'overflow-y': 'hidden' }).height(elem.prop("scrollHeight"));
}

//提交按钮
function clickSubmit() {
    if (testBlank($("#_j_title").val())) {
        logError("标题不能为空！");
        return;
    }
    if ($("._j_content_box").children("div").length == 1 && testBlank($("#textarea").val())) {
        logError("请填写游记内容！");
        return;
    }
    var str = "";
    $(".textarea").each(function (i, item) {
        str += $(item).val();
    });
    $("h2").each(function (i, item) {
        str += $(item).text();
    });
    $.ajax({
        type: "post",
        url: "/note/getPlace",
        data: JSON.stringify({content:$("#_j_title").val() + str}),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            $(".pi-otherTag").empty();
            for(var key in result) {
                addPoiTag(key, result[key]);
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
    $("#submit-mask").fadeIn(250);
}

//确认提交
function submitNote() {
    var intro = $("#textarea").val();
    var reg = /\n\n+/g;
    intro = intro.replace(reg, "<br/><br/>");
    reg = /\n/g;
    str = intro.replace(reg, "<br/>");
    var data = {
        title:$("#_j_title").val(),
        content:getContent(),
        topImg:$("#top_image").attr("src"),
        intro:intro,
        nid:nid,
        poi:poiId,
        date:$("#date-input").val(),
        money:$("#money-input").val(),
        time:$("#days-input").val(),
        status:"1"
    }
    $.ajax({
        type: "post",
        url: "/note/updateNote",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            if (result) {
                logSuccess("发布成功");
                setTimeout(function () {
                    document.location.href="/preview/" + nid;
                }, 1000);
            }
            else {
                logError("网络故障，请稍后再试");
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}

//保存草稿
function saveNote() {
    var intro = $("#textarea").val();
    var reg = /\n\n+/g;
    intro = intro.replace(reg, "<br/><br/>");
    reg = /\n/g;
    str = intro.replace(reg, "<br/>");
    var data = {
            title:$("#_j_title").val(),
            content:getContent(),
            topImg:$("#top_image").attr("src"),
            intro:intro,
            nid:nid
    }
    $.ajax({
        type: "post",
        url: "/note/updateNote",
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (result) {
            if (result) {
                $("._j_draft_save_time").text("已于" + getNowFormatDate() + "保存");
                logSuccess("保存成功");
            }
            else {
                logError("网络故障，请稍后再试");
            }
        },
        error: function (e) {
            console.log(e);
        }
    });
}

//获取文章主题内容
function getContent() {
    var data = new Array();
    $("._j_content_box").children("div").each(function (i, item) {
        if ($(item).attr("class") == "article_title _j_data_item") {
            //title
            data.push({title:"\"" + $(item).find("h2").text() + "\""});
        }
        else if ($(item).attr("class") == "add_pic _j_data_item") {
            //pic
            data.push({pic:"\"" + $(item).find("img").attr("src") + "\""});
        }
        else {
            //text
            if (!testBlank($(item).find(".textarea").val())) {
                var str = $(item).find(".textarea").val();
                var reg = /\n\n+/g;
                str = str.replace(reg, "<br/><br/>");
                reg = /\n/g;
                str = str.replace(reg, "<br/>");
                data.push({text: "\"" + str + "\""});
            }
            else {
                data.push({text: "\"\""});
            }
        }
    });
    return data;
}

function addPoiTag(name, id) {
    var poi = $("<li class='pi-tagitem _j_tagitem' poi-id=" + id + " role='button'>" + name + "</li>");
    $(".pi-otherTag").append(poi);
    poi.click(function () {
        if (poi.attr("class") == 'pi-tagitem on _j_tagitem') {
            poiId = null;
            poi.attr("class", "pi-tagitem _j_tagitem");
        }
        else {
            $(".pi-tagitem").attr("class", "pi-tagitem _j_tagitem");
            poi.attr("class", "pi-tagitem on _j_tagitem");
            poiId = poi.attr("poi-id");
        }
    });
}

$(function () {
    $.fn.extend({
        // 获取当前光标位置的方法
        getCurPos:function() {
            var getCurPos = '';
            if ( navigator.userAgent.indexOf("MSIE") > -1 ) {  // IE
                // 创建一个textRange,并让textRange范围包含元素里所有内容
                var all_range = document.body.createTextRange();all_range.moveToElementText($(this).get(0));$(this).focus();
                // 获取当前的textRange,如果当前的textRange是一个具体位置而不是范围,则此时textRange的范围是start到end.此时start等于end
                var cur_range = document.selection.createRange();
                // 将当前textRange的start,移动到之前创建的textRange的start处,这时,当前textRange范围就变成了从整个内容的start处,到当前范围end处
                cur_range.setEndPoint("StartToStart",all_range);
                // 此时当前textRange的Start到End的长度,就是光标的位置
                curCurPos = cur_range.text.length;
            } else {
                // 获取当前元素光标位置
                curCurPos = $(this).get(0).selectionStart;
            }
            // 返回光标位置
            return curCurPos;
        },
        txtaAutoHeight: function () {
            return this.each(function () {
                var $this = $(this);
                if (!$this.attr('initAttrH')) {
                    $this.attr('initAttrH', $this.outerHeight());
                }
                setAutoHeight(this).on('input', function () {
                    setAutoHeight(this);
                });
            });
            function setAutoHeight(elem) {
                var $obj = $(elem);
                return $obj.css({ height: $obj.attr('initAttrH'), 'overflow-y': 'hidden' }).height(elem.scrollHeight);
            }
        }
    });

    activeText = $(".textarea");
    $(".textarea").focus(function () {
        activeText = $(this);
    });
    $(".add-title").click(function() {
        clickTitle();
    });
    $("._j_submit_paragraph").click(function () {
        addTitle();
    });
    $("#file").change(function () {
        selectPic();
    });
    $("#top_image_input").change(function () {
        setTopImage();
    });

    $("#mask").click(function () {
        $("#mask").fadeOut(250);
        $(".add-panel-title").fadeOut(250);
    });

    //发表游记
    $("#btn-publish").click(function () {
        clickSubmit();
    });

    //提交弹出框
    $("#date-input").calendar();
    $("#popup_close").click(function () {
        $("#submit-mask").fadeOut(250);
    });
    $(".btn-submit").click(function () {
        if (!testNum($("#days-input").val()) || $("#days-input").val() == 0) {
            logError("请输入正确的天数");
            return;
        }
        if (!testNum($("#money-input").val()) ||  $("#money-input").val() == 0) {
            logError("请输入正确的花费金额");
            return;
        }
        if (testBlank($("#date-input").val())) {
            logError("请输入正确的开始日期");
            return;
        }
        submitNote();
    });

    //保存草稿
    $(".btn-save").click(function () {
        saveNote();
    });

    $(".textarea").txtaAutoHeight();// 调用

    setContent();
    autoSave();
    //滚动事件
    var sideTopOffset = $("#sidebar").offset().top;
    var sideLeftOffset = $("#sidebar").offset().left;
    $(window).scroll(function(event){
        if ($(window).scrollTop() > sideTopOffset) {
            $("#sidebar").attr("class", "sidebar-fixed");
            $("#sidebar").css({left:sideLeftOffset});
        }
        else {
            $("#sidebar").attr("class", "sidebar");
            $("#sidebar").css({left:0});
        }
    });
});

//自动保存
var saveTime = 10;
function autoSave() {
    if (saveTime === 0) {
        saveNote();
        saveTime = 60;
    } else {
        saveTime--;
        setTimeout(function () {autoSave();},1000);
    }
}



//页面加载的时候设置内容
function setContent() {
    var first = true;
    $(content).each(function (i, item) {
        if (item['text'] != undefined) {
            var text = item['text'].replace(/<br\/>/g, "\n");
            if (first) {
                $("#textarea").val(text);
                setAutoHeight($("#textarea"));
                first = false;
            }
            else {
                var newText = $(textBox);
                newText.find("textarea").txtaAutoHeight();
                newText.find("textarea").val(text);
                newText.find("textarea").focus(function () {
                    activeText = newText.find("textarea");
                });
                $("#_j_content_box").append(newText);
                setAutoHeight(newText.find("textarea"));
            }
        }
        else if (item['title'] != undefined) {
            var title = $(titleBox);
            title.attr("id", "title-" + index);
            index++;
            title.find("h2 span").text(item['title']);
            title.find("h2").show();
            //添加点击事件
            title.find("._j_paragraph_title_delete").click(function () {
                title.hide(250, function () {
                    title.prev().find("textarea").val(title.prev().find("textarea").val() + "\n" + title.next().find("textarea").val());
                    setAutoHeight(title.prev().find("textarea"));
                    title.next().remove();
                    title.remove();
                    refreshTitle();
                });
            });
            title.find("._j_paragraph_title_edit").click(function () {
                activeTitle = title;
                $(".a-inptxt").val(title.find("h2 span").text());
                $("#mask").fadeIn(250);
                $(".add-panel-title").fadeIn(250);
                refreshTitle();
            });
            $("#_j_content_box").append(title);
        }
        else {
            var pic = $(picBox);
            pic.find("img").attr("src", item['pic']);
            //点击事件
            pic.find(".go_set_page").click(function () {
                $("#top_image").attr("src", pic.find("img").attr("src"));
                $(".set_page").hide();
                $(".set_btn").show();
            });
            pic.find("i").click(function () {
                pic.hide(250, function () {
                    pic.prev().find("textarea").val(pic.prev().find("textarea").val() + "\n" + pic.next().find("textarea").val());
                    setAutoHeight(pic.prev().find("textarea"));
                    pic.next().remove();
                    pic.remove();
                });
            });
            $("#_j_content_box").append(pic);
        }
    });
    refreshTitle();
}

function testBlank(str) {
    str = str.replace(/\s+/g, "");
    var reg = /\n/g;
    str = str.replace(reg, "");
    return str.length == 0;
}

function testNum(str) {
    var re = /^[0-9]+$/ ;
    return re.test(str)
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}