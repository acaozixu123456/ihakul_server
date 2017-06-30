<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾后台管理系统-添加数据字典</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.10.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href="${pageContext.request.contextPath }/paginFindDatadic.action?pageNow=1&showPage=5">返回&gt;&gt;</a></div>
	<i></i><a href="">小艾</a>><a href="">数据字典</a>><a href=""><strong>添加</strong></a>
</div>

<div class="container">
	<form action="${pageContext.request.contextPath }/insertDatadictroy.action" method="post">
	<table class="table mt_20 p_lr_15 table_2">
		<tr>
			<td width="150"><i class="red_heart"></i>数据名字:</td>
			<td><input type="text" placeholder="请输入字典名字" name="ddName" value=""></td>
		</tr>				
	</table>
	<button class="btn_submit mt_20">确定添加</button> 
	</form>
</div>

</body>
</html>