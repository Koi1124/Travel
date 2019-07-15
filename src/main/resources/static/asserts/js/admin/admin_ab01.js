//注册登录页面
$(document).ready(function() {
	var currPage=$("#pageNum");
	toastr.options = {
	        positionClass: "toast-top-center",
	        hideDuration: "1000",
	        closeButton: true,
	    };
	$(function() { 
		ab01Select();
	})
	
	function ab01Select(){
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/admin/ab01/selectByPage",
	        data:JSON.stringify({currPage:currPage.text()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	var data=result.data;
	        	var count=data.length;
	        	var html=""; 
	        	$("#ab01Result tbody").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aab101+"</td><td>"+ls.aaa101+"</td><td>"+ls.aab102+
	            	     "</td><td>"+ls.aab104+"</td><td>"+ls.aab105+"</td><td>"+ls.aab106+
	            	     "</td><td>"+ls.aab107+"</td><td>"+ls.aab108+"</td><td>"+ls.aab109+
	            	     "</td><td>"+ls.aab110+"</td><td>"+ls.aab111+"</td>"+
						 "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aab101+",1)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left' >&#xe6b1;</i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aab101+",2)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left;margin-left:10px;'>&#xe69a;</i></a>"+
	            	     "</td></tr>";
	            	$("#ab01Result tbody").append(html);
	            }
	            $("#pageNum").text(result.currPage);
	            $("#tolPage").text(result.tolPage);
	        },
	        error: function (e) {
	        	toastr.error("网络错误!");
	        }
	    }); 
		}
 ab01SelectTest=ab01Select;	
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
	
	ab01SelectTest();
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
	ab01SelectTest();
}

function check(id,state)
{
	$.ajax({
		type:"post",
		url: "http://localhost:8080/admin/ab01/changeState",
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
		ab01SelectTest(); 

}

