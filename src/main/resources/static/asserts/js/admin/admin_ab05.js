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
	            	html="<tr><td>"+ls.author+"</td><td>"+ls.name+"</td><td>"+ls.gotime+
	            	     "</td><td>"+ls.setout+"</td><td>"+ls.destiny+"</td><td>"+ls.spendtime+"</td><td>"+ls.peoplenumber+
	            	     "</td><td>"+ls.tel+
	            	     "</td><td width='50px' class='td-manage'>" +
	            	     "<a title='查看详情' onclick='xadmin.open('查看详情','/company/"+ls.aab501+"',600,800)' href='javascript:;'>" +
	            	     "<i class='layui-icon layui-icon-link' style=' font-size: 23px;margin-left:20px ' ></i></a>" +
	            	     "</a></td>"+
	            	     "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.id+",1)' href=\"javascript:;\">"+
                         "<i class='layui-icon layui-icon-auz' style='font-size: 23px;float:left ' ></i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.id+",2)' href=\"javascript:;\">"+
                         "<i class='layui-icon layui-icon-close' style='font-size: 23px;float:left;margin-left:10px;'></i></a>"+
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

