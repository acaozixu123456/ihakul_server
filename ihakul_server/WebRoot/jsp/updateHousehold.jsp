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
	<title>机器人小艾后台管理系统-家电管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href="household.jsp">返回&gt;&gt;</a></div>
	<i></i><a href="">小艾</a>><a href="">家电管理</a>><a href=""><strong>详情</strong></a>
</div>

<div class="container">
	<form action="${pageContext.request.contextPath }/updateHousehold.action" method="post">
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150">名称：</td>
					<td><input type="text" name="hhName" value="${houseHold.hhName }">
						<input type="hidden" name="hid" value="${houseHold.hid }">
					</td>
				</tr>
				<tr>
					<td>家庭组名：</td>
					<td><input type="text" name="groupName" value="${houseHold.familygroup.groupName }"></td>
				</tr>
				<tr><td>种类：</td>
					<td><input type="text" name="houseType" value="${houseHold.housetype.htName} "></td></tr>				
				<tr>
					<td>品牌：</td>
					<td><input type="text" name="brand" value="${houseHold.brand }"></td>
				</tr>
				<tr>
					<td>型号：</td>
					<td><input type="text" name="model" value="${houseHold.model }"></td>
				</tr>
			</table>
			<button class="btn_submit mt_20" type="submit" >确认修改</button>
	</form>
</div>
</body>
</html>