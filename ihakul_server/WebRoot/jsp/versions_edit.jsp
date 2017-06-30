<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>小艾后台管理系统-版本管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href="${pageContext.request.contextPath }/jsp/versions_management.html">返回&gt;&gt;</a></div>
	<i></i><a href="">小艾</a>><a href="">版本管理</a>><a href=""><strong>版本编辑</strong></a>
</div>

<div class="container">
	<form action="${pageContext.request.contextPath }/update.action" method="post">
	<table class="table mt_20 p_lr_15 table_2">
		<tr>
			<td width="150"><i class="red_heart">*</i>版本号：</td>
			<td><input type="text" id="vnumber" name="versionNumber" value="${version.versionNumber }"></td>
		</tr>
		<tr>
			<td>版本名称：</td>
			<td><input type="text" name="versionName" value="${version. versionName}"></td>
		</tr>					
		<tr>
			<td>升级内容：</td>
			<td><input type="text" name="upgradeClass" value="${version.upgradeClass }"></td>
		</tr>				
		<tr>
			<td><i class="red_heart">*</i>平台类型：</td>
			<td>
				<select name="versionType" id="">
					<option value="0">--请选择用户--</option>
					<option value="1">Android</option>
					<option value="2">IOS</option>
					<option value="3">小艾</option>
					<option value="4">其它</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>下载URL：</td>
				<td>
				<input type="hidden" name="id" value="${version.id }">
				<input type="text" name="versionUrl" value="${version.versionUrl}"><br>
			</td>
		</tr>	
	</table>
	<button class="btn_submit mt_20" type="submit">确认修改</button>
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#vnumber").blur(function (){
			var verNumber=$("#vnumber").val();
			queryNumber(verNumber);
		});
	
	
	});
	
</script>

</body>
</html>