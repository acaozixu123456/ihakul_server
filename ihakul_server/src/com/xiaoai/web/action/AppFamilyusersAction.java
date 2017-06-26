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

@Controller("appFauserAction")
public class AppFamilyusersAction extends XiaoaiMessage {
	@Resource(name = "fauserService")
	private IFamilyUserService fauserService;
	@Resource(name = "usersService")
	private IUsersService usersService;
	@Resource(name = "familyService")
	private IFamilygroupService familyService;
	@Resource(name="roomDao")
	private IRoomDao roomDao;
	
	@Resource(name="houseHoldDao")
	private IHouseholdDao householdDao;
	
	
	@Resource(name="xiaoiDao")
	private XiaoiDao xiaoiDao;

	private boolean success; // 成功、失败标记
	private String message; // 信息
	private int code; // 标记
	private static Logger logger = Logger.getLogger(AppHouseholdAction.class);

	/**
	 * 用户账号绑定家庭组
	 * 
	 * @param groupNumber 家庭组编号
	 * @param userId 用户id
	 * @param groupPassword  家庭组验证密码
	 * @return success=true(绑定成功)或者success=false(绑定失败)
	 * @throws IOException
	 */
	public String insert() throws IOException {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("用户账号绑定家庭组的入参");
		String groupNumber = request.getParameter("groupNumber");
		String userId = request.getParameter("userId");
		String groupPassword = request.getParameter("groupPassword");
		json1.put("groupNumber", groupNumber);
		json1.put("userId", userId);
		json1.put("groupPassword", groupPassword);
	
		json=insertFaUser(json1);
		if(success){
		  json=getFamilyGroup(groupNumber);
		}
		out.print(json.toString());
		logger.info("用户账号绑定家庭组的出参:" + json);
		return null;
	}
	
	public JSONObject insertFaUser(JSONObject json1){
		JSONObject json=new JSONObject();
		String groupNumber = json1.getString("groupNumber");
		String userId = json1.getString("userId");
		String groupPassword = json1.getString("groupPassword");
		Familygroup family = null;
		Users users = null;
		FamilyUser fu = new FamilyUser();
		FamilyUser fu1 =null;
		if (XATools.isNull(groupNumber)) {
			code = FamilyCode.emptyId;
			message = "家庭组编号不能为空";
			success = false;
		}
		if (XATools.isNull(groupPassword)) {
			code = FamilyCode.emptySecurityCode;
			message = "家庭组验证密码不能为空";
			success = false;
		}
		if (XATools.isNull(userId)) {
			code = UserCode.emptyId;
			message = "用户id不能为空";
			success = false;
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
		if (success) {
			users = usersService.selectUsersByid(Integer.parseInt(userId));
			if (users != null) {
				family = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				if (family != null) {
					fu1=fauserService.selectFamilyUserByGNU(users, family);
					if(fu1==null){
					// 验证家庭组密码是否正确，正确时才可以绑定到家庭组当中
					if (family.getGroupPassword().equals(groupPassword)) {
						fu.setFamilygroup(family);
						fu.setUsers(users);
						success = fauserService.insertFamilyUser(fu);
					} else {
						code = FamilyCode.errorSecurityCode;
						success = false;
						message = "家庭组验证密码错误!";
					}
					}else{
						code = FamilyCode.conflictAbortBind;
						success = false;
						message = "该用户已绑定该家庭组";
					}
				} else {
					code = FamilyCode.noExistBean;
					success = false;
					message = "没有该家庭组";
				}
			} else {
				code = UserCode.noExistBean;
				success = false;
				message = "没有该用户!";
			}
		}
		json.put("code", code);
		json.put("message", message);
		return json;
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
	
	
}