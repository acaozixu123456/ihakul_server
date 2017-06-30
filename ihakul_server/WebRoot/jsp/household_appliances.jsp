<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾台管理系统-家电</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"></div>
	<i></i><a href="">小艾</a>><a href="">家庭组</a>><strong>家电</strong>
</div>

<div class="fga">
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150px" align="center" >序号</td>
					<td >名字</td>
				</tr>				
				<c:forEach items="${houseList }" var="house" varStatus="star">
				<tr>
					<td>${star.index+1 }</td>
					<td><a href="${pageContext.request.contextPath }/shullHousehold.action?hid=${house.hid }&pageNow=1&showPage=5">${house.eaName }</a></td>
				</tr>				
				</c:forEach>

			
</table>
	
</div>


</body>
</html>