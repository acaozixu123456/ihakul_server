package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IAdministrateDao;
import com.xiaoai.entity.Administrate;

/**
 * 管理员数据操作实现类
 * @author Administrator
 *
 */
@Repository("adminDao")
@Transactional
public class AdministrateDao implements IAdministrateDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	
	//管理员登入查询是否有该用户名
	@SuppressWarnings("unchecked")
	public List<Administrate> selectAdmiByad(String aname) {	
		String a1="from Administrate where aname=?";
		List<Administrate> list =hibernateTemplate.find(a1,aname);
		return list;
	}
	
	

	//管理员登入查询
	@SuppressWarnings("unchecked")
	public List<Administrate> selectAdmiByadmin(String aname,String password ) {
		String hql="from Administrate where aname=? and password=?";
		List<Administrate> list=hibernateTemplate.find(hql,aname,password);
	
		return list;
	}

	//分页查询所有记录数
	@SuppressWarnings("unchecked")
	public List<Administrate> selectAll(int offset,int length ) {
		String hql="from Administrate";
		//openSession这种方法浪费系统资源和影响执行效率
	   //Session session=hibernateTemplate.getSessionFactory().openSession();
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql);  //创建sql
		query.setMaxResults(length);
		query.setFirstResult(offset);
		List<Administrate> list=query.list();     //执行sql查询
		return list;
	}
	//得到总记录数
	@SuppressWarnings("unchecked")
	public int getCountAdmin() {
		String hql="from Administrate ";
		List<Administrate> list=hibernateTemplate.find(hql);
		return list.size();
	}
	//根据id查询 
	public Administrate selectAdminByid(int aid) {
		
		
		return hibernateTemplate.get(Administrate.class,aid);
	}

	/**
	 * 修改
	 */
	public boolean updateAdmin(Administrate admin) {
		
		boolean fals=false;
		try{
			hibernateTemplate.update(admin);
			fals=true;
		}catch(Exception e){
			fals=false;
			e.getStackTrace();
		}
			
		return fals;
	}

	/**
	 * 添加管理员
	 */
	public boolean insertAdmin(Administrate admin) {
		boolean fals=true;
		try{
			hibernateTemplate.save(admin);
		}catch(Exception e){
			fals=false;
			e.getStackTrace();
		}
		return fals;
	}

	/**
	 * 删除
	 * @see com.xiaoai.dao.IAdministrateDao#deleteAdminByid(int)
	 */
	public boolean deleteAdminByid(Administrate admin) {
		boolean fals=true;
		try{
			hibernateTemplate.delete(admin);
		}catch(Exception e){
			fals=false;
			e.getStackTrace();
		}
		
		return fals;
	}

	
	
	
	
}
