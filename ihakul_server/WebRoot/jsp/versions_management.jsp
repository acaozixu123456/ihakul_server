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
	<title>小艾后台管理系统-版本管理</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/vendor/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/common.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>版本管理</strong></a></div>

<div class="container">
	<ul class="tabs_2 tabs clearfix" id="tabs">
		<li><a href="">版本列表</a></li>
		<li><a href="">新增版本</a></li>
	</ul>
	<ul class="tab_conbox" id="tab_conbox">
		<!-- 版本列表 -->
		<li class="tab_con">
			<form action="${pageContext.request.contextPath }/findVersion.action?pageNow=1&showPage=5" method="post">
			<ul class="ad_list_search clearfix">
				<li class="name"><label>版本号：</label><input type="text" name="versionNumber" value=""></li>
				<li class="name"><label>版本包名：</label><input type="text" name="versionPackage" value=""></li>
				<!-- <li class="time_from"><label>升级内容：</label><input type="text" name="upgradeClass" value=""></li> --> 
				<li class="search_btn"><button type="submit">搜索</button></li>
				
			</ul>
			</form>
			<table class="table mt_20">
				<tr>
					<th>序号</th>
					<th>版本号</th>
					<th>版本包名</th>
					<th>版本名字</th>
					<th>升级内容</th>
					<th>版本更新时间</th>
					<th>平台类型</th>
					<th>版本下载URL</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${versionList }" var="version" varStatus="state">
				<tr>
					
					<td>${state.index+1 }</td>
					<td>${version.versionNumber }</td>
					<td>${version.versionPackage}</td>
					<td>${version.versionName }</td>
					<td>${version.upgradeClass }</td>
					<td>${version.upgradeTime }</td>
					<td>
					<c:if test="${version.versionType ==2 }">
						IOS
					</c:if>
					
					<c:if test="${version.versionType ==1 }">
						Android
					</c:if>
					<c:if test="${version.versionType ==3 }">
						小艾
					</c:if>
					<c:if test="${version.versionType ==4 }">
						其它
					</c:if>
					</td>
					<td>${version.versionUrl }</td>
					<td><a href="${pageContext.request.contextPath }/findVersionByid.action?id=${version.id }" class="operate">编辑</a>
						<a href="#"  class="operate" onclick="de(${version.id })">删除</a>
					</td>
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

		<!-- 新增版本 -->
		<li class="tab_con">
			<form action="${pageContext.request.contextPath }/upload.action" name="formMat" method="post" enctype="multipart/form-data" >
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td width="150"><i class="red_heart">*</i>版本号：</td>
					<td><input type="text"  name="versionNumber" id="vnumber" value=""></td>
				</tr>
				<tr>
					<td width="150"><i class="red_heart">*</i>版本包名：</td>
					<td><input type="text"  name="versionPackage" id="vpackage" value=""></td>
				</tr>
				<tr>
					<td>版本名称：</td>
					<td><input type="text" name="versionName"  id="vname" value=""></td>
				</tr>				
				<tr>
					<td>更新内容：</td>
					
					<td><textarea rows="5" cols="40" name="upgradeClass" id="uclass"></textarea>
					</td>
				</tr>				
				<tr>
					<td><i class="red_heart">*</i>平台类型：</td>
					<td>
						<select name="versionType" id="vtype">
							<option value="0">--请选择--</option>
							<option value="1">Android</option>
							<option value="2">IOS</option>
							<option value="3">小艾</option>
							<option value="4">其它</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>上传APP：</td>
					<td>
							<input type="file" name="file" id="vfile" value="">
					</td>
				</tr>
			</table>
			<input type="hidden" value="${refresh }" id="refresh">
			<button class="btn_submit mt_20" type="submit" id="bt">确认</button>
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
	if(refresh==2){//修改后，添加后返回
		 window.location.href="${pageContext.request.contextPath }/findVersion.action?pageNow=1&showPage=5";
	}
	}
/* 	$("#vnumber").blur(function (){
			var verNumber=$("#vnumber").val();
            var verPackage=$("#vpackage").val();
            if(verPackage==""){
              alert("版本包名不能为空!");
              return false;
            }
           
		}); */
	//验证版本号是否已使用
	function queryNumber(verNumber){
		$.ajax({
			url:"queryNumber.action",
			type:"post",
			data:{"versionNumber":verNumber},
			dataType:"json",
			success:function(json){	
				if(json.fals){
					alert("对不起，该版本号已经存在,请重新输入");
					$("#vnumber").val("");
				}
			},
			error:function(){
				alert("出现错误");
			},
		
		});
		
	};
	
	//验证版本号在该包名下是否已使用
	function queryPackageNumber(verNumber,verPackage){
		$.ajax({
			url:"queryPackageNumber.action",
			type:"post",
			data:{"versionNumber":verNumber,"versionPackage":verPackage},
			dataType:"json",
			success:function(json){	
				if(json.fals){
					alert("对不起，该版本号在该包名下已经存在,请重新输入");
					$("#vnumber").val("");
					return false;
				}
			},
			error:function(){
				alert("出现错误");
				return false;
			},
		});
		return true;
	};
	
	
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
	
	
		//检查数据
	$("#bt").click(function(){
		 var verNumber=$("#vnumber").val();
		 var verPackage=$("#vpackage").val();
		 var vname=$("#vname").val();
		 var uclass=$("#uclass").val();
		 var vtype=$("#vtype").val();
		 var ivtype=parseInt(vtype); 
		 var vfile=$("#vfile").val();	 
		 if(verNumber==""){
		  alert("版本号不能为空!");
		  return false;
		 }
		 if(verPackage==""){
              alert("版本包名不能为空!");
              return false;
            }
	     if(vname==""){
	      alert("版本名称不能为空!");
	       return false;
	     }
		 if(uclass==""){
		  alert("版本名称不能为空!");
		   return false;
		 }
		 if(ivtype<1){
		  alert("请选择平台类型!");
		   return false;
		 }
		  if(vfile==""){
		  alert("请上传版本!");
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
			window.location.href="${pageContext.request.contextPath }/findVersion.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="${pageContext.request.contextPath }/findVersion.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/findVersion.action?pageNow=1&showPage=5";
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
		 	window.location.href="${pageContext.request.contextPath }/findVersion.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});
       
});

function de(id){
     var fals=confirm("你确定要删除吗？");
	if(fals){
	$.ajax({
		url:"deleteVersion.action",
		data:{"id":id},
		type:"post",
		dataType:"json",
		success:function(json){
			var	fals=json.fals;
			alert(123);
			if(fals){
				 alert("删除成功");
				 window.location.reload();//刷新当前页面
			}else{
				alert("删除失败");
				 window.location.reload();//刷新当前页面
			};	
		}
	});
		return true;
	}else{
		return false;
	}
};
</script>
</body>
</html>