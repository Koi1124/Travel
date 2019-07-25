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
		            	 	 "<p>"+ls.aab303+
		            	     "</p></td><td>"+changeEmpty(ls.aab304)+"</td><td>"+changeEmpty(ls.aab305)+"</td><td>"+changeEmpty(ls.aab306)+"</td><td>"+
		            	     "<p>"+ls.aab307+
		            	     "</p></td><td><p>"+ls.aab308+
		            	     // "</p></td><td><p>"+ls.aab309+
		            	     "</a></p></td><td><p>"+ls.aab310+"</p></td>"+
                             "<td width='50px' class='td-manage'>" +
                             "<a title='查看详情' onclick=\"xadmin.open('查看详情','/admin/sight/preview/"+ls.aab301+"')\">" +
                             "<i class='layui-icon layui-icon-link' style=' font-size: 23px;margin-left:20px '  ></i></a>" +
                             "</a></td>"+
		            	     "<td class='td-manage'>"+
		            	     "<button class='layui-btn layui-btn layui-btn-xs'  onclick='xadmin.open(\"编辑\",\"/admin/sight/"+ls.aab301+"\")'>"+
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

function endPage(page)
{
	$(page).text($("#tolPage").text());
	fuzzyQueryTest($("#name").val(),$("#intro").val());
}
function startPage(page)
{
	$(page).text(1);
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
function changeEmpty(str)
{
	return (str==null)?'':str;
}