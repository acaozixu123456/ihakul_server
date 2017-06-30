<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>404错误页面 </title>
<meta http-equiv="refresh" content="60;url=main.jsp">
<!-- content="60，即60秒后返回主页，可根据需要修改或者删除这段代码 -->
<link href="${pageContext.request.contextPath }/css/error.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 代码 开始 -->
<div id="container"><img class="png" src="${pageContext.request.contextPath }/images/404.png" /> <img class="png msg" src="${pageContext.request.contextPath }/images/404_msg.png" />
  <p><a href="main.jsp" target="_blank"><img class="png" src="${pageContext.request.contextPath }/images/404_to_index.png" /></a> </p>
</div>
<div id="cloud" class="png"></div>
<!-- 代码 结束 -->

 
</body>
</html>