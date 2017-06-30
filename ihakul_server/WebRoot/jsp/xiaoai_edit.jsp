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
	<title>机器人小艾后台管理系统-小艾管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.10.2.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href="xiaoai_man.html">返回&gt;&gt;</a></div>
	<i></i><a href="">小艾</a>><a href="">小艾管理</a>><a href=""><strong>编辑</strong></a>
</div>

<div class="container">
<form action="updateXiaoi.action" method="post">
	<table class="table mt_20 p_lr_15 table_2">
		<tr>
			<td width="150"><i class="red_heart"></i>小艾名字：</td>
			<td><input type="text" name="xiaoName" value="${xiao.xname }"></td>
		</tr>				
		<tr>
			<td>种类：</td>
			<td>
			<div class="check_label clearfix">
							
							<label for="woman"><input type="radio" name="xiaoType" id="woman" value="1" />普通版</label>
							<label for="other"><input type="radio" name="xiaoType" id="other" value="2" />时尚版</label>
						</div>
					</td>
		</tr>
		<tr>
			<td>家庭组名字：</td>
			<td><input type="text" name="groupName" value="${xiao.familygroup.groupName }"></td>
		</tr>
		<tr>
			<td>使用状态：</td>
			<td>
				<div class="check_label clearfix">
							
							<label for="woman"><input type="radio" name="useType" id="woman" value="1" />已使用</label>
							<label for="other"><input type="radio" name="useType" id="other" value="0" />未使用</label>
						</div>
						</td>
		</tr>									
	</table>
	<input type="hidden" name="id" value="${xiao.xid }">
	<button class="btn_submit mt_20" type="submit">确认修改</button>
	</form>
</div>

</body>
</html>