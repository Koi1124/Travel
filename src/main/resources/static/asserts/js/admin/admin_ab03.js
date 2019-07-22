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
		fuzzyQuery($('#name').val(),$('#intro').val());
	})
	
	function fuzzyQuery(name,intro){
		var count=0;
		$.ajax({
	        type: "post",
	        url: "/ab03/fuzzyQuery",
	        data:JSON.stringify({currPage:currPage.text(),name:name,intro:intro}),
	        contentType: "application/json",
	        dataType: "json",
	        async: false,
	        success: function (result) {
	        		var data=result.data;
	        		count=data.length;
	        		var html=""; 
		        	$("#ab03Result tbody").html("");
		        	rowlen=0;
		            for(var i=0;i<count;i++)
		            {
		            	
		            	var ls=data[i];
		            	html="<tr><td>"+ls.aaa302+"</td><td>"+ls.aab302+"</td><td>"+
		            	 	 "<p><a href='#' onclick='xadmin.open(\"显示详情\",\" /show?msg="+encodeURIComponent(ls.aab303)+"  \",300,220)'>"+ls.aab303+
		            	     "</a></p></td><td>"+ls.aab304+"</td><td>"+ls.aab305+"</td><td>"+ls.aab306+"</td><td>"+
		            	     "<p><a href='#' onclick='xadmin.open(\"显示详情\",\" /show?msg="+encodeURIComponent(ls.aab307)+"  \",300,220)'>"+ls.aab307+
		            	     "</a></p></td><td><p><a href='#' onclick='xadmin.open(\"显示详情\",\" /show?msg="+encodeURIComponent(ls.aab308)+"  \",300,220)'>"+ls.aab308+
		            	     "</a></p></td><td><p><a href='#' onclick='xadmin.open(\"显示详情\",\" /show?msg="+encodeURIComponent(ls.aab309)+"  \",300,220)'>"+ls.aab309+
		            	     "</a></p></td><td><p><a href='#' onclick='xadmin.open(\"显示详情\",\" /show?msg="+encodeURIComponent(ls.aab310)+"  \",300,220)'>"+ls.aab310+"</a></p></td>"+
		            	     "<td>"+ls.aab311+"</td><td>"+ls.aab312+"</td>"+
		            	     "<td class='td-manage'>"+
		            	     "<button class='layui-btn layui-btn layui-btn-xs'  onclick='xadmin.open(\"编辑\",\"   /editAttraction?id="+ls.aab301+"&operation=2    \",600,600)'>"+
		            		 "<i class='layui-icon'>&#xe642;</i>编辑</button><br>"+
	                         "<button class='layui-btn-danger layui-btn layui-btn-xs'  onclick='delAttraction("+ls.aab301+")'>"+
	                         "<i class='layui-icon'>&#xe640;</i>删除</button>"+
	                         "</td></tr>";
		            	     
		            	$("#ab03Result tbody").append(html);
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
	fuzzyQueryTest($("#name").val(),$("#intro").val()); 
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
	fuzzyQueryTest($("#name").val(),$("#intro").val()); 
}


function delAttraction(id)
{
	$.ajax({
		type:"post",
		url: "/ab03/delAttraction",
		data: JSON.stringify({id:id}),
	    contentType: "application/json",
	    dataType: "json",
	    async: false,
	    success: function (data) {
	    	toastr.success('删除成功！');
	    },
	    error: function (e) {
	    	toastr.error('删除失败！');
	    }	
	});
	fuzzyQueryTest($("#name").val(),$("#intro").val());
}

function find(name,intro)
{
	var count=fuzzyQueryTest(name,intro);
	if(count>0)
	{
		toastr.success('查询成功');
	}
	else
	{
		toastr.error('查询失败');
	}
}
