var activeText = null;//当前激活的文本框
var curCurPos = 0;//当前的光标位置
var activeTitle = null;
var index = 1;//title编号，用来右侧栏跳转

var base64 = new Array();
var picCount = 0;
var picLoad = 0;

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

//
function clickSubmit() {
    if (testBlank($("#_j_title").val())) {

    }
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

    $(".textarea").txtaAutoHeight();// 调用


    //滚动锦亭事件
    $(window).scroll(function(event){
        if ($(window).scrollTop() > $("#sidebar").offset().top) {
            $("#sidebar").css({'padding-top':($(window).scrollTop() - $("#sidebar").offset().top + 10) + "px"});
        }
    });
});


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

function testBlank(str) {
    str = str.replace(" ", "");
    return str.length == 0;
}