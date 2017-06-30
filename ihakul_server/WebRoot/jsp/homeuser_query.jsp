<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>小艾后台管理系统-家庭组管理</title>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/date/js/laydate.js"></script>
	

	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
	
	
</head>
<body>
<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>家电使用情况查询</strong></a></div>

<div class="container">
	<form action="${pageContext.request.contextPath }/findAllhhold.action" method="post">
	<ul class="ad_list_search clearfix">
		
		<li class="tttl">
		<input type="hidden" name="pageNow" value="1">
		<input type="hidden" name="showPage" value="5">
		<input  class="inputsss"  type="text" name="hhName"  placeholder="请输入家电名称"></li>
		
		<li class="search_btn"><button id="query">查询</button></li>
		
		
	</ul>
	</form>
    <div>
	
	<ul>
		<c:forEach items="${ddlist }" var="datadictionary" >
		<li class="foat1"><a href="">${datadictionary.ddName }</a></li>   
		
        </c:forEach>
	</ul>
	<input type="hidden" value="${refresh }" id="refresh">
	</div>
</div>




<script type="text/javascript">
$(document).ready(function(){
	var refresh=$("#refresh").val();
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="${pageContext.request.contextPath }/queryHouseUser.action";
	}
	/*$("#insert").click(function(){
		alert("asf");
		window.location.href="datadictionary_insert.jsp";
	});*/	
	});
	
</script>
</body>
</html>