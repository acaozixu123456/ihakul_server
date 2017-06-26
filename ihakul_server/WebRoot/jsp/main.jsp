<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>后台管理系统</title>
	<script type="text/javascript" src="../js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<link type="text/css" rel="stylesheet" href="../css/main.css" />
</head> 
<body>

<div class="nav">
	<ul class="system fr">
		<li><a href=""><span class="user_icon fl"><img src="../images/user_icon.jpg" alt=""></span>${admin.realName }</a></li>
		<!--<li><a href=""><i class="cog"></i>设置</a></li>-->
		<li><a href="#" onclick="log()"><i class="off"></i>退出登录</a></li>
	</ul>
	<div class="logo fl"></div>
</div>

<div class="menu">
	<ul>
	    <li id="l1" class="curr"><a href="showAllAdminAction.action?pageNow=1&showPage=5" target="content_frame">账号管理</a></li>	
		<li id="l2"><a href="showAllFanily.action?pageNow=1&showPage=5" target="content_frame">家庭组管理</a></li>
		<li id="l3"><a href="findAllUsers.action?pageNow=1&showPage=5" target="content_frame">用户管理</a></li>
		<li id="l4"><a href="findAllhhold.action?pageNow=1&showPage=5" target="content_frame">家电管理</a></li>
		<li id="l5"><a href="findAllXiaoi.action?pageNow=1&showPage=5" target="content_frame">终端管理</a></li>
		<li id="l6"><a href="showXiaolog.action?pageNow=1&showPage=5" target="content_frame">终端工作日志</a></li>
		<li id="l7"><a href="findVersion.action?pageNow=1&showPage=5" target="content_frame">版本管理</a></li>
		<li id="l8"><a href="queryHouseUser.action" target="content_frame">数据查看</a></li>
		<li id="l9"><a href="paginFindDatadic.action?pageNow=1&showPage=5" target="content_frame">数据字典</a></li>  <!--pageNow=1  默认当前页数,showPage=5  显示行数  -->
	</ul>
</div>
	
<div class="main_box">
	<div class="main">
		<iframe width="100%" height="100%" frameborder="0" id="content_frame" name="content_frame" scrolling="auto" src="showAllAdminAction.action?pageNow=1&showPage=5" ></iframe>
	</div>
</div>

<div class="copyright">
	<p></p>
	<p>哈酷  版权所有</p>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#l1").click(function(){
		$("#l1").addClass("curr");
		$("#l2").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l8").removeClass("curr");
		$("#l9").removeClass("curr");
	});	
	$("#l2").click(function(){
		$("#l2").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l8").removeClass("curr");
		$("#l9").removeClass("curr");
	});
	
	$("#l3").click(function(){
		$("#l3").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l2").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l8").removeClass("curr");
		$("#l9").removeClass("curr");
	});
	$("#l4").click(function(){
		$("#l4").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l2").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l8").removeClass("curr");
		$("#l9").removeClass("curr");
	});
	$("#l5").click(function(){
		$("#l5").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l2").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l8").removeClass("curr");
		$("#l9").removeClass("curr");
	});
	$("#l6").click(function(){
		$("#l6").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l2").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l8").removeClass("curr");
		$("#l9").removeClass("curr");
	});
	$("#l7").click(function(){
		$("#l7").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l2").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l8").removeClass("curr");
		$("#l9").removeClass("curr");
	});
	$("#l8").click(function(){
		$("#l8").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l2").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l9").removeClass("curr");
	});
	$("#l9").click(function(){
		$("#l9").addClass("curr");
		$("#l1").removeClass("curr");
		$("#l3").removeClass("curr");
		$("#l4").removeClass("curr");
		$("#l2").removeClass("curr");
		$("#l5").removeClass("curr");
		$("#l6").removeClass("curr");
		$("#l7").removeClass("curr");
		$("#l8").removeClass("curr");
	});
	});
function log(){
	var fals=confirm("你确定要退出吗？");
	if(fals){
		window.location.href="login.jsp";
	}else{
		return false;
	}
	
	
}

</script>
</body>
</html>