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
	<title>机器人小艾后台管理系统-小艾管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title"><i></i><a href="">机器人小艾</a>><a href=""><strong>数据字典管理</strong></a></div>

<div class="container">
	<form action="${pageContext.request.contextPath }/paginFindDatadic.action?pageNow=1&showPage=5" method="post">
	<ul class="ad_list_search clearfix">
		
		<li class="name"><label>数据名字:</label><input type="text" name="ddName" value="" placeholder="数据字典名字"></li>
		
		<li class="search_btn"><button type="submit">搜索</button></li>
		
		<li class="search_btn"><a href="${pageContext.request.contextPath }/jsp/datadictionary_insert.jsp" ><button type="button">添加</button></a></li>
	</ul>
	</form>
	<table class="table mt_20">
		<tr>
		    <th>序号</th>
			<th>名字</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${dataList }" var="list" varStatus="star">
		<tr>
		  <td>${star.index+1 }</td>
			
			<td>${list.ddName }</td>
			
			
			<td>
			<a href="${pageContext.request.contextPath }/deleteDatadictory.action?id=${list.id }" class="operate">删除</a></td>
		</tr>
		</c:forEach>

		<tr>
			
			<td colspan="7" class="management">
				
				
				<div id="fr" class=" fr"><!-- 公用翻页 -->
					<input type="hidden" id="total"  value="">
							<input type="hidden" id="page" value="${pageNow }">
							<input type="hidden" id="totalPage" value="${totalPage }" >
							<button type="button"  id="firstPage" >首页</button>
							<button type="button" id="upPage" >上一页</button>
							<button type="button" id="nextPage" >下一页</button>
							<button  id="lastPage" >尾页</button>	
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
						

			</td>
		</tr>
	</table>
<input type="hidden" value="${refresh }" id="refresh">
</div>
<script type="text/javascript">
$(document).ready(function(){
	var refresh=$("#refresh").val();
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="${pageContext.request.contextPath }/paginFindDatadic.action?pageNow=1&showPage=5";
	};
	//下一页
	$("#nextPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="${pageContext.request.contextPath }/paginFindDatadic.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="${pageContext.request.contextPath }/paginFindDatadic.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/paginFindDatadic.action?pageNow=1&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/paginFindDatadic.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});
		
	});
	
</script>
</body>
</html>