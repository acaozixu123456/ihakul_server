<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <meta charset="UTF-8">
	<title>机器人小艾后台管理系统-日志管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
  </head>
  <div class="main_title"><i></i><a href="">小艾</a>><a href="">家庭组</a>><a href="">家电</a>><strong>家电详情</strong></div>
  <body>


<div class="container">
	
	
		<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150"><i class="red_heart"></i>家电名字:${houseHold.hhName }</td>
					<td ><i class="red_heart"></i>家庭组名字:${houseHold.familygroup.groupName }</td>
				</tr>				
				<tr>
					<td width="150"><i class="red_heart"></i>品牌:${houseHold.brand }</td>
					<td width="150"><i class="red_heart"></i>型号:${houseHold.model }</td>
				</tr>
			</table>
	<table class="table mt_20">
		<tr>
		    <th>序号</th>
		    <th>使用时间</th>
			<th>使用详情</th>
		</tr>
		<c:forEach items="${logList }" var="xiaoilog" varStatus="star">
		<tr>
		  <td>${star.index+1 }</td>
			<td>${xiaoilog.userTime }</td>
			<td>${xiaoilog.usingDetails }</td>
			
		</tr>
		</c:forEach>
		
		
		<tr>
			
			
				<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${pageNow }">
						<input type="hidden" id="totalPage"  value="${totalPage }">
						<input type="hidden" id="hids" value="${houseHold.hid }">
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
		 var id=$("#hids").val();
		 var hid=parseInt(id);
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="${pageContext.request.contextPath }/shullHousehold.action?hid="+hid+"&pageNow="+(pageNow+1)+"&showPage=5";
		}
			
		
	});
	//上一页
	$("#upPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		 var id=$("#hids").val();
		 var hid=parseInt(id); 
		 if(pageNow-1 ==0){
			alert("已经是第一页了");
		 }else{
			window.location.href="${pageContext.request.contextPath }/shullHousehold.action?hid="+hid+"&pageNow="+(pageNow-1)+"&showPage=5";
		 }
	});
	
	//首页
	$("#firstPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP);
		 var id=$("#hids").val();
		 var hid=parseInt(id);
		 if(pageNow==1){
		 	alert("已经是第一页了");
		 }else{
		 	window.location.href="${pageContext.request.contextPath }/shullHousehold.action?hid="+hid+"&pageNow=1&showPage=5";
		 }
	
	});
	
	//末页	
	$("#lastPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP);
		 var id=$("#hids").val();
		 var hid=parseInt(id); 
		 if(pageNow==totalPage){
		 	alert("已经是最后一页了");
		 }else{
		 	window.location.href="${pageContext.request.contextPath }/shullHousehold.action?hid="+hid+"&pageNow="+totalPage+"&showPage=5";
		 }
	
	});

});

</script>

</body>
</html>
