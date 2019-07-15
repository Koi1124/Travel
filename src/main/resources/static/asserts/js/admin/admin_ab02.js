//注册登录页面
$(document).ready(function() {
	var currPage=$("#pageNum");
	toastr.options = {
	        positionClass: "toast-top-center",
	        hideDuration: "500",
	        closeButton: true,
	    };
	
	function fuzzyQuery(city,name){
		var count=0;
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/ab02/fuzzyQuery",
	        data:JSON.stringify({currPage:currPage.text(),city:city,name:name}),
	        contentType: "application/json",
	        dataType: "json",
	        async: false,
	        success: function (result) {
	        		var data=result.data;
	        		count=data.length;
	        		var html=""; 
		        	$("#ab02Result tbody").html("");
		        	rowlen=0;
		            for(var i=0;i<count;i++)
		            {
		            	var ls=data[i];
		            	html="<tr><td>"+ls.aab201+"</td><td>"+ls.aab301+"</td><td>"+ls.aab202+"</td><td>"+ls.aab203+
		            	     "</td><td>"+ls.aab204+"</td><td>"+ls.aab205+"</td>"+
		            	     "<td class='td-manage'>"+
		            	     "<button class='layui-btn layui-btn layui-btn-xs'  onclick='xadmin.open(\"编辑\",\"   /editStrategy?id="+ls.aab201+"&operation=2    \")'>"+
		            		 "<i class='layui-icon'>&#xe642;</i>编辑</button>"+
	                         "<button class='layui-btn-danger layui-btn layui-btn-xs'  onclick='delStrategy("+ls.aab201+")'>"+
	                         "<i class='layui-icon'>&#xe640;</i>删除</button>"+
	                         "</td></tr>";
		            	     
		            	$("#ab02Result tbody").append(html);
		            }
		            $("#pageNum").text(result.currPage);
		            $("#tolPage").text(result.tolPage);
	        },
	        error: function (e) {
	        }
	        
	    });
		return count;
		}
	fuzzyQueryTest=fuzzyQuery;
	
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
	fuzzyQueryTest($("#city").val(),$("#name").val()); 
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
	fuzzyQueryTest($("#city").val(),$("#name").val()); 
}


function delStrategy(id)
{
	$.ajax({
		type:"post",
		url: "http://localhost:8080/ab02/delStrategy",
		data: JSON.stringify({id:id}),
	    contentType: "application/json",
	    dataType: "json",
	    async: true,
	    success: function (data) {
	    	toastr.success('删除成功！');
	    },
	    error: function (e) {
	    	toastr.error('删除失败！');
	    }	
	});
	fuzzyQueryTest($("#city").val(),$("#name").val());
}

function find(city,name)
{
	var count=fuzzyQueryTest(city,name);
	if(count>0)
	{
		toastr.success('查询成功');
	}
	else
	{
		toastr.error('查询失败');
	}
}
