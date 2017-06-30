<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>用户登录</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>

<div class="login_container">
	<div class="login_main">
		<div class="login_box">
			<div class="login_logo"><h1>用户登入</h1></div>
			<div class="login_info_bd">
			<form  action="${pageContext.request.contextPath }/UsersLogin.action" method="post">
				<ul class="info">
					<li><label>账号：</label><input type="text" class="input" id="a" name="userName"></li>
					<li><label>密码：</label><input type="password" class="input" id="pass" name="userPassword"></li>
					<li><button class="login_btn" type="submit">登录</button></li>
				</ul>
			</form>
			</div>
		</div>
	</div>
</div>

<script>


</script>
</body>
</html>