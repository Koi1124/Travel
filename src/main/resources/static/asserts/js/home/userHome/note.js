/// 用户的个人中心
var activeNote = null;
var noteId = null;
//==============作者操作==================================================
function clickDeleteNote(e) {
    $("#_j_layer_0").fadeIn(250);
    noteId = $(e).attr("note-id");
    activeNote = $(e).closest(".common_block");
}

function deleteNote() {
    $.ajax({
        type: "post",
        url: "/note/changeStatus",
        contentType: "application/json",
        dataType: "json",
        data:JSON.stringify({
            nid:noteId,
            status:9
        }),
        async: true,
        success: function (result) {
            if (result) {
                logSuccess("删除成功");
                activeNote.hide(250, function () {
                    activeNote.remove();
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

function deleteCancle() {
    $("#_j_layer_0").fadeOut(250);
}