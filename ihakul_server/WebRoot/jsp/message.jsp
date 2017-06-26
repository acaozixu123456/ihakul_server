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
	<script type="text/javascript" src="../js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<link type="text/css" rel="stylesheet" href="../css/main.css" />
</head>
<body>
   
  </body>
  <script type="text/javascript">
  	$(document).readly(function(){
  		alert("请不要重复提交");
  		 window.location.href="main.jsp";
  	
  	});
  
  </script>
</html>
