$(function () {

    function restore() {
        if ($(".checkbox").hasClass("on")) {
            $(".checkbox").removeClass("on");
            $(".item").each(function () {
                $(this).show();
            })
        }
    }

    function focusOnly(attr,type,self) {
        if ($(self).hasClass("on")) {
            $(self).removeClass("on");
            $(".item").each(function () {
                if ($(this).attr(attr)==type) {
                    $(this).show(300);
                }
            })
        }else {
            restore();
            $(self).addClass("on");
            $(".item").each(function () {
                if ($(this).attr(attr)==type) {
                    $(this).hide(300);
                }
            })
        }
    }


    $("._publish_btn").click(function () {
        focusOnly("action-type","participator",this);
    });
    
    $("._join_btn").click(function () {
        focusOnly("action-type","publisher",this);
    })

    $("._star_btn").click(function () {
        focusOnly("star-type","unstar",this);
    })
    
})