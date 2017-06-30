<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>机器人小艾后台管理系统-用户管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>小艾管理</strong></a></div>

<div class="container">
	<ul class="tabs_2 tabs clearfix" id="tabs">
		<li><a href="">小艾列表</a></li>
		<li><a href="">新增小艾</a></li>
	</ul>
	<ul class="tab_conbox" id="tab_conbox">
		<!-- 小艾列表 -->
		
		<li class="tab_con">
		<form action="${pageContext.request.contextPath }/findAllXiaoi.action?pageNow=1&showPage=5" method="post">
			<ul class="ad_list_search clearfix">
			
				<li class="name"><label>家庭名字：</label><input type="text" name="groupName" value=""></li>
				<li class="time_from"><label>小艾编号：</label><input type="text" name="xiaoNumber" value=""></li>
				
				<li class="search_btn"><button type="submit">搜索</button></li>
		
	</ul>
</form>
			<table class="table mt_20 " >
		<tr>
		    <th>序号</th>
			<th>小艾编号</th>
			<th>小艾名字</th>
			<th>小艾种类</th>
			<th>家庭组名字</th>
			<th>使用状态</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${list }" var="xiao" varStatus="star" >
		<tr>
		  <td>${star.index+1 }</td>
			<td>${xiao.xiaoNumber }</td>
			<td>${xiao.xname}</td>
			<td>
			<c:if test="${xiao.xiaoType==1 }">
				标准版
			</c:if>	
			<c:if test="${xiao.xiaoType==2 }">
				时尚版
			</c:if>	
			</td>
			<td>${xiao.familygroup.groupName }</td>
			
			<td>
			<c:if test="${xiao.onlineState==1 }">
				在线
			</c:if>
			<c:if test="${xiao.onlineState==0 }">
				不在线
			</c:if>
			</td>
			
			<td><a href="${pageContext.request.contextPath }/selectXiaoiByid.action?id=${xiao.xid }" class="operate">编辑</a>
			<a href="${pageContext.request.contextPath }/deleteXiaoi.action?id=${xiao.xid }" class="operate">删除</a>
			
			</td>
		</tr>
	</c:forEach>

		<tr>
			
		<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${pageNow }">
						<input type="hidden" id="totalPage"  value="${totalPage }">
							<button type="button" id="firstPage" >首页</button>
							<button type="button"  id="upPage" >上一页</button>
							<button type="button"  id="nextPage" >下一页</button>
							<button type="button"  id="lastPage" >尾页</button>
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>	
					</td>
		</tr>
	</table>
		</li>

		<!-- 新增小艾 -->
		<li class="tab_con">
			<form action="${pageContext.request.contextPath }/addXiaoi.action" method="post">
			<table class="table mt_20 p_lr_15 table_2">
				
				<tr>
					<td width="150">小艾名字：</td>
					<td><input type="text" name="xiaoName" value=""></td>
				</tr>
				<tr>
					<td>小艾编号：</td>
					<td><input type="text" name="xiaoNumber" value=""></td>
				</tr>
				<tr>
					<td>小艾型号</td>
					<td>
						<div class="check_label clearfix">
							<label for="fashion"><input type="radio" name="xiaoType" id="fashion" value="1" />普通版</label>
							<label for="common"><input type="radio" name="xiaoType" id="common" value="2" />时尚版</label>
						</div>
					</td>
					
				</tr>			
			</table>
			<input type="hidden" value="${refresh }" id="refresh">
			<button class="btn_submit mt_20" type="submit">确认添加</button>
		</form>
		</li>
	</ul>
</div>

<!-- 滑动标签js -->
<script type="text/javascript">
$(document).ready(function() {
	var refresh=$("#refresh").val();
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="${pageContext.request.contextPath }/findAllXiaoi.action?pageNow=1&showPage=5";
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
			window.location.href="${pageContext.request.contextPath }/findAllXiaoi.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="${pageContext.request.contextPath }/findAllXiaoi.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/findAllXiaoi.action?pageNow=1&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/findAllXiaoi.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});
	
});





</script>
</body>
</html>