//注册登录页面
$(document).ready(function() {
var currPage=$("#pageNum");
var beginTime=$("#beginTime");
var endTime=$("#endTime");
var name=$("#name");
	toastr.options = 
		{
	        positionClass: "toast-top-center",
	        closeButton: true,
	        timeOut:"1000",
	    };
	$(function() { 
		console.log("again");
		fuzzyQuery();
	})
	
	
	function fuzzyQuery(){
		var count=0;
		$.ajax({
	        type: "post",
	        url: "/admin/aa04/fuzzyQuery",
	        data:JSON.stringify({currPage:currPage.text(),name:name.val(),beginTime:beginTime.val(),endTime:endTime.val()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: false,
	        success: function (result) {
	        	var data=result.data;
	        	count=data.length;
	        	var html=""; 
	        	$("#aa04Result tbody").html("");
	            for(var i=0;i<count;i++)
	            {
	           
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aaa402+
	            	     "</td><td>"+"<select onchange='role_change("+ls.aaa401+")' class='role' id='"+ls.aaa401+"' lay-ignore>"+
	            	     "<option value='1'>高级管理员</option>"+
	            	     "<option value='2'>审核管理员</option>"+
	            	     "<option value='3'>景点攻略管理员</option>"+
	            	     "</select>" +
	            	     "</td><td>"+ls.aaa405+"</td><td>"+ls.aaa406+  
	            	     "</td><td class='td-status'><span id='state' onclick='member_stop(this,"+ls.aaa407+","+ls.aaa401+")'  " +
	            	     "class='layui-btn layui-btn-normal layui-btn-mini "+classType(ls.aaa407)+"'>"+isEnable(ls.aaa407)+"</span></td>"+
	            	     "<td class='td-manage'>"+
                         "<a title=\"删除\" onclick='del("+ls.aaa401+")' href=\"javascript:;\">"+
                         "<i class=\"layui-icon\" style='float:left;margin-left:10px;'>&#xe640;</i></a>"+
	            	     "</td></tr>";
	            	$("#aa04Result tbody").append(html);
	            	$('#'+ls.aaa401).val(ls.aaa404);
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

/*管理员-停用*/
function member_stop(obj,state,id){
      //发异步把用户状态进行更改
	          if(state==1)
	          {
		          $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
	          }
	          else
	          {
		          $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
	          }
	          
	          //修改数据库
	          (state==0)?state=1:state=0;
	          $.ajax({
	  	        type: "post",
	  	        url: "/admin/aa04/changeState",
	  	        data:JSON.stringify({id:id,state:state}),
	  	        contentType: "application/json",
	  	        dataType: "json",
	  	        async: true,
	  	        success: function (result) {
	  	        	console.log('已修改');
	  	        },
	  	        error: function (e) {
	  	        	console.log('网络故障');
	  	        }
	  	    });
	          fuzzyQueryTest();
}

function role_change(id)
{
	
	var role=$("select option:selected").val();
	console.log("role:"+role+"id"+id);
	 $.ajax({
	        type: "post",
	        url: "/admin/aa04/changeRole",
	        data:JSON.stringify({id:id,role:role}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	console.log("role change");
	        	toastr.success("修改成功");
	        },
	        error: function (e) {
	        	toastr.success("修改失败");
	        }
	    });
}

function add(page,max)
{
	var now=$(page).text();
	now++;
	if(now>=max)
	{
		now=max;
	}
	$(page).text(now);
	fuzzyQueryTest(); 


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
	fuzzyQueryTest();

}



function find()
{
	var count=fuzzyQueryTest();	
	if(count>0)
	{
		toastr.success('查询成功!');
	}
	else
	{
		toastr.error('没有符合条件的数据或禁止访问!');
	}
}

/*管理员-删除*/
function del(id)
{
	$.ajax({
		type:"post",
		url:"/admin/aa04/del",
		data:JSON.stringify({id:id}),
		contentType: "application/json",
		datatype:"json",
		async:true,
		success:function(result){
			toastr.success("删除成功");
		},
		error:function(e)
		{
			toastr.error("网络故障!");
		}
	});	
	fuzzyQueryTest();
}




function title(id)
{
	if(id==0)
	{
		return '启用';
	}
	else
	{
		return '停用';
	}
}

function isEnable(id)
{
	if(id==0)
	{
		return '已停用';
	}
	else
	{
		return '已启用';
	}
}

function classType(type)
{
	if(type==0)
	{
		return ' layui-btn-disabled';
	}
	else
	{
		return '';
	}
}

function icon(id)
{
	if(id==0)
	{
		return "&#xe601;";
	}
	else
	{
		return "&#xe62f;";
	}
}

function show(id)
{
 console.log(id);	
}