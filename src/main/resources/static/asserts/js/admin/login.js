//注册登录页面
$(document).ready(function() {
	var username=$("#username");
	var password=$("#password");
	var login=$("#login");
	toastr.options = {
	        positionClass: "toast-top-center",
	        hideDuration: "1000",
	        closeButton: true,
	    };

	login.click(function(){
		if(isEmpty(username.val())||isEmpty(password.val()))
		{

			toastr.warning('请输入用户名和密码!');
		}
		else
		{
			$.ajax({
				type:"post",
				url: "http://localhost:8080/admin/check",
				data: JSON.stringify({username:username.val(),password:password.val()}),
			    contentType: "application/json",
			    dataType: "json",
			    async: true,
			    success: function (data) {
			    	if(data)
			    	{
			    		toastr.success('欢迎管理员！1s后跳转');
			    		setTimeout(function () {location.href="/admin/login";}, 1000);
			    		
			    	}
			    	else
			    	{
			    		toastr.warning('用户名或密码错误！');
			    	}
			    	
			    },
			    error: function (e) {
			    	toastr.error('网络错误！')
			    }	
			});
		}
		});
		
			
});

function isEmpty(em)
{
	if(em==null||em==''||em==undefined)
	{
		return true;
	}
	else
	{
		return false;
	}

}





