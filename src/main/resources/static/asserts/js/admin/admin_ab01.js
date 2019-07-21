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
	            	html="<tr><td>"+ls.author+"</td><td>"+ls.aab102+
	            	     "</td><td>"+ls.aab104+"</td><td>"+ls.aab105+"</td><td>"+ls.aab106+
	            	     "</td><td>"+ls.aab107+"</td><td>"+ls.poi+
	            	     "</td><td width='50px' class='td-manage'>" +
	            	     "<a title='查看详情' onclick=\"xadmin.open('查看详情','/admin/note/preview/"+ls.aab101+"')\">" +
	            	     "<i class='layui-icon layui-icon-link' style=' font-size: 23px;margin-left:20px '  ></i></a>" +
	            	     "</a></td>"+
						 "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aab101+",2)' href=\"javascript:;\">"+
                         "<i class='layui-icon layui-icon-auz' style='font-size: 23px;float:left ' ></i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aab101+",3)' href=\"javascript:;\">"+
                         "<i class='layui-icon layui-icon-close' style='font-size: 23px;float:left;margin-left:10px;'></i></a>"+
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
		url: "http://localhost:8080/note/changeStatus",
		data: JSON.stringify({status:state,nid:id}),
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

