//注册登录页面
$(document).ready(function() {
	var ab05CurrPage=$("#ab05page");
	var aa02CurrPage=$("#aa02page");
	$(function() { 
		ab05Select();
		aa02Select();
	})
	
	function ab05Select(){
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/admin/ab05/selectByPage",
	        data:JSON.stringify({currPage:ab05CurrPage.val()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	var data=result.data;
	        	var count=data.length;
	        	var html=""; 
	        	$("#ab05Result").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aab501+"</td><td>"+ls.aab502+"</td><td>"+ls.aab503+
	            	     "</td><td>"+ls.aab504+"</td><td>"+ls.aab505+"</td><td>"+ls.aab506+
	            	     "</td><td>"+ls.aab507+"</td><td>"+ls.aab508+"</td><td>"+
	            	     "<input type='button' value='通过'  onclick='check("+ls.aab501+",1,\"http://localhost:8080/admin/ab05/changeState\",\"ab05\")'  />"+
	            	     "<input type='button' value='拒绝'  onclick='check("+ls.aab501+",2,\"http://localhost:8080/admin/ab05/changeState\",\"ab05\")'  />"+
	            	     "</td></tr>";
	            	$("#ab05Result").append(html);
	            }
	            $("#ab05currPage").html(result.currPage);
	            $("#ab05tolPage").html(result.tolPage);
	        },
	        error: function (e) {
	        	alert("没有未被审核的数据");
	        }
	    }); 
		}

	function aa02Select(){
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/admin/aa02/selectByPage",
	        data:JSON.stringify({currPage:aa02CurrPage.val()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	var data=result.data;
	        	var count=data.length;
	        	var html=""; 
	        	$("#aa02Result").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aaa201+"</td><td>"+ls.aaa202+"</td><td>"+ls.aaa203+
	            	     "</td><td>"+ls.aaa204+"</td><td>"+ls.aaa205+"</td><td>"+ls.aaa206+
	            	     "</td><td>"+ls.aaa207+"</td><td>"+ls.aaa208+"</td><td>"+ls.aaa209+"</td><td>"+ls.aaa210+"</td><td>"+
	            	     "<input type='button' value='通过'  onclick='check("+ls.aaa201+",1,\"http://localhost:8080/admin/aa02/changeState\",\"aa02\")'  />"+
	            	     "<input type='button' value='拒绝'  onclick='check("+ls.aaa201+",2,\"http://localhost:8080/admin/aa02/changeState\",\"aa02\")'  />"+
	            	     "</td></tr>";
	            	$("#aa02Result").append(html);
	            }
	            $("#aa02currPage").html(result.currPage);
	            $("#aa02tolPage").html(result.tolPage);
	        },
	        error: function (e) {
	        	alert("没有未被审核的数据");
	        }
	    }); 
		}	
	
	
	
 ab05SelectTest=ab05Select;
 aa02SelectTest=aa02Select;

	
	
});
function add(page,max,tableName)
{
	var now=$(page).val();
	now++;
	if(now>=max)
	{
		now=max;
	}
	$(page).val(now);
	
	switch(tableName)
	{
		case "ab05":ab05SelectTest(); break;
		case "aa02":aa02SelectTest(); break;
	}
}
function decrease(page,tableName)
{
	var now=$(page).val();
	now--;
	if(now<=0)
	{
		now=1;
	}
	$(page).val(now);
	switch(tableName)
	{
		case "ab05":ab05SelectTest(); break;
		case "aa02":aa02SelectTest(); break;
	}
}

function check(id,state,url,tableName)
{
	$.ajax({
		type:"post",
		url: url,
		data: JSON.stringify({state:state,id:id}),
	    contentType: "application/json",
	    dataType: "json",
	    async: true,
	    success: function (data) {
	        console.log("已审核");
	    },
	    error: function (e) {
	        console.log("审核失败");
	    }	
	});
	
	switch(tableName)
	{
		case "ab05":ab05SelectTest(); break;
		case "aa02":aa02SelectTest(); break;
	}
}

