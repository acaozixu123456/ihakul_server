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
	<title>机器人小艾后台管理系统-家庭组管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.10.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href=familygroup.html>返回&gt;&gt;</a></div>
	<i></i><a href="">机器人小艾</a>><a href="">家庭组管理</a>><a href=""><strong>修改</strong></a>
	</div>

<div class="container">
	<form action="${pageContext.request.contextPath }/updateFamily.action" method="post">
			<table class="table p_lr_15 table_2">
				<tr>
					<td width="150">家庭组编号：</td>
					<td><span  id="fa">${family.groupNumber }</span> <em>系统默认，无法修改</em></td>
				</tr>
				<tr>
					<td>家庭组名称：</td>
					<td><input type="text" name="groupName" value="${family.groupName }"><em>家庭组名称长度至少6个字符，最长30个汉字</em></td>
				</tr>
				<tr>
					<td>国家：</td>
					<td><input type="text" name="state" value="${family.state }"></td>
				</tr>
				<tr>
					<td>城市：</td>
					<td><input type="text" name="city" value="${family.city }"></td>
				</tr>
				<tr>
					<td>街道：</td>
					<td><input type="text" name="district" value="${family.district }"></td>
				</tr>
			</table>
			<input type="hidden" name="id" value="${family.groupId }">
			<button class="btn_submit mt_20" type="submit">确认修改</button>

</form>
</div>
</body>
</html>