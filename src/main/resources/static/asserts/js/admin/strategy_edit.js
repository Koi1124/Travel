//注册登录页面
$(document).ready(function() {
	var a_id=$("#strategy_id");
	var a_op=$("#strategy_operation");
	toastr.options = 
	{
        positionClass: "toast-top-center",
        closeButton: true,
        timeOut:"1000",
    };

	$(function() { 
		if(a_op.val()==2)
		{
			ab02Select(a_id.val());
		}
	});
	
	function ab02Select(id){
		$.ajax({
	        type: "post",
	        url: "/ab02/queryById",
	        data:JSON.stringify({id:id}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	$("#cityId").val(result.aab301);
	        	$("#name").val(result.aab202);
	        	$("#overview").val(result.aab203);
	        	$("#detail").val(result.aab204);
	        	$("#sumarry").val(result.aab205);
	        },
	        error: function (e) {
	        	console.log(id);
	        }
	    }); 
	}
});

function ab02Update(operation)
{
	var url="/ab02/updateById";
	if(operation=='1')
	{
		url="/ab02/insertStrategy";
	}
		$.ajax({
	        type: "post",
	        url: url,
	        data:JSON.stringify({id:$("#strategy_id").val(),
	        		aab301:$("#cityId").val(),
	        		aab202:$("#name").val(),
	        		aab203:$("#overview").val(),
	        		aab204:$("#detail").val(),
	        		aab205:$("#sumarry").val(),}),
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






