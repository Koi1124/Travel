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
		ab04Select();
	})
	function ab04Select(){
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/admin/ab04/selectByPage",
	        data:JSON.stringify({currPage:currPage.text()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	var data=result.data;
	        	var count=data.length;
	        	var html=""; 
	        	$("#ab04Result tbody").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];  //"<input type='checkbox' name='' lay-skin='primary' >"复选框
	            	html="<tr><td>"+ls.aab401+"</td><td>"+ls.aaa201+"</td><td>"+ls.aab402+
	            	     "</td><td>"+ls.aab403+"</td><td>"+ls.aab404+"</td><td>"+ls.aab405+
	            	     "</td><td>"+ls.aab406+"</td><td>"+ls.aab407+"</td>"+ 
	            	     "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aab401+",1)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left' >&#xe6b1;</i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aab401+",2)' href=\"javascript:;\">"+
                         "<i class=\"iconfont\" style='float:left;margin-left:10px;'>&#xe69a;</i></a>"+
	            	     "</td></tr>";
	            	$("#ab04Result tbody").append(html);
	            }
	            $("#pageNum").text(result.currPage);
	            $("#tolPage").text(result.tolPage);
	        },
	        error: function (e) {
	        	
	        }
	    }); 
		}
 ab04SelectTest=ab04Select;
 
	
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
	ab04SelectTest();

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
	ab04SelectTest(); 
}

function check(id,state)
{
	$.ajax({
		type:"post",
		url: "http://localhost:8080/admin/ab04/changeState",
		data: JSON.stringify({state:state,id:id}),
	    contentType: "application/json",
	    dataType: "json",
	    async: true,
	    success: function (data) {
	    	toastr.success('审核成功！');
	    },
	    error: function (e) {
	    	toastr.error('审核失败！')
	    }	
	});
		ab04SelectTest(); 
}


