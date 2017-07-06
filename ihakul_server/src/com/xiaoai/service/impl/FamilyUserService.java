package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IFamilyUserDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.util.XATools;
/**
 * 用户家庭组对照表业务实现类
 * @author Administrator
 *
 */
@Service("fauserService")
public class FamilyUserService implements IFamilyUserService {
	@Resource(name="fauserDao")
	private IFamilyUserDao fauserDao;
	@Override
	public FamilyUser selectFamilyUserByid(int id) {
		
		return fauserDao.selectFamilyUserByid(id);
	}

	@Override
	public List<FamilyUser> selectFamilyUser(FamilyUser fu) {
		StringBuffer sb= new StringBuffer("from FamilyUser as fu where 1=1");
		if(fu.getFamilygroup() !=null ){
			sb.append(" and fu.familygroup=:familygroup");
		}
		if(fu.getUsers() !=null){
			sb.append(" and fu.users=:users");
		}
		String hql=sb.toString();
		return fauserDao.selectFamilyUser(fu, hql);
	}

	@Transactional
	@Override
	public boolean updateFamilyUser(FamilyUser fu) {
		boolean fals=false;
		try {
			if(fu !=null){
				fauserDao.updateFamilyUser(fu);
				fals=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fals;

	}

	@Transactional
	@Override
	public boolean insertFamilyUser(FamilyUser fu) {
		boolean fals=false;
		try {
			if(fu !=null){
				fauserDao.insertFamilyUser(fu);
				fals=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fals;

	}

	@Transactional
	@Override
	public boolean deleteFamilyUser(FamilyUser fu) {
		boolean fals=false;
		try {
			if(fu !=null){
				fauserDao.deleteFamilyUser(fu);
				fals=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fals;

	}

	@Override
	public List<FamilyUser> selectFamilyUserByuserid(int userId) {
		
		return fauserDao.selectFamilyUserByuserid(userId);
	}

	@Override
	public List<FamilyUser> selectFamilyUserBygroupid(int groupId) {
		
		return fauserDao.selectFamilyUserBygroupid(groupId);
	}

	@Override
	public FamilyUser selectFamilyUserByGNU(Users users,Familygroup familygroup) {
		List<FamilyUser> fa=fauserDao.selectFamilyUserByGNU(users,familygroup);
		if(!XATools.isNull(fa)){
			return	fa.get(0);
		}
		return null;
	}
	
	
}
