<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾后台管理系统-家电信息</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
	<div class="main_title">
		<div class="fr blue"><a href="${pageContext.request.contextPath }/jsp/homeuser_query.jsp">返回&gt;&gt;</a></div>
		<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>电器使用情况</strong></a></div>
	</div>

<div class="container">
	

	<table class="table mt_20">
		<tr>
		    <th>序号</th>
			<th>家电名称</th>
			<th>品牌</th>
			<th>型号</th>
			<th>家电类别</th>
			<th>家庭组名称</th>
			<th>房间名称</th>
		</tr>
		<c:forEach items="${houseList }" var="hhlist" varStatus="star"> 
		<tr>
		  <td>${star.index+1 }</td>
			<td>${hhlist.eaName }</td>
			<td>${hhlist.brand }</td>
			<td>${hhlist.model }</td>
			<td>
			<c:if test="${hhlist.classId==1 }">
					红外
			</c:if>
			<c:if test="${hhlist.classId==2 }">
					智能
		    </c:if>
		    </td>
			<td>${hhlist.familygroup.groupName}</td>
			<td>${hhlist.room.roomName}</td>
		</tr>
	</c:forEach>
	<tr>
				<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${ pageNow}">
						<input type="hidden" id="totalPage"  value="${totalPage} ">
							<button type="button" id="firstPage" >首页</button>
							<button type="button" id="upPage" >上一页</button>
							<button type="button" id="nextPage" >下一页</button>
							<button type="button" id="lastPage" >尾页</button>
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
					</td>
					</tr>
	
	</table>

</div>
<script type="text/javascript">
$(document).ready(function(){
	//下一页
	$("#nextPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="${pageContext.request.contextPath }/findAllhhold.action?pageNow="+(pageNow+1)+"&showPage=5";
		}
	});
	//上一页
	$("#upPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		 if(pageNow-1 ==0){
			alert("已经是第一页了");
		 }else{
			window.location.href="${pageContext.request.contextPath }/findAllhhold.action.action?pageNow="+(pageNow-1)+"&showPage=5";
		 }
	});
	
	//首页
	$("#firstPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		 if(pageNow==1){
		 	alert("已经是第一页了");
		 }else{
		 	window.location.href="${pageContext.request.contextPath }/findAllhhold.action.action?pageNow=1&showPage=5";
		 }
	
	});
	
	//末页	
	$("#lastPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		 if(pageNow==totalPage){
		 	alert("已经是最后一页了");
		 }else{
		 	window.location.href="${pageContext.request.contextPath }/findAllhhold.action.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});

});
</script>
</body>
</html>