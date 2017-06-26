package com.xiaoai.web.action;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.xiaoai.dao.IHouseholdDao;
import com.xiaoai.dao.IRoomDao;
import com.xiaoai.dao.impl.XiaoiDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
import com.xiaoai.util.XiaoiResult;
/**
 * 家庭组操作实现类
 * 供安卓调用
 * @author Administrator
 *
 */
@Controller("appFamilyAction")
public class AppFamilygroupAction extends XiaoaiMessage{
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	@Resource(name="usersService")
	private IUsersService usersService;
	@Resource(name="fauserService")
	private IFamilyUserService fauserService;
	
	@Resource(name="roomDao")
	private IRoomDao roomDao;
	
	@Resource(name="houseHoldDao")
	private IHouseholdDao householdDao;
	
	
	@Resource(name="xiaoiDao")
	private XiaoiDao xiaoiDao;
	
	private boolean success ;         //成功、失败标记
	private  String message ;        //信息
	private int code ;				 //标记
	private static Logger logger = Logger.getLogger(AppFamilygroupAction.class);
	
	/**
	 * 添加家庭组
	 * @param userId 用户Id
	 * @param groupName 家庭组名字
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String insert() throws IOException{
		String groupName=null,userId=null;
		XiaoiResult xiaoiResult = new XiaoiResult();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("添加家庭组的入参");
		JSONObject json=new JSONObject();
		JSONObject json2=new JSONObject();
		JSONArray array=new JSONArray();
		groupName=request.getParameter("groupName");
		userId=request.getParameter("userId");
		if(XATools.isNull(groupName)){
			xiaoiResult=XiaoiResult.build("家庭组名称不能为空", false, FamilyCode.emptyName);
		}
		if(XATools.isNull(userId)){
			xiaoiResult=XiaoiResult.build("用户Id不能为空", false, UserCode.emptyId);
		}else {
			if(!XATools.isInteger(userId)){
				xiaoiResult=XiaoiResult.build("用户ID格式不符 ", false, UserCode.formatisInconsistent);
			}
		}

	  if(xiaoiResult.isSuccess()){	
		Familygroup family=new Familygroup();
		family.setGroupName(groupName);
		family.setCreationTime(XATools.getTNowTime());
		family.setManagerId(Integer.parseInt(userId));
		family.setVersionNumber(0);
		
		xiaoiResult.setSuccess(familyService.insertFamilygroup(family));
		//所产生的家庭组编号
		int number=10000+family.getGroupId();
		String groupPassword=XATools.random(10); //生成10位随机数的验证码
		family.setGroupPassword(groupPassword);
		family.setGroupNumber(number);
		xiaoiResult.setSuccess(familyService.updateFamily(family));
		if(xiaoiResult.isSuccess()){
			Users users=null;
			users=usersService.selectUsersByid(Integer.parseInt(userId));
			if(users!=null){
			FamilyUser fu=new FamilyUser();
			fu.getUsers();
			fu.setFamilygroup(family);
			fu.setUsers(users);
			fu.setDna(userId);
			xiaoiResult.setSuccess(fauserService.insertFamilyUser(fu));		
			if(xiaoiResult.isSuccess()){ //用户绑定保存成功就正确返回
				json2.put("groupNumber", number);
				json2.put("groupPassword", groupPassword);
				array.add(json2);
			}else{
				xiaoiResult = XiaoiResult.build("用户绑定家庭组失败!", FamilyCode.conflictAbortBind);
			}
		 }else{
				xiaoiResult = XiaoiResult.build("没有该用户!",UserCode.noExistBean);
			}
		}else{
			xiaoiResult = XiaoiResult.build("家庭组添加失败!",FamilyCode.insertFalse);
		}
	  }
	  	json.put("code", xiaoiResult.getCode());
		json.put("message", xiaoiResult.getMessage());
		json.put("result", array);    //家庭组信息
		logger.info("添加家庭组的出参:"+json);
		out.print(json);
		return null;
	}
	
	
	/**
	 * 修改家庭组信息
	 * @param groupId 家庭组Id
	 * @param groupName 家庭组名字
	 * @param state 国家
	 * @param city 城市
	 * @param district 街道
	 * @param userId 用户Id
	 * @return success=true(修改成功)或者success=false(修改失败)
	 * @throws Exception 
	 */
	public String update() throws Exception{
		success =true;
		message =null;
		code=OK;
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("修改家庭组的入参");
		String groupNumber=request.getParameter("groupNumber");
		String groupName=request.getParameter("groupName");
		String userId=request.getParameter("userId");  
		Users users=null;
		Familygroup family=null;
		FamilyUser fu1 =null;
		JSONObject json=new JSONObject();
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
	    }
		if(XATools.isNull(userId)){
			code=UserCode.emptyId;
			success=false;
			message="用户id不能为空";
		}	
		
