package com.xiaoai.service.impl;




import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
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
/**
 * 家庭组业务实现
 * @author Administrator
 *
 */
@Service("familyService")
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
		
		
	
}
