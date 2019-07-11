//注册登录页面
$(document).ready(function() {
	var ab05CurrPage=$("#ab05page");
	var aa02CurrPage=$("#aa02page");
	var ab04CurrPage=$("#ab04page");
	$(function() { 
		ab05Select();
		aa02Select();
		ab04Select();
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
	        	$("#ab05Result tbody").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aab501+"</td><td>"+ls.aaa101+"</td><td>"+ls.aab502+"</td><td>"+ls.aab503+
	            	     "</td><td>"+ls.aab504+"</td><td>"+ls.aab505+"</td><td>"+ls.aab506+
	            	     "</td><td>"+ls.aab507+"</td><td>"+ls.aab508+"</td>"+
						 "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aab501+",1,\"http://localhost:8080/admin/ab05/changeState\",\"ab05\")' href=\"javascript:;\">"+
                         "<i class=\"layui-icon\">&#xe63c;</i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aab501+",2,\"http://localhost:8080/admin/ab05/changeState\",\"ab05\")' href=\"javascript:;\">"+
                         "<i class=\"layui-icon\">&#xe640;</i></a>"+
	            	     "</td></tr>";
	            	$("#ab05Result tbody").append(html);
	            }
	            $("#ab05currPage").html(result.currPage);
	            $("#ab05tolPage").html(result.tolPage);
	        },
	        error: function (e) {
	        	
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
	        	$("#aa02Result tbody").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];
	            	html="<tr><td>"+ls.aaa201+"</td><td>"+ls.aaa202+"</td><td>"+ls.aaa203+
	            	     "</td><td>"+ls.aaa204+"</td><td>"+ls.aaa205+"</td><td>"+ls.aaa206+
	            	     "</td><td>"+ls.aaa207+"</td><td>"+ls.aaa208+"</td><td>"+ls.aaa209+"</td><td>"+ls.aaa210+"</td>"+
						 "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aaa201+",1,\"http://localhost:8080/admin/aa02/changeState\",\"aa02\")' href=\"javascript:;\">"+
                         "<i class=\"layui-icon\">&#xe63c;</i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aaa201+",2,\"http://localhost:8080/admin/aa02/changeState\",\"aa02\")' href=\"javascript:;\">"+
                         "<i class=\"layui-icon\">&#xe640;</i></a>"+
	            	     "</td></tr>";
	            	$("#aa02Result tbody").append(html);
	            }
	            $("#aa02currPage").html(result.currPage);
	            $("#aa02tolPage").html(result.tolPage);
	        },
	        error: function (e) {
	        	
	        }
	    }); 
		}	
	
	function ab04Select(){
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/admin/ab04/selectByPage",
	        data:JSON.stringify({currPage:ab04CurrPage.val()}),
	        contentType: "application/json",
	        dataType: "json",
	        async: true,
	        success: function (result) {
	        	var data=result.data;
	        	var count=data.length;
	        	var html=""; 
	        	$("#ab04Result tbody").html("");
	        	rowlen=0;
	            for(var i=0;i<count;i++)
	            {
	            	var ls=data[i];  //"<input type='checkbox' name='' lay-skin='primary' >"复选框
	            	html="<tr><td>"+ls.aab401+"</td><td>"+ls.aaa201+"</td><td>"+ls.aab402+
	            	     "</td><td>"+ls.aab403+"</td><td>"+ls.aab404+"</td><td>"+ls.aab405+
	            	     "</td><td>"+ls.aab406+"</td><td>"+ls.aab407+"</td>"+ 
						 "<td class='td-manage'>"+
                         "<a title=\"通过\" onclick='check("+ls.aab401+",1,\"http://localhost:8080/admin/ab04/changeState\",\"ab04\")' href=\"javascript:;\">"+
                         "<i class=\"layui-icon\">&#xe63c;</i></a>"+
                         "<a title=\"拒绝\" onclick='check("+ls.aab401+",2,\"http://localhost:8080/admin/ab04/changeState\",\"ab04\")' href=\"javascript:;\">"+
                         "<i class=\"layui-icon\">&#xe640;</i></a>"+
	            	     "</td></tr>";
	            	$("#ab04Result tbody").append(html);
	            }
	            $("#ab04currPage").html(result.currPage);
	            $("#ab04tolPage").html(result.tolPage);
	        },
	        error: function (e) {
	        	
	        }
	    }); 
		}
	
	
 ab05SelectTest=ab05Select;
 aa02SelectTest=aa02Select;
 ab04SelectTest=ab04Select;
	
	
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
		case "ab04":ab04SelectTest(); break;
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
		case "ab04":ab04SelectTest(); break;
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
		case "ab04":ab04SelectTest(); break;
	}
}

