package com.xiaoai.dao.impl;



import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IUsersDao;


import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.util.XATools;

/**
 * 用户操作实现类
 * @author Administrator
 *
 */
@Repository("usersDao")
@Transactional
public class UsersDao implements IUsersDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	public List<Users> selectUsersByfid(int groupId) {
		String hql="from Users where groupId=?";
		return hibernateTemplate.find(hql,groupId);
	}
   
	@SuppressWarnings("unchecked")
	public List<Users> selectUsersAll() {
		String hql="from Users";
		return hibernateTemplate.find(hql);
	}
	
	public void deleteUsersByid(Users users) {
		hibernateTemplate.delete(users);

	}

	public void updateUsersByid(Users users) {
		hibernateTemplate.update(users);
		
	}

	
	public Users selectUsersByid(int userId) {
		
		return hibernateTemplate.get(Users.class, userId);
	}


	@SuppressWarnings({ "unused", "unchecked" })
	public List<Users> queryUsers(String hql, int offset, int showPage,
			Users users) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(showPage);
		query.setProperties(users);
		
		List<Users> list=query.list();
		return list;
	}

	/**
	 * 得到总记录数
	 */
	@SuppressWarnings("unused")
	public int getAllUsersCount(String hql,Users users) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setProperties(users);
		int  count=((Long)query.uniqueResult()).intValue();
		return count;
	}
	
	/**
	 * 用户登入检查
	 */
	@SuppressWarnings("unchecked")
	public Users usersLogin(String userLogin, String userPassword) {
		String  hql="";
		if(XATools.isMobileNO(userLogin)){ //如果是手机号
			hql="from Users where userPhoneNumber=? and userPassword=?";
		}else{
			hql="from Users where userName=? and userPassword=?";
		}	
		List<Users> list=hibernateTemplate.find(hql, userLogin,userPassword);	
		if(!XATools.isNull(list)){
			return list.get(0);
		}		
		return null;
	}
     
	/**
	 * 用户登入检查用户名
	 */
	@SuppressWarnings("unchecked")
	public Users usersByuserLogin(String userLogin) {
		String  hql="";
		if(XATools.isMobileNO(userLogin)){ //如果是手机号
			hql="from Users where userPhoneNumber=? ";
		}else{
			hql="from Users where userName=? ";
		}	
		List<Users> list=hibernateTemplate.find(hql, userLogin);	
		if(!XATools.isNull(list)){
			return list.get(0);
		}		
		return null;
	}
	
	public void saveUsers(Users users) {
		hibernateTemplate.saveOrUpdate(users);
		
	}


	@Override
	public int getUsersCountByfamily(Familygroup familygroup) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql="select count(users.userId) from Users as users join users.familygroup as family where family=:familygroup";
		Query query=session.createQuery(sql);
		int count= ((Long)query.setParameter("familygroup", familygroup).uniqueResult()).intValue();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> queryUsersByfamily(Familygroup familygroup,
			int showPage, int beginPage) {
		String sql="select users from Users as users join users.familygroup as family where family=:familygroup";
	
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(sql);
		query.setFirstResult(beginPage);
		query.setMaxResults(showPage);
		query.setParameter("familygroup", familygroup);
		List<Users> list=query.list();
		return list;
	}


	
	@SuppressWarnings("unchecked")
	public List<Users> selectUsersByfg(Familygroup familygroup) {
		String sql="select users from Users as users join users.familygroup as family where family=:familygroup";
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(sql);
		query.setParameter("familygroup", familygroup);
		List<Users> list=query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> selectUsersByPh(String userPhoneNumber) {
		String hql="from Users where userPhoneNumber=?";
		return hibernateTemplate.find(hql,userPhoneNumber);
	}

	

}
