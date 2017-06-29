package com.xiaoai.service.impl;




import java.util.List;
import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoai.dao.IFamilyUserDao;
import com.xiaoai.dao.IFamilygroupDao;
import com.xiaoai.dao.IHouseholdDao;
import com.xiaoai.dao.IRoomDao;
import com.xiaoai.dao.IXiaoiDao;
import com.xiaoai.dao.IXiaoiModeDao;
import com.xiaoai.dao.IXiaoitaskDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.entity.XiaoiMode;
import com.xiaoai.entity.Xiaoitask;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage.FamilyCode;
/**
 * 家庭组业务实现
 * @author Administrator
 *
 */
@Service("familyService")
@Transactional
public class FamilygroupService implements IFamilygroupService {
	@Resource(name="familyDao")
	private IFamilygroupDao familyDao;
	@Resource(name="fauserDao")
	private IFamilyUserDao fauserDao;
	
	@Resource(name="xiaoiDao")
	private IXiaoiDao xiaoiDao;
	@Resource(name="roomDao")
	private IRoomDao roomDao;
	@Resource(name="xiaoiModeDao")
	private IXiaoiModeDao xiaoiModeDao;
	@Resource(name="xiaoitaskDao")
	private IXiaoitaskDao xiaoitaskDao;
	
	@Resource(name="houseHoldDao")
	private IHouseholdDao householdDao;
	
	
	
