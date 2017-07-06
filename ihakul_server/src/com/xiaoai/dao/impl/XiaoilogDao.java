package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IXiaoilogDao;
import com.xiaoai.entity.Xiaoilog;
/**
 * 工作日志持久化操作
 * @author Administrator
 *
 */
@Repository("xiaoilogDao")
public class XiaoilogDao implements IXiaoilogDao {
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	//删除
	public void deleteXiaoilogByid(Xiaoilog xiaoilog) {
		hibernateTemplate.delete(xiaoilog);

	}
	
	
	//按条件查询
	@SuppressWarnings({ "unused", "unchecked" })
	public List<Xiaoilog> selectXiaoilog(Xiaoilog xiaoilog,String hql,int showPage,int effor) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Transaction s=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setFirstResult(effor);
		query.setMaxResults(showPage);
		query.setProperties(xiaoilog);
		List<Xiaoilog> list=query.list();
		return list;
	}
	//得到总记录数
	@SuppressWarnings("unused")
	public int getCountXiaolog(Xiaoilog xiaoilog, String hql) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setProperties(xiaoilog);
		int  count=((Long)query.uniqueResult()).intValue();
		return count;
	}

	//根据id查询
	public Xiaoilog selectXiaoilogByid(int id) {
		
		return hibernateTemplate.get(Xiaoilog.class, id);
	}


	//查询导出报表
	@SuppressWarnings({ "unchecked" })
	public List<Xiaoilog> exportExecl(Xiaoilog xiaoilog, String hql) {
		List<Xiaoilog> list=hibernateTemplate.find(hql,xiaoilog);
		return list;
	}


	//添加
	public void insertXiaoilog(Xiaoilog xiaoilog) {
		hibernateTemplate.save(xiaoilog);
		
	}


	//修改
	public void updateXiaoilog(Xiaoilog xiaoilog) {
		hibernateTemplate.update(xiaoilog);
		
	}


	//根据小艾id查询
	@SuppressWarnings("unchecked")
	public List<Xiaoilog> selectXiaoilogByxid(int xid) {
		String hql=" from Xiaoilog where xid=?";
		List<Xiaoilog> list=hibernateTemplate.find(hql,xid);
		return list;
	}


	

}
