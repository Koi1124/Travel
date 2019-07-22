//注册登录页面
$(document).ready(function() {
	var username=$("#username");
	var pwd=$("#password");
	var rpwd=$("#rpassword");
	var email=$("#email");
	var role=$('input:radio:checked');
	toastr.options = 
	{
        positionClass: "toast-top-center",
        closeButton: true,
        timeOut:"1000",
    };
	$(function() { 
	})
	
	
	
	
	function ab03Select(id){
		$.ajax({
	        type: "post",
	        url: "/ab03/queryById",
	        data:JSON.stringify({id:id}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	$("#parentId").val(result.aaa301);
	        	$("#name").val(result.aab302);
	        	$("#intro").val(result.aab303);
	        	$("#tel").val(result.aab304);
	        	$("#web").val(result.aab305);
	        	$("#timeReference").val(result.aab306);
	        	$("#tran").val(result.aab307);
	        	$("#ticket").val(result.aab308);
	        	$("#openTime").val(result.aab309);
	        	$("#location").val(result.aab310);
	        },
	        error: function (e) {
	        	console.log(id);
	        }
	    }); 
		}
	
	
	
});

function ab03Update(operation)
{
	var url="/ab03/updateById";
	if(operation=='1')
	{
		url="/ab03/insertAttraction";
	}
	var a_id=$("#attraction_id");
		$.ajax({
	        type: "post",
	        url: url,
	        data:JSON.stringify({id:a_id.val(),
	        		aaa301:$("#parentId").val(),
	        		aab302:$("#name").val(),
	        		aab303:$("#intro").val(),
	        		aab304:$("#tel").val(),
	        		aab305:$("#web").val(),
	        		aab306:$("#timeReference").val(),
	        		aab307:$("#tran").val(),
	        		aab308:$("#ticket").val(),
	        		aab309:$("#openTime").val(),
	        		aab310:$("#location").val(),		}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (data) {
	        		if(operation=='1')
	        		{
	        			toastr.success('添加成功！');
	        		}else
	        		{
	        			toastr.success('更新成功！');
	        		}
	        		setTimeout(function () {
	        		xadmin.close();
    	        	xadmin.father_reload();}, 1000);
                	
   	
	        },
	        error: function (data) {
	        	toastr.error('网络故障！');
	        }
	    });
        
}






