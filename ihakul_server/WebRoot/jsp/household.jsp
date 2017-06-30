<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾后台管理系统-家电管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery.easyui.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
	
</head>
<body>
<div class="main_title"><i></i><a href="">小艾</a>><a href="" ><strong>电器</strong></a></div>

<div class="container">
	
	<ul class="ad_list_search clearfix">
		<li class="name"><label>家电名字</label><input type="text" name="eaName" value=""></li>
		<li class="name"><label>品牌：</label><input type="text" name="brand" value=""></li>
		<li class="search_btn"><button id="query">查询</button></li>
		
	</ul>
	
	<table id="tb1" class="table ms_b ">
		
	</table>

</div>

<script type="text/javascript">
$(document).ready(function(){
	$("#query").click(function(){
		showHouseHold();
	});
	

});
//查询显示
function showHouseHold(){
	$.ajax({
			url:"${pageContext.request.contextPath }/findHouseHold.action",
			type:"post",  
			cache:false, 
			data:{"eaName":$("input[name='eaName']").val(),brand:$("input[name='brand']").val(),pageNow:1,showPage:5,se:1},  /*data参数,以json格式为准  */
			dataType:"json",  /*返回的格式  */
			success:function(json){
				$("#tb1").html("");
				$("#tb1").append("<tr><th>序号</th><th>名字</th><th>种类</th><th>品牌</th><th>型号</th><th>操作</th></tr>");
				if(json !=null){
					$.each(json.houseList,function(key,houseHold){
						var no=key+1;
						$("#tb1").append("<tr><td>"+no+"</td><td>"+houseHold.hhName+"</td>"+
						"<td>"+houseHold.housetype.htName+"</td>"+"<td><a href='' class='operate' >"+
						houseHold.brand+"</a></td><td>"+houseHold.model+"</td>"+"<input type='hidden' id='hid' value='"+houseHold.hid+"' >"+
						"<td><a href='#' id='sq' onclick='deleteHh("+houseHold.hid+")' class='operate'>删除</a><a href='getHousehold.action?hid="+houseHold.hid+"' class='operate'>"+
						"详情</a></td></tr>");
					});
				};
			},
			error:function(){
				alert("返回数据失败");
			},
		});


};

//删除
function deleteHh(id){
		alert("开始删除");
		$.ajax({
			url:"${pageContext.request.contextPath }/deleteHousehold.action",
			type:"post",
			cache:false,
			data:{hid:id,se:1},
			dataType:"json",
			success:function(json){
				if(json.fals){
					alert("删除成功");
					//window.location.reload();//刷新当前页面
					showHouseHold();
				}else{
					alert("删除失败");
					showHouseHold();
					//	window.location.reload();//刷新当前页面
				}
			},
			error:function(){
				alert("返回数据失败");
			},
		});
	};	
	
</script>

</body>
</html>