//获取当前网址，如： http://localhost:80/ybzx/index.jsp  
var curPath=window.document.location.href;
//获取主机地址之后的目录，如： ybzx/index.jsp  
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址，如： http://localhost:80  
var localhostPaht=curPath.substring(0,pos);


$(document).ready(function() {
    $.ajax({
        url: localhostPaht + "/together/mdd_info",
        type: "post",
        dataType: "json",
        async: true,
        success: function (data) {
            var avaliableTags = eval(data);
            $("#_j_go_mdd").autocomplete({
                minLength:0,
                source: function (request,response) {
                    response($.ui.autocomplete.filter(
                        avaliableTags,extractLast(request.term)
                    ));
                },
                message:{
                    results: function(){
                    },
                    noResults: ''
                },
                focus: function () {
                    return false;
                },
                select:function (event,ui) {
                    var terms = split(this.value);
                    var temp=split(this.id);
                    terms.pop();
                    temp.pop();
                    terms.push(ui.item.value);
                    temp.push(ui.item.id);
                    terms.push("");
                    temp.push("");
                    this.value=terms.join(";");
                    this.id=temp.join(";");
                    $("#go_mdd_id").val(this.id);
                    return false;
                }
            })

            $("#_j_from_mdd").autocomplete({
                source:avaliableTags,
                message:{
                    results: function(){
                    },
                    noResults: ''
                },
                select:function (event,ui) {
                    $("#from_mdd_id").val(ui.item.id);
                }
            })
        },
        error: function (e) {
            console.log(e);
        }
    });
});

function extractLast( term ) {
    return split( term ).pop();
};

function split( val ) {
    return val.split( /;\s*/ );
}


