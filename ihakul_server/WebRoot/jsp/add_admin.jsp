<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾台管理系统-管理员管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
  
</head>
<body>
<div class="main_title">
	<div class="fr blue"><a href="login.jsp">返回>></a></div>
	<i></i><a href="">小艾</a>><a href="">账号管理</a>><a href=""><strong>添加管理员</strong></a>
</div>

<div class="container">
			<form action="${pageContext.request.contextPath }/insertAdmin.action" method="post" >
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150">账号：</td>
					<td><input type="text"  name="aname"  value="" id=aname><span class="hj" id="an">账号不能为空</span></td>
				</tr>				
				<tr>
					<td>密码：</td>
					<td><input type="password" id="ps1" name="password" value="" ><span  class="hj" id="p1">密码不能为空</span></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input type="password" id="ps2" name="password2" value="" ><span class="hj" id="p2">确认密码不能为空</span>
					<span class="hj" id="p3">确认密码与原密码不相同</span></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input type="text" id="realName" name="realName" value="" >
						<span class="hj" id="rl">必须填写真实姓名</span>
					</td>
				</tr>				
				<tr>
					<td>手机号：</td>
					<td><input type="text" id="phone" name="phoneNumber" value="" >
						<span class="hj" id="ph">请输入正确的手机号</span>
					</td>
				</tr>
				<tr>
					<td>性别：</td>
					<td>
						<div class="check_label clearfix">
							<label for="man"><input type="radio" name="sex" id="man" value="男" />男</label>
							<label for="woman"><input type="radio" name="sex" id="woman" value="女" />女</label>
							<label for="other"><input type="radio" name="sex" id="other" value="保密" checked="checked" />保密</label>
						</div>
					</td>
				</tr>
			</table>
			<button class="btn_submit mt_20" type="submit" id="add_bt">添加</button>
			<s:token></s:token>
</form>
</div>
<!-- 滑动标签js -->
<script type="text/javascript">
	$(document).ready(function() {
	
		$("input[name='aname']").blur(function(){
			var aname=$("input[name='aname']").val();
			if(aname ==""){
				$("#an").removeClass("hj");
			}else{
				queryName(aname);
				$("#an").addClass("hj");
			 }
		});
		
		//验证用户名是否已注册
	function queryName(aname){
		$.ajax({
			url:"${pageContext.request.contextPath }/getAdminByaname.action",
			type:"post",
			data:{"aname":aname},
			dataType:"json",
			success:function(json){	
				if(json.fals){
					alert("对不起，该用户名已经存在,请重新输入");
					$("#aname").val("");
				}
			},
			error:function(){
				alert("出现错误");
			},
		
		});
		
	};
		
		
		$("#ps1").blur(function(){
			var pass1=$("#ps1").val();
			if(pass1 =="" ){
				$("#p1").removeClass("hj"); //添加样式
			}else{
				$("#p1").addClass("hj");   //移除样式
			}
			
		});
		$("#ps2").blur(function(){
			var pass1=$("#ps1").val();
			var pass2=$("#ps2").val();
			if(pass2 ==""){
				$("#p2").removeClass("hj");
				$("#p3").addClass("hj");
			}else if(pass1 !=pass2){
				$("#p2").addClass("hj");	
			    $("#p3").removeClass("hj");
			}else{
			    $("#p2").addClass("hj");
				$("#p3").addClass("hj");
			}
		} );
		
		$("input[name='realName']").blur(function(){
			var realName=$("input[name='realName']").val();
			if(realName==""){
				$("#rl").removeClass("hj");
			}else{
				$("#rl").addClass("hj");
			}
		
		});
		
		/*增加手机号为11位数的判断  */
		$("input[name='phoneNumber']").blur(function(){
			var phoneNumber=$("input[name='phoneNumber']").val();
			if(phoneNumber==""||phoneNumber.length!=11){
				$("#ph").removeClass("hj");
			}else{
				$("#ph").addClass("hj");
			}
		});
	
	});
	
	//提交判断
	$("#add_bt").click(function(){
	   var aname=$("#aname").val();
	   var pass1=$("#ps1").val();
	   var pass2=$("#ps2").val();
	   var rname=$("#realName").val();
	   var pnumber=$("#phone").val();
	  
	   if(aname==""){
	     alert("账号不能为空!");
	     return false;
	   }else if(pass1==""){
	      alert("密码不能为空!");
	     return false;
	   }else if(pass2==""){
	      alert("确认密码不能为空!");
	      return false;
	   }else if(pass1 !=pass2){
	      alert("密码和确认密码不一致!");
	      return false;
	   }else if(rname==""){
	      alert("姓名不能为空!");
	     return false;
	   }
	   if(pnumber==""){
	      alert("手机号不能为空!");
	      return false;
	   }else if(pnumber.length!=11){
	     alert("请输入正确的手机号!");
	     return false;
	   } 
	   
	
	});
	


</script>

</body>
</html>