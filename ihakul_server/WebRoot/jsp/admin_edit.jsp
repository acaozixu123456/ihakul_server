<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾后台管理系统-管理员账号管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=1&showPage=5">返回&gt;&gt;</a></div> <!--返回跳转界面  -->
	<i></i><a href="">机器人小艾</a>><a href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=1&showPage=5">管理员账号管理</a>><a href=""><strong>账号编辑</strong></a>
</div>

<div class="container">
		<form action="${pageContext.request.contextPath }/updateAdmin.action" method="post">
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150"><i class="red_heart">*</i>账号：</td>
					<td><input type="text"  value="${admin.aname }" name="aname">
						<input type="hidden" value="${admin.aid }" name="aid">
					</td>
					
				</tr>				
				<tr>
					<td><i class="red_heart">*</i>密码：</td>
					<td><input type="password"  name="password" value="${admin.password }"></td>
				</tr>
				<tr>
					<td><i class="red_heart">*</i>真实姓名：</td>
					<td><input type="text"  name="realName" value="${admin.realName }"></td>
				</tr>
				<tr>
					<td><i class="red_heart">*</i>手机号：</td>
					<td><input type="text"  name="phoneNumber" value="${admin.phoneNumber }"></td>
				</tr>
				<tr>
					<td>性别：</td>
					<td>
						<div class="check_label clearfix">
							<label for="man"><input type="radio" name="sex" id="man" value="男" />男</label>
							<label for="woman"><input type="radio" name="sex" id="woman" value="女" />女</label>
							<label for="other"><input type="radio" name="sex" id="other" value="保密" />保密</label>
						</div>
					</td>
				</tr>
				<tr>
					<td>角色：</td>
					<td>
						<div class="check_label clearfix">
							
							<label for="ok"><input type="radio" name="roleName" id="ok" value="高级管理员">高级管理员</label>
							<label for="no"><input type="radio" name="roleName" id="no" value="普通管理员">普通管理员</label>	
						</div>
					</td>
				</tr>
			
			</table>
			<input type="hidden" id="qx" value="${admin.role.rolename }">
				<button class="btn_submit mt_20"  type="submit">确定修改</button>
		  </form>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		
		var rolrName=$("#qx").val();
		
		if(rolrName==$("#ok").val()){
			$("#ok").attr("checked",'checked');
		}
		if(rolrName==$("#no").val()){
			$("#no").attr("checked",'checked');
		}
	});



</script>
</html>
