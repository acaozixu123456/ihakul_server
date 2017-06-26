<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾台管理系统-用户管理</title>
	<script type="text/javascript" src="../js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<link type="text/css" rel="stylesheet" href="../css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href="findAllUsers.action?pageNow=1&showPage=5">返回>></a></div>
	<i></i><a href="">小艾</a>><a href=""><a href="findAllUsers.action?pageNow=1&showPage=5">用户管理</a>><a href=""><strong>编辑用户</strong></a>
</div>

<div class="container">
	<form action="updateUser.action" method="post">
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150">用户名：</td>
					<td>
					<input type="hidden" name="userId" value="${user.userId}">
					<input type="text" name="userName" value="${user.userName }"></td>
				</tr>				
				<tr>
					<td>密码修改：</td>
					<td><input type="password" name="userPassword" value="${user.userPassword }" ></td>
				</tr>				
				<tr>
					<td>手机号：</td>
					<td><input type="text" name="userPhoneNumber" value="${user.userPhoneNumber }"></td>
				</tr>
				<tr>
					<td>性别：</td>
					
					<td>
						<div class="check_label clearfix">
							<input type="hidden" id="s" value="${user.userSex}">
							<label for="man"><input type="radio" name="sex" id="man" value="男"   />男</label>
							<label for="woman"><input type="radio" name="sex" id="woman" value="女" />女</label>
							<label for="other"><input type="radio" name="sex" id="other" value="保密"  />保密</label>
						</div>
					</td>
				</tr>
				<tr>
					<td>登入权限：</td>
					<td>
						<div class="check_label clearfix">
							<input type="hidden" id="ac" value="${user.accessper }">
							<label for="admin"><input type="radio" name="accessper" id="ok" value="1" />允许登入</label>
							<label for="driver"><input type="radio" name="accessper" id="no" value="0" />不允许登入</label>
							
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" name="se" value="1">
			<button class="btn_submit mt_20" type="submit" onclick="show()">确认修改</button>
</form>
</div>
<script type="text/javascript">
$(document).ready(function(){
		var userSex=$("#s").val();
		var accessper=$("#ac").val();
		//根据数据库性别信息默认单选框选中
		if(userSex==$("#man").val()){
			$("#man").attr("checked",'checked');
		}
		if(userSex==$("#woman").val()){
			$("#woman").attr("checked",'checked');
		}
		if(userSex==$("#other").val()){
			$("#other").attr("checked",'checked');
		}
		if(accessper=$("#ok").val()){
			$("#ok").attr("checked",'checked');
		}
		if(accessper=$("#no").val()){
			$("#no").attr("checked",'checked');
		}
		
	});
 


</script>

</body>
</html>