		if(XATools.isNull(groupName)){
			code=FamilyCode.emptyName;
			success=false;
			message="家庭组名称不能为空";
		}	
		
		if(!XATools.isInteger(userId)){
			code=UserCode.formatisInconsistent;
			success=false;
			message="用户ID格式不符 ";
		}
		if(!XATools.isInteger(groupNumber)){
			code=FamilyCode.formatisInconsistent;
			success=false;
			message="家庭组编号格式不符 ";
		}
		if(success){
		users=usersService.selectUsersByid(Integer.parseInt(userId));
		if(users !=null){
				family=familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				if(family !=null){
					fu1=fauserService.selectFamilyUserByGNU(users, family);
					if(fu1!=null){
				  if(userId.equals(fu1.getDna())){ //判断该家庭组是不是该用户创建					  
					family.setGroupName(groupName);
					success=familyService.updateFamily(family);
					if(success==false){
						code=FamilyCode.updateFalse;
						success=false;
						message="家庭组修改失败!";	
					}
				}else{
					code=FamilyCode.privilegeMaster;
					success=false;
					message="您不是群主，没有权限修改!";
				}
		      }else{
		    	  code=FamilyCode.noExistUser;
					success=false;
					message="该家庭组中不存在该用户";
				}			
			} else{
				code=FamilyCode.noExistBean;
				success=false;
				message="没有该家庭组";
			}		
		}else{
			code=UserCode.noExistBean;
			success=false;
			message="没有该用户!";
		}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("修改家庭组出参:"+json);
		out.print(json);
		return null;
	}
	
	
	/**
	 * 查询当前家庭组的信息
	 * @param groupId 家庭组Id
	 * @return family 家庭组信息
	 * @throws IOException 
	 */
	public String getFamilygroupByid() throws IOException{
		JSONObject json=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("查询家庭组的入参");
		String groupNumber=request.getParameter("groupNumber");
		json=getFamilyGroup(groupNumber);
		out.print(json);
		logger.info("查询家庭组的出参:"+json);	
		return null;
	}
	
	
	public  JSONObject getFamilyGroup(String groupNumber){
		success =true;
		message =null;
		code=OK;
		JSONObject json2=new JSONObject();
		JSONObject json5=new JSONObject();
		JSONArray array=new JSONArray();
		JSONArray array3=new JSONArray();
		JSONArray array5=new JSONArray();
		JSONObject json=new JSONObject();
		if(!XATools.isInteger(groupNumber)){
			code=FamilyCode.formatisInconsistent;
			success=false;
			message="家庭组编号格式不符 ";
		}
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
		}
		Familygroup family=null;
		List<Users> user=null;
		if(success){ //如果家庭组编号不为空则查询
			int gn= Integer.parseInt(groupNumber);
			family=familyService.getFamilygroupByNumber(gn);
		if(family !=null){
			json2.put("groupName", family.getGroupName()); //家庭组名称 
			json2.put("groupNumber", family.getGroupNumber()); //家庭编号
			json2.put("groupPassword", family.getGroupPassword()); //验证密码
			json2.put("managerId", family.getManagerId()); //创建家庭组用户ID	
			json.put("versionNumber", family.getVersionNumber());   //家庭组版本号
			user=familyService.selectusersByFamilygroup(family);
			for(Users us:user){
				if(family.getManagerId().equals(us.getUserId())){
					json2.put("userName", us.getUserName()); //创建家庭组用户名称
				}
			}
			List<Xiaoi> xiaoList=xiaoiDao.selectXiaoiByid(family.getGroupId());
			 if(!XATools.isNull(xiaoList)){
				for(Xiaoi xiaoi:xiaoList){
				json5.put("xiaoName", xiaoi.getXname());
				json5.put("onlineState", xiaoi.getOnlineState()); //在线状态(0,不在线;1,在线)
				json5.put("xiaoNumber", xiaoi.getXiaoNumber());//终端编号
				json5.put("xiaoType", xiaoi.getXiaoType());//终端类型(1,普通;2时尚)
				json5.put("xiaoIp", xiaoi.getXiaoIp());//终端IP
				json5.put("activationTime", xiaoi.getActivationTime());//激活时间
				json5.put("mode", xiaoi.getMode());//情景模式
				json5.put("volume", xiaoi.getVolume());//声音
				array5.add(json5);
			   }
			 }
			
			List<Room> roomList=roomDao.getRoomByGroupId(family.getGroupId());
		    if(!XATools.isNull(roomList)){
		    	 for(Room room:roomList){
		    		 JSONArray array4=new JSONArray();
		    		 JSONObject json3=new JSONObject();
		    		 json3.put("groupNumber", family.getGroupNumber()); //家庭编号
		    		 json3.put("roomName", room.getRoomName()); //房间名称
		    		 json3.put("roomNickName", room.getRoomNickName()); //房间昵称
		    		 json3.put("floor", room.getFloor()); //房间楼层(默认 0)
		    		 json3.put("parentId", room.getParentId()); //父节点标识
		    		 json3.put("roomNumber", room.getRoomNumber()); //房间编号
		    		 json3.put("robot", room.getRobot()); //终端绑定的编号
		    		 json3.put("creator", room.getCreator()); //终端创建的编号
		    		 List<Household> householdList =householdDao.selectHouseholdByroomIDandGroupId(room.getId(),family.getGroupId());
		    		 if(!XATools.isNull(householdList)){
		    		 for(Household household:householdList){
		    			 JSONObject json4=new JSONObject();
		    			 json5.put("roomNumber", room.getRoomNumber()); //房间编号
		    			 json4.put("names", household.getEaName());  //家电呢称
		    			 json4.put("classId", household.getClassId());//家电类别  1 智能家电,2红外线家电
		    			 json4.put("brand", household.getBrand()); //品牌
		    			 json4.put("model", household.getHid()); //型号 
		    			 json4.put("eaNumber", household.getEaNumber()); //家电编号 
		    			 json4.put("type", household.getType()); //家电类型 
		    			 json4.put("prop", household.getProp()); //通讯参数 
		    			 json4.put("stub", household.getStub()); //智能索引
		    			 json4.put("status", household.getStatus()); //
		    			 json4.put("port", household.getPort()); //
		    			 array4.add(json4);
		    			 json3.put("household", array4);//家电信息
		    		 }
		    		 }else{
		    			 json3.put("household", array4);//家电信息 
		    		 }
		    		 array3.add(json3);
		    		 json2.put("room", array3);//房间信息
		    	 }
		    }else{
		    	json2.put("room", array3);
		    }
			array.add(json2);
		}else{
			code=FamilyCode.noExistBean;
			success=false;
			message="没有该家庭组";
		}
		}
		json.put("code", code);
		json.put("message", message);
		json.put("result", array);   //家庭组信息
		json.put("result1", array5);   //小艾信息
		return json;	
	}
	
	
	
	
	
	/**
	 * 删除家庭组
	 * @param userId 用户Id
	 * @param groupId 家庭组Id
	 * @return success=true(删除成功)或者success=false(删除失败)
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		success =true;
		message =null;
		code=OK;
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("删除家庭组的入参");
		String groupNumber=request.getParameter("groupNumber");
		String userId=request.getParameter("userId");	
		Users users=null;
		FamilyUser fu1 =null;
		JSONObject json=new JSONObject();
		if(XATools.isNull(userId)){
			code=UserCode.emptyId;
			success=false;
			message="用户id不能为空";
		}
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
		}
		if(!XATools.isInteger(userId)){
			code=UserCode.formatisInconsistent;
			success=false;
			message="用户ID格式不符 ";
		}
		if(!XATools.isInteger(groupNumber)){
			code=FamilyCode.formatisInconsistent;
			success=false;
			message="家庭组编号格式不符 ";
		}
		if(success){
		users=usersService.selectUsersByid(Integer.parseInt(userId));
		if(users !=null){
				Familygroup family=null;
				family=familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				if(family !=null){
					fu1=fauserService.selectFamilyUserByGNU(users, family);
					if(fu1!=null){
					 if(userId.equals(fu1.getDna())){ //判断该家庭组是不是该用户创建					  
					success=familyService.deleteFamilygroup(family);
					if(success==false){
						code=FamilyCode.deleteFalse;
						success=false;
						message="家庭组删除失败!";	
					}
				}else{
					code=FamilyCode.privilegeMaster;
					success=false;
					message="您不是群主，没有权限修改!";
				}
			 }else{
				code=FamilyCode.noExistUser;
				success=false;
				message="该家庭组中不存在该用户";
				}
			}else{
				code=FamilyCode.noExistBean;
				success=false;
				message="没有该家庭组";	
			}
		}else{
			code=UserCode.noExistBean;
			success=false;
			message="没有该用户!";
		}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("删除家庭组的出参:"+json);
		out.print(json);
		return null;
	}


	
	
}
