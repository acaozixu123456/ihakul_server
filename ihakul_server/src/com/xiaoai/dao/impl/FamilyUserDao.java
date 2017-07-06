package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IFamilyUserDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
/**
 * 用户家庭组关系对照表管理实体类
 * @author Administrator
 *
 */
@Repository("fauserDao")
public class FamilyUserDao implements IFamilyUserDao{
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public FamilyUser selectFamilyUserByid(int id) {
		
		FamilyUser fu=hibernateTemplate.get(FamilyUser.class, id);
		return fu;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FamilyUser> selectFamilyUser(FamilyUser fu,String hql) {
		List<FamilyUser> list=hibernateTemplate.find(hql);
		return list;
	}

	@Override
	public void updateFamilyUser(FamilyUser fu) {
		hibernateTemplate.update(fu);
	}

	@Override
	public void insertFamilyUser(FamilyUser fu) {
		hibernateTemplate.save(fu);
	}

	@Override
	public void deleteFamilyUser(FamilyUser fu) {
		hibernateTemplate.delete(fu);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FamilyUser> selectFamilyUserByuserid(int userId) {
		return hibernateTemplate.find("from FamilyUser where userId=?",userId);
	}
  
	@SuppressWarnings("unchecked")
	@Override
	public List<FamilyUser> selectFamilyUserByGNU(Users users,Familygroup familygroup) {
		return hibernateTemplate.find("from FamilyUser where users=? and familygroup=?",users,familygroup);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FamilyUser> selectFamilyUserBygroupid(int groupId) {
		String hql="from FamilyUser where groupId=?";
		return hibernateTemplate.find(hql,groupId);
	}
	
	
}
