//注册登录页面
$(document).ready(function() {
	var a_id=$("#attraction_id");
	var a_op=$("#attraction_operation");
	toastr.options = 
	{
        positionClass: "toast-top-center",
        closeButton: true,
        timeOut:"1000",
    };

	$(function() { 
		if(a_op.val()==2)
		{
			ab03Select(a_id.val());
		}
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
	        	$("#intro").val(result.aab303.replace(/<br\/>/g,"\n"));
	        	$("#tel").val(result.aab304);
	        	$("#web").val(result.aab305);
	        	$("#timeReference").val(result.aab306);
	        	$("#tran").html(result.aab307);
	        	$("#ticket").html(result.aab308.replace(/<br\/>/g,"\n"));
	        	$("#openTime").html(result.aab309.replace(/<br\/>/g,"\n"));
	        	$("#location").html(result.aab310);
	        	$("#longitude").val(result.aab311);
	        	$("#latitude").val(result.aab312);
	        	$("#img").attr('src',result.aab313);
	        },
	        error: function (e) {
	        	console.log(id);
	        }
	    }); 
		}
	
	
	
});

function exist(){
	console.log("blur");
	$.ajax({
        type: "post",
        url: "/aa03/exist",
        data:JSON.stringify({id:$("#parentId").val()}),
        contentType: "application/json",
        dataType: "json",
        async: true,
        success: function (data) {
        		if(!data)
        		{
        			toastr.warning("城市ID不存在!");
        		}
        },
        error: function (data) {
        	toastr.error('城市信息错误或禁止访问!');
        }
    });
}
    


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
	        		aab310:$("#location").val(),
	        		aab311:$("#longitude").val(),
	        		aab312:$("#latitude").val(),
	        		aab313:$("#img").attr('src'),
	        		}),
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








