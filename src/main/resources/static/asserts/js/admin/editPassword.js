//注册登录页面
$(document).ready(function() {
	var oldPwd=$("#oldPassword");
	var newPwd=$("#newPassword");
	var rnewPwd=$("#rnewPassword");
	var button=$("#update")
	toastr.options = {
	        positionClass: "toast-top-center",
	        hideDuration: "1000",
	        closeButton: true,
	    };

	button.click(function(){
		if(isEmpty(oldPwd.val())||isEmpty(newPwd.val())||isEmpty(rnewPwd.val()))
		{
			
			console.log("old:"+oldPwd.val());
			console.log("new:"+newPwd.val());
			console.log("rnew:"+rnewPwd.val());
			toastr.warning('密码不能为空!');
		}
		else
		{
			if(check(oldPwd.val())&&check(newPwd.val())&&check(rnewPwd.val()))
			{
				if(newPwd.val()!==rnewPwd.val())
				{
					toastr.warning('两次新密码不同');
				}
				else
				{
					$.ajax({
						type:"post",
						url: "http://localhost:8080/admin/editPassword",
						data: JSON.stringify({oldPassword:oldPwd.val(),newPassword:newPwd.val()}),
					    contentType: "application/json",
					    dataType: "json",
					    async: true,
					    success: function (data) {
					    	if(data)
					    	{
					    		toastr.success('修改成功!1s后跳转');
					    		setTimeout(function () {
					        		xadmin.close();
				    	        	xadmin.father_reload();}, 1000);	
					    	}
					    	else
					    	{
					    		toastr.warning('网络故障:修改失败！');
					    	}
					    	
					    },
					    error: function (e) {
					    	toastr.error('网络错误！')
					    }	
					});
					
					
				}				
			}
			else
			{
				toastr.warning('密码长度为8-16位!');
			}
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

function check(pwd)
{
	var length=pwd.length;
	if(length>16||length<8)
	{
		return false;
	}
	else
	{
		return true;
	}
}