	public List<Familygroup> queryFamilygroup( int offset, int length,Familygroup familygroup) {
		
		StringBuffer sql=new StringBuffer("from Familygroup as fa where 1=1");
	    if(familygroup.getGroupName() !=null){
	    	sql.append(" and fa.groupName=:groupName");
	    }
	    if(familygroup.getCity() !=null){
	    	sql.append(" and fa.city=:city");
	    }
	    if(familygroup.getCreationTime() !=null){
		   sql.append(" and fa.creationTime=:creationTime");
	   }
	   
	     
	    return familyDao.queryFamilygroup(sql.toString(),offset, length,familygroup);
	      
		 
	}

	
	public int getAllRowCount(Familygroup familygroup) {
		StringBuilder sql=new StringBuilder("select count(*) from Familygroup as fa where 1=1");
		 	if(familygroup.getGroupName() !=null){
		    	sql.append(" and fa.groupName=:groupName");
		    }
		    if(familygroup.getCity() !=null){
		    	sql.append(" and fa.city=:city");
		    }
		    if(familygroup.getCreationTime() !=null){
				   sql.append(" and fa.creationTime=:creationTime");
			   }
		  
		return familyDao.getAllRowCount(sql.toString(),familygroup);
	}

	
	public boolean insertFamilygroup(Familygroup family) {
		boolean fals=true;
		try{
//			Date date=new Date();
//			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			String nowTime=sf.format(date);
			family.setCreationTime(XATools.getTNowTime()); //转换成 Timestamp 的时间格式必须是 yyyy-MM-dd hh:mm:ss
			familyDao.insertFamilygroup(family);
		}catch(Exception e){
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	
	public boolean deleteFamilygroup(Familygroup family) {
		boolean fals=true;
		try{
		int groupId=family.getGroupId();
		List<FamilyUser> falist= fauserDao.selectFamilyUserBygroupid(groupId);			
		List<Xiaoi> xiaoList=xiaoiDao.selectXiaoiByid(groupId);
		List<Room> roomList=roomDao.getRoomByGroupId(groupId);
		List<Household> hslist=householdDao.selectHouseholdBygroupId(groupId);
		List<XiaoiMode> modeList = xiaoiModeDao.findAllModeByGroupNum(family.getGroupNumber());
		List<Xiaoitask> taskList = xiaoitaskDao.selectXiaoitaskByGroupId(groupId);
		if(!XATools.isNull(falist)){    //删除家庭组用户对照表
			for (FamilyUser fu : falist) {
				fauserDao.deleteFamilyUser(fu);
			}
		}
		
		if(!XATools.isNull(hslist)){    //删除家电数据
			for (Household hh : hslist) {
				householdDao.deleteHousehold(hh);
			}
		}
		
		if(!XATools.isNull(xiaoList)){//如果有与家庭组关联的小艾数据
			for (Xiaoi xiaoi : xiaoList) {
				 xiaoiDao.delete(xiaoi);
			}
		}
		
		if(!XATools.isNull(roomList)){//删除房间数据
			for (Room room : roomList) {
				roomDao.deleteRoom(room);
			}
		}
		
		if(!XATools.isNull(modeList)){//删除相关情景模式数据
			for (XiaoiMode xiaoiMode : modeList) {
				xiaoiModeDao.deleteMode(xiaoiMode);
			}
		}
		
		if(!XATools.isNull(modeList)){//删除相关计划任务数据
			for (Xiaoitask xiaoitask : taskList) {
				xiaoitaskDao.deleteXiaoitask(xiaoitask);
			}
		}
		familyDao.deleteFamilygroup(family);
	}catch(Exception e){
		fals=false;
		e.printStackTrace();
	}
		return fals;
	}


	public Familygroup getFamilygroupByid(int id) {
		
		return familyDao.getFamilygroupByid(id);
	}

	public Familygroup getFamilygroupByNumber(int groupNumber){
		return familyDao.getFamilygroupByNumber(groupNumber);
	}

	
	public boolean updateFamily(Familygroup familygroup) {
		boolean fals=true;
		try {
			familyDao.updateFamily(familygroup);
		} catch (Exception e) {
			fals=false;
		}
		return fals;

	}

	//根据家庭组编号查询
	public Familygroup getFamilygroupByName(String groupName) {
		List<Familygroup> list=familyDao.getFamilygroupByName(groupName);
		if(list.size()>0){
			return list.get(0);
		}		
		return null;
			
	}

	@Override
	public List<Familygroup> selectFamilygroupByusers(Users users) {
		
		return familyDao.selectFamilygroupByusers(users);
	}
	
	@Override
	public List<Users> selectusersByFamilygroup(Familygroup fa) {
		
		return familyDao.selectusersByFamilygroup(fa);
	}
		
	@Override
	public  JSONObject getFamilyGroup(String groupNumber){
		boolean success =true;
		String	message =null;
		Integer code=0;
		JSONObject json2=new JSONObject();
		JSONObject json5=new JSONObject();
		JSONArray array=new JSONArray();
		JSONArray array3=new JSONArray();
		JSONArray array5=new JSONArray();
		JSONArray array6=new JSONArray();
		
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
			//family=familyService.getFamilygroupByNumber(gn);
			family= getFamilygroupByNumber(gn);
		if(family !=null){
			json2.put("groupName", family.getGroupName()); //家庭组名称 
			json2.put("groupNumber", family.getGroupNumber()); //家庭编号
			json2.put("groupPassword", family.getGroupPassword()); //验证密码
			json2.put("managerId", family.getManagerId()); //创建家庭组用户ID	
			json.put("versionNumber", family.getVersionNumber());   //家庭组版本号
			user=selectusersByFamilygroup(family);
			Integer groupId = family.getGroupId();
			for(Users us:user){
				if(family.getManagerId().equals(us.getUserId())){
					json2.put("userName", us.getUserName()); //创建家庭组用户名称
				}
			}
			List<Xiaoi> xiaoList=xiaoiDao.selectXiaoiByid(groupId);
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
			//情景模式 
			List<XiaoiMode> xiaoiModes = xiaoiModeDao.findAllModeByGroupNum(family.getGroupNumber());
			//json2.put("xiaoiMode", JSONObject.toJSON(xiaoiModes));
			JSONObject js=new JSONObject();
			js.put("xiaoiMode", JSONObject.toJSON(xiaoiModes));
			array6.add(js);
			//房间
			List<Room> roomList=roomDao.getRoomByGroupId(groupId);
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
		    		 List<Household> householdList =householdDao.selectHouseholdByroomIDandGroupId(room.getId(),groupId);
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
		json.put("result2", array6);   //情景模式信息
		
		return json;	
	}


	@Override
	public Familygroup getFamilygroupByNumberNow(int groupNumber) {
		return familyDao.getFamilygroupByNumberNow(groupNumber);
	}




	
	
}
