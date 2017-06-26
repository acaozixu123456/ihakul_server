<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" onload="reurl()">
	<title>机器人小艾后台管理系统-用户管理</title>
	<script type="text/javascript" src="../js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<link type="text/css" rel="stylesheet" href="../css/main.css" />
</head>
<body>
<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>用户管理</strong></a></div>

<div class="container">
	<ul class="tabs_2 tabs clearfix" id="tabs">
		<!-- <li><a href="">用户列表</a></li>
		<li><a href="">新增用户</a></li> -->   <!--目前不用  -->
	</ul>
	<ul class="tab_conbox" id="tab_conbox">
		<!-- 用户列表 -->
		
		<li class="tab_con">
			<form action="findAllUsers.action" method="post">
			<ul class="ad_list_search clearfix">
				
				<li class="name"><label>用户名</label><input type="text" name="userName" value=""></li>
				<li class="name"><label>手机名</label><input type="text" name="userPhoneNumber" value=""></li>
				<li class="search_btn"><button type="submit">搜索</button></li>
				</ul>
				</form>
			<table class="table mt_20">
				<tr>
					<th>序号</th>
					<th>用户名</th>
					<th>手机号</th>
					<th>用户登录状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${map['usersList'] }" var="users" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${users.userName}</td>
					<%-- <td>
						<div class="thumbnail3 clearfix">
							<p>${users.userName }</p>
							<small>注册时间：${users.createTime }</small>
						</div>
					</td> --%>
					<td>${users.userPhoneNumber }</td>
					
				<%-- 	<c:forEach items="${map['fglist'] }" var="fg">
					 
					<td>${fg.groupName }</td>
					</c:forEach>  --%>
					
					<td>
					<c:if test="${users.accessper==0 }">
					<span class="off">禁止登入</span>
					</c:if>
					
					<c:if test="${users.accessper==1 }">
					<span class="off">允许登入</span>
					</c:if>
					</td>
					<td><a href="getUser.action?userId=${users.userId }&se=1" class="operate">编辑</a><a href="javascript:;" onclick="deleteUser(${users.userId})"  class="operate">删除</a></td>
				</tr>
			</c:forEach>

				<tr>
					<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${pageNow }">
						<input type="hidden" id="totalPage"  value="${totalPage }">
							<button type="button" id="firstPage" >首页</button>
							<button type="button" id="upPage" >上一页</button>
							<button type="button" id="nextPage" >下一页</button>
							<button type="button" id="lastPage" >尾页</button>
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
						
					</td>
				</tr>
				

			
			</table>
		</li>

		<!-- 新增用户 -->
		<li class="tab_con">
		<form action="insertUsers.action" method="post">
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150"><i class="red_heart">*</i>家庭组名：</td>
					<td><input type="text" id="gname" name="groupName" value=""></td>
				</tr>
				<tr>
					<td width="150"><i class="red_heart">*</i>用户名：</td>
					<td><input type="text" name="userName" value=""></td>
				</tr>
				<tr>
					<td width="150"><i class="red_heart">*</i>密码：</td>
					<td><input type="text" name="userPassword"></td>
				</tr>			
				<tr>
					<td width="150"><i class="red_heart">*</i>手机号：</td>
					<td><input type="text" name="phoneNumber" value=""></td>
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
			
			</table>
			
			<input type="hidden" value="${refresh }" id="refresh">
			<button class="btn_submit mt_20" type="submit" >确认添加</button>
			</form>
		</li>
	</ul>
</div>

<!-- 滑动标签js -->
<script type="text/javascript">
$(document).ready(function() {
	var refresh=$("#refresh").val();
	if(refresh !=null){
	refresh++;
	if(refresh==2){//修改后返回
		 window.location.href="findAllUsers.action?pageNow=1&showPage=5";
	}
   }
	$("#gname").blur(function(){
		var groupName=$("#gname").val();
		
		$.ajax({
			url:"findFamily.action",
			type:"post",
			data:{groupName:groupName},
			dataType:"json",
			success:function(json){
				if(json.fals){
				}else{
					alert("请输入有效的家庭组名字");
					$("#gname").val("");
				};
			},
			error:function(){
				alert("返回数据失败");
			},
		});
	
	});		
	
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
			window.location.href="findAllUsers.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="findAllUsers.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="findAllUsers.action?pageNow=1&showPage=5";
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
		 	window.location.href="findAllUsers.action?pageNow="+totalPage+"&showPage=5";
		 }
	});



//删除
function deleteUser(id){
	$.ajax({
		url:"deleteUsers.action",
		type:"post",
		data:{"userId":id,"se":1},
		dataType:"json",
		success:function(data){
			
			if(data.fals){
				alert("删除成功");
				window.location.reload();//刷新当前页面.
			}else{
				alert("删除失败");
				window.location.reload();//刷新当前页面.
			}
			 
		},
		error:function(date){
			alert("返回数据失败");
		},
	});
};





});





</script>
</body>
</html>