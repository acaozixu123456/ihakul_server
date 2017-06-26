<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <meta charset="UTF-8">
	<title>机器人小艾后台管理系统-日志管理</title>
	<script type="text/javascript" src="../js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<link type="text/css" rel="stylesheet" href="../css/main.css" />
</head>
  </head>
  <div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>工作日志</strong></a></div>
  <body>


<div class="container">
	<form action="showXiaolog.action?showPage=10&pageNow=1" method="post">
	<ul class="ad_list_search clearfix">
		<li class="time_from"><label>小艾编号：</label><input type="text" name="xiaoiNumber" id="xiaoinu" value=""></li>
		<li class="search_btn"><button id="search_bt">搜索</button></li>
	</ul>
	</form>
		<table class="table mt_20 p_lr_15 table_2">
				
				<tr>
					<td width="150"><i class="red_heart"></i>小艾编号：${xiaoi.xiaoNumber }</td>
					<td ><i class="red_heart"></i>小艾名称：${xiaoi.xname }</td>
				</tr>				
				
				<tr>
					<td width="150"><i class="red_heart"></i>所属家庭组名字：${xiaoi.familygroup.groupName }</td>
					<td width="150"><i class="red_heart"></i>激活时间：${xiaoi.activationTime }</td>
				</tr>
	</table>
	
	<table class="table mt_20">
		<tr>
		    <th>序号</th>
		    <th>使用时间</th>
			<th>使用详情</th>
		</tr>
		<c:forEach items="${xiaoilogList }" var="xiaoilog" varStatus="state">
		<tr>
		  <td>${state.index+1}</td>
			<td>${xiaoilog.userTime }</td>
			<td>${xiaoilog.usingDetails }</td>
		</tr>
		</c:forEach>
		<tr>
					<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${paheNow }">
						<input type="hidden" id="totalPage" value="${totalPage }">
						
						<input type="hidden" value="${refresh }" id="refresh">
							<a href="showXiaolog.action?pageNow=1&showPage=5" id="firstPage" >首页</a>
							<a href="showXiaolog.action?pageNow=${pageNow-1 }&showPage=5" id="upPage" >上一页</a>
							<a href="showXiaolog.action?pageNow=${pageNow+1 }&showPage=5" id="nextPage" >下一页</a>
							<a href="showXiaolog.action?pageNow=${totalPage }&showPage=5" id="lastPage" >尾页</a>
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
						
					</td>
					</tr>
		
		
	</table>
<a href="exportExcell.action?xiaoNumber=${xiaoi.xiaoNumber}" class="btn_submit mt_20">导出报表</a>
</div>
<script type="text/javascript">
$(document).ready(function(){
    var refresh=$("#refresh").val();
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="showXiaolog.action?pageNow=1&showPage=5";
	}
    $("#search_bt").click(function(){
      var xiaoinumber=$("#xiaoinu").val();
      if(xiaonumber==""){
        alert("小艾编号不能为空!");
        return false;
      }
    
    }); 
     
	//下一页
	$("#nextPage").click(function(){
		 var page=$("#page").val();
		 var pageNow=parseInt(page); 
		 var totalP=$("#totalPage").val();
		 var totalPage=parseInt(totalP); 
		if(pageNow+1 >totalPage){
			alert("已经是最后一页了");
		}else{
			window.location.href="showXiaolog.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="showXiaolog.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="showXiaolog.action?pageNow=1&showPage=5";
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
		 	window.location.href="showXiaolog.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});

});

</script>
</body>
</html>