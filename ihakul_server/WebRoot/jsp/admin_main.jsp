<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName
()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" onload="reurl()">
	<title>机器人小艾后台管理系统-账号管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body >
<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>账号管理</strong></a></div>

<div class="container">
	<ul class="tabs_4 tabs clearfix" id="tabs">
		<li><a href="">账号列表</a></li>
		
	</ul>
	<ul class="tab_conbox" id="tab_conbox">
		<!-- 账号列表 -->
		<li class="tab_con">

			<table class="table mt_20">
				<tr>
					<th>账号</th>
					<th>真实姓名</th>
					<th>手机号</th>
					<th>登录次数</th>
					<th>最后登录时间</th>
					<th>管理等级</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${adminList}" varStatus="status" var="admin">
				<tr>
					<td>
						<div class="thumbnail3 clearfix">
							
							<p>${admin.aname }</p>
							<small>注册时间：${admin.createdate}</small>
						</div>
					</td>
					<td>${admin.realName }</td>
					<td>${admin.phoneNumber }</td>
					<td>${admin.loginNumber}</td>
					<td class="line_h2">${admin.loginLastTime }</td>
					<td><span class="off">${admin.role.rolename }</span></td>
					
					<td><a href="${pageContext.request.contextPath }/getAdmin.action?aid=${admin.aid }" class="operate">编辑</a>
					<a href="#"  class="operate" onclick="de(${admin.aid })">删除</a></td>
					<!-- <a href="deleteAdmin.action?aid=${admin.aid }"  class="operate" >删除</a> -->
				</tr>
				</c:forEach>
					<tr>
					<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="">
						<input type="hidden" id="total"  value="">
						<input type="hidden" value="${refresh }" id="refresh">
							<a href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=1&showPage=5" id="firstPage" >首页</a>
							<a href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=${pageNow-1 }&showPage=5" id="upPage" >上一页</a>
							<a href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=${pageNow+1 }&showPage=5" id="nextPage" >下一页</a>
							<a href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=${totalPage }&showPage=5" id="lastPage" >尾页</a>
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
					</td>
					</tr>
			</table>
		</li>

		
	</ul>
</div>

<!-- 滑动标签js -->
<script type="text/javascript">
	
$(document).ready(function() {
	var refresh=$("#refresh").val();
	if(refresh!=null){
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=1&showPage=5";
	}
	}
	
	jQuery.jqtab = function(tabtit,tab_conbox,shijian) {
		$(tab_conbox).find("#tab_conbox > li").hide();
		$(tabtit).find("li:first").addClass("thistab").show(); 
		$(tab_conbox).find("li:first").show();
	
		$(tabtit).find("li").bind(shijian,function(){
		  $(this).addClass("thistab").siblings("li").removeClass("thistab"); 
			var activeindex = $(tabtit).find("li").index(this);
			$(tab_conbox).children().eq(activeindex).show().siblings().hide();
			return false;
		});
	
	};
	/*调用方法如下：*/
	$.jqtab("#tabs","#tab_conbox","click");	
	
	//下一页
	$("#nextPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="${pageContext.request.contextPath }/showAllAdminAction.action?pageNow=${pageNow+1 }&showPage=5";
		}
			
		
	});
});







</script>
</body>
</html>