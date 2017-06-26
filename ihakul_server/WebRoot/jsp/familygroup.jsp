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
	<title>小艾后台管理系统-家庭组管理</title>
	
	<script type="text/javascript" src="../js/vendor/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="../date/js/laydate.js"></script>
	

	<link type="text/css" rel="stylesheet" href="../css/common.css" />
	<link type="text/css" rel="stylesheet" href="../css/main.css" />
	
	
</head>
<body>
<div class="main_title"><i></i><a href="">小艾</a>><a href=""><strong>家庭组管理</strong></a></div>

<div class="container">
	<ul class="tabs_3 tabs clearfix" id="tabs">
		<!-- <li><a href="">家庭组列表</a></li> -->
		<!-- <li><a href="">添加家庭组</a></li> -->
		
	</ul>
	<ul class="tab_conbox" id="tab_conbox">
		<!-- 家庭组列表 -->
		<li class="tab_con">
		<form action="showAllFanily.action?pageNow=1&showPage=5" method="post">
			<ul class="ad_list_search clearfix">
				
				<li class="name"><label>家庭组名字</label>
				<input type="text" name="groupName" value=""></li>
		 		
				<li class="name"><label>所在城市</label>
				<input type="text" name="city" value="">
				</li>
				<li class="name"><label>时间</label>
				
				<input placeholder="请输入开始日期" name="startTime"  onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				<input placeholder="请输入截止日期" name="endTime"  onClick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				<li ><button class="btn_submit mt_20" type="submit">搜索</button></li>
				
			</ul>
			</form>
			<table class="table mt_20">
				<tr>
					<th>序列号</th>
					<th>家庭组名称</th>
					<th>添加时间</th>
					<th>所属位区</th>
					<!-- <th>操作</th> -->
				</tr>

				<c:forEach items="${list }" var="fa" varStatus="star" >
				<tr>
					
					<td>${star.index+1 }</td>
					<td><a href="houseHoldfind.action?id=${fa.groupId }&pageNow=1&showPage=5" class="operate" >${fa.groupName }</a></td>
					<td>${fa.creationTime}</td>
					<td>${fa.city }</td>
					<%-- <td><a href="selectFami.action?id=${fa.groupId }" class="operate">修改</a><a href="#" onclick="shanchu(${fa.groupId})" class="operate">删除</a></td> --%>
				</tr>
			
			</c:forEach>

			<tr>

				<td colspan="7" class="management">
						<div id="fr" class=" fr"><!-- 公用翻页 -->
						<input type="hidden" id="page"  value="${ pageNow}">
						<input type="hidden" id="totalPage"  value="${totalPage} }">
							<button type="button" id="firstPage" >首页</button>
							<button type="button" id="upPage" >上一页</button>
							<button type="button" id="nextPage" >下一页</button>
							<button type="button" id="lastPage" >尾页</button>
							
						</div>
						<label id="lb" for="check">${pageNow }/${totalPage }</label>
						
					</td>
					</tr>

</table>

		<!--添加家庭组 -->
		<li class="tab_con">
		<form action="addFamily.action" method="post">
			<table class="table mt_20 p_lr_15 table_2">
				<tr>
					<td> 机器人小艾编号</td>
					<td> <input type="text" name="xiaoNumber" id="da"  onblur="upperCase()" value="">
					 <em>必填，否则无法产生家庭组序号</em></td>
				</tr>
				<tr>
					<td width="150">家庭组编号：

					</td>				
					
					<td ><label id="fa"  >
					
					</label> <em>由系统产生，无法修改</em></td>
				</tr>
				<tr>
					<td>家庭组名称：</td>
					<td><input name="groupName" type="text" value=""></td>
				</tr>
				<tr>
					<td>国家：</td>
					<td><input name="state" type="text" value=""></td>
				</tr>
				<tr>
					<td>省份城市：</td>
					<td><input name="city" type="text" value=""></td>
				</tr>
				<tr>
					<td>街道</td>
					<td><input name="district" type="text" value=""></td>
				</tr>
				
			</table>
			<input type="hidden" value="${refresh }" id="refresh">
			<button class="btn_submit mt_20" type="submit">确认</button>
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
		 window.location.href="showAllFanily.action?pageNow=1&showPage=5";
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
			window.location.href="showAllFanily.action?pageNow="+(pageNow+1)+"&showPage=5";
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
			window.location.href="showAllFanily.action?pageNow="+(pageNow-1)+"&showPage=5";
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
		 	window.location.href="showAllFanily.action?pageNow=1&showPage=5";
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
		 	window.location.href="showAllFanily.action?pageNow="+totalPage+"&showPage=5";
		 }
	
	});
	
});
function shanchu(id){
//var id=$("#ad").val();
  var fals=confirm("你确定要删除吗？");
	if(fals){
	$.ajax({
		url:"deleteFamily.action",
		data:{"id":id},
		type:"post",
		dataType:"json",
		success:function(json){
			var	fals=json.fals;
			if(fals){
				alert("删除成功");
				 window.location.reload();//刷新当前页面
			}else{
				alert("删除失败");
				 window.location.reload();//刷新当前页面
			};
			
		},
		error:function(){
			alert("返回数据失败");
			window.location.reload();//刷新当前页面
		}
	
	});
		return true;
	}else{
		return false;
	}

	
};
//获取输入的小艾编号生成家庭组编号
function upperCase(){
	var xiaoiNumber=$("#da").val();
	$.ajax({
		url:"getXiaoi.action",
		type:"post",
		data:{xiaoiNumber:xiaoiNumber},
		dataType:"json",
		success:function(json){
			var groupNumber=json.familynumber;
			$("#fa").html(groupNumber);
			$("#fa").append("<input type='hidden' id='aaf' name='gnumber' value='"+groupNumber+"'>");
		},
		error:function(){
			alert("无效编号，请重新改输入");
		},
	});
};
!function(){
	laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
	laydate({elem: '#demo'});//绑定元素
}();

//日期范围限制
var start = {
    elem: '#start',
    format: 'YYYY-MM-DD',
    min: laydate.now(), //设定最小日期为当前日期
    max: '2099-06-16', //最大日期
    istime: true,
    istoday: false,
    choose: function(datas){
         end.min = datas; //开始日选好后，重置结束日的最小日期
         end.start = datas ;//将结束日的初始值设定为开始日
    }
};

var end = {
    elem: '#end',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: '2099-06-16',
    istime: true,
    istoday: false,
    choose: function(datas){
        start.max = datas; //结束日选好后，充值开始日的最大日期
    }
};
laydate(start);
laydate(end);

//自定义日期格式
laydate({
    elem: '#test1',
    format: 'YYYY年MM月DD日',
    festival: true, //显示节日
    choose: function(datas){ //选择日期完毕的回调
        alert('得到：'+datas);
    },
});
//日期范围限定在昨天到明天
laydate({
    elem: '#hello3',
    min: laydate.now(-1), //-1代表昨天，-2代表前天，以此类推
    max: laydate.now(+1) //+1代表明天，+2代表后天，以此类推
});


</script>
</body>
</html>