//注册登录页面
$(document).ready(function() {
	var currPage=$("#pageNum");
	toastr.options = 
	{
        positionClass: "toast-top-center",
        closeButton: true,
        timeOut:"1000",
    };
	$(function() { 
		aa02Select();
	})
	
	function aa02Select(){
		$.ajax({
	        type: "post",
	        url: "/admin/aa02/selectByPage",
	        data:JSON.stringify({currPage:currPage.text()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	var data=result.data;
	        	var count=data.length;
	        	var html=""; 
	        	$("#aa02Result tbody").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aaa201+"</td><td>"+ls.aaa202+"</td><td>"+ls.aaa203+
	            	     "</td><td>"+ls.aaa204+"</td><td>"+ls.aaa205+"</td><td>"+ls.aaa206+
	            	     "</td><td>"+ls.aaa207+"</td><td>"+ls.aaa208+"</td><td>"+ls.aaa209+"</td><td>"+ls.aaa210+"</td>"+
	            	     "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aaa201+",1)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left' >&#xe6b1;</i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aaa201+",2)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left;margin-left:10px;'>&#xe69a;</i></a>"+
	            	     "</td></tr>";
	            	$("#aa02Result tbody").append(html);
	            }
	            $("#pageNum").text(result.currPage);
	            $("#tolPage").text(result.tolPage);
	        },
	        error: function (e) {
	        	
	        }
	    }); 
		}	
 aa02SelectTest=aa02Select;	
});
function add(page,max)
{
	var now=$(page).text();
	now++;
	if(now>=max)
	{
		now=max;
	}
	$(page).text(now);

    aa02SelectTest(); 

}
function decrease(page)
{
	var now=$(page).text();
	now--;
	if(now<=0)
	{
		now=1;
	}
	$(page).text(now);
	aa02SelectTest(); 
}

function check(id,state)
{
	$.ajax({
		type:"post",
		url: "/admin/aa02/changeState",
		data: JSON.stringify({state:state,id:id}),
	    contentType: "application/json",
	    dataType: "json",
	    async: true,
	    success: function (data) {
	    	toastr.success('审核成功！')
	    },
	    error: function (e) {
	    	toastr.success('审核失败！')
	    }	
	});
		aa02SelectTest(); 
}


