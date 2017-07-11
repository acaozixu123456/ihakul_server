package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IFamilyUserDao;
import com.xiaoai.dao.IUsersDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.service.IUsersService;
import com.xiaoai.service.IXiaoilogService;
import com.xiaoai.util.XATools;

/**
 * 
 * 用户业务操作实现
 * @author Administrator
 *
 */
@Service("usersService")
public class UsersService implements  IUsersService{
	@Resource(name="usersDao")
	private IUsersDao usersDao;
	@SuppressWarnings("unused")
	@Resource(name="xiaoilogService")
	private IXiaoilogService xiaoilogService;
	@Resource(name="fauserDao")
	private IFamilyUserDao fauserDao;
	
	
	//根据家庭组id查询
	public List<Users> selectUsersByfid(int fid) {
		
		return usersDao.selectUsersByfid(fid);
	}

	//用户删除	
	@Transactional
	public boolean deleteUsersByid(Users users) {
		boolean fals=true;
		try{
			int id=users.getUserId();
			List<FamilyUser> falist=fauserDao.selectFamilyUserByuserid(id);
			if(falist.size()>0){
				for (FamilyUser familyUser : falist) {
					fauserDao.deleteFamilyUser(familyUser);
				}
			}
			usersDao.deleteUsersByid(users);
		}catch(Exception e){
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	//修改
	@Transactional
	public boolean updateUsersByid(Users users) {
		boolean fals=true;
		try {
			usersDao.updateUsersByid(users);
		} catch (Exception e) {
			fals=false;
		}
		
		return fals;
	}

	//根据id查询
	public Users selectUsersByid(int usersId) {
		
		return usersDao.selectUsersByid(usersId);
	}

	//条件分页查询
	public List<Users> queryUsers(int offset,int showPage,Users user) {
		
		StringBuilder sql=new StringBuilder("from Users as us where 1=1");
		if(user.getUserName() !=null){
			sql.append(" and us.userName=:userName");
		}
		if(user.getUserPhoneNumber() !=null){
			sql.append(" and us.userPhoneNumber=:userPhoneNumber");
		}
//		if(user.getFamilygroup() !=null){
//			sql.append(" and us.familygroup=:familygroup");
//		}
		List<Users> list=usersDao.queryUsers(sql.toString(), offset, showPage, user);
		return list;
		
	}

	//得到总记录数
	public int getAllUsersCount(Users user) {
		StringBuilder sql=new StringBuilder("select count(*) from Users as us where 1=1");
		if(user.getUserName() !=null){
			sql.append(" and us.userName=:userName");
		}
		if(user.getUserPhoneNumber() !=null){
			sql.append(" and us.userPhoneNumber=:userPhoneNumber");
		}
//		if(user.getFamilygroup() !=null){
//			
//			sql.append(" and us.familygroup=:familygroup");
//		}
		int count=usersDao.getAllUsersCount(sql.toString(), user);
		
		
		return count;
	}

	//用户登入
	public Users UsersLogin(String userPhoneNumber, String userPassword) {
		Users user=usersDao.usersLogin(userPhoneNumber, userPassword);
		return user;
	}
	
	

	//添加用户
	@Transactional
	public boolean saveUsers(Users users) {
		
		boolean fals=true;
		try {
			usersDao.saveUsers(users);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		
		return fals;
	}

	
	public int getUsersCountByfamily(Familygroup familygroup) {
		
		return usersDao.getUsersCountByfamily(familygroup);
	}

	
	public List<Users> queryUsersByfamily(Familygroup familygroup, 
			int showPage, int beginPage) {
		
		return usersDao.queryUsersByfamily(familygroup, showPage, beginPage);
	}

	
	public List<Users> selectUsersByfg(Familygroup familygroup) {
		
		return usersDao.selectUsersByfg(familygroup);
	}

	@Override
	public Users selectUsersByPh(String userPhoneNumber) {
		List<Users> us=usersDao.selectUsersByPh(userPhoneNumber);
		if(!XATools.isNull(us)){
			return us.get(0);
		}
		return null;
	}

	@Override
	public Users usersByuserLogin(String userLogin) {
		return usersDao.usersByuserLogin(userLogin);
	}

	

	
}
