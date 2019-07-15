//注册登录页面
$(document).ready(function() {
	var currPage=$("#pageNum");
	toastr.options = {
	        positionClass: "toast-top-center",
	        hideDuration: "1000",
	        closeButton: true,
	    };
	$(function() { 
		ab05Select();
	})
	
	function ab05Select(){
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/admin/ab05/selectByPage",
	        data:JSON.stringify({currPage:currPage.text()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	var data=result.data;
	        	var count=data.length;
	        	var html=""; 
	        	$("#ab05Result tbody").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aab501+"</td><td>"+ls.aaa101+"</td><td>"+ls.aab502+"</td><td>"+ls.aab503+
	            	     "</td><td>"+ls.aab504+"</td><td>"+ls.aab505+"</td><td>"+ls.aab506+
	            	     "</td><td>"+ls.aab507+"</td><td>"+ls.aab508+"</td>"+
	            	     "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aab501+",1)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left' >&#xe6b1;</i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aab501+",2)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left;margin-left:10px;'>&#xe69a;</i></a>"+
	            	     "</td></tr>";
	            	$("#ab05Result tbody").append(html);
	            }
	            $("#pageNum").text(result.currPage);
	            $("#tolPage").text(result.tolPage);
	        },
	        error: function (e) {
	        	
	        }
	    }); 
		}
 ab05SelectTest=ab05Select;	
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
	ab05SelectTest(); 


}
function decrease(page)
{
	var now=$(page).val();
	now--;
	if(now<=0)
	{
		now=1;
	}
	$(page).text(now);
	ab05SelectTest();

}

function check(id,state)
{
	$.ajax({
		type:"post",
		url: "http://localhost:8080/admin/ab05/changeState",
		data: JSON.stringify({state:state,id:id}),
	    contentType: "application/json",
	    dataType: "json",
	    async: true,
	    success: function (data) {
	    	toastr.success('审核成功！')
	    },
	    error: function (e) {
	    	toastr.error('审核失败！')
	    }	
	});
		ab05SelectTest(); 

}

