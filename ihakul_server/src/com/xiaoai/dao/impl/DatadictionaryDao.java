package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IDatadictionaryDao;
import com.xiaoai.entity.Datadictionary;
@Repository("datadicDao")
public class DatadictionaryDao implements IDatadictionaryDao{

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	//查询所有数据字典
	@SuppressWarnings("unchecked")
	public List<Datadictionary> findAll() {
		List<Datadictionary> list=hibernateTemplate.find("from Datadictionary");
		return list;
	}

	//修改数据字典数据
	public void update(Datadictionary datadictionary) {
		hibernateTemplate.update(datadictionary);
		
	}

	//添加数据字典数据
	public void insert(Datadictionary datadictionary) {
		hibernateTemplate.save(datadictionary);
		
	}

	//删除数据字典数据
	public void delete(Datadictionary datadictionary) {
		hibernateTemplate.delete(datadictionary);
		
	}

	//分页查询
	@SuppressWarnings("unchecked")
	public List<Datadictionary> paginFind(int begin, int showPage,
			Datadictionary datadictionary,String hql) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql);
		query.setFirstResult(begin);
		query.setMaxResults(showPage);
		query.setProperties(datadictionary);
		List<Datadictionary> list=query.list();
		return list;
	}

	//查询总记录数
	public int getCountDatadictionary(String hql,Datadictionary datadictionary) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql);
		query.setProperties(datadictionary);
		int count=((Long)query.uniqueResult()).intValue();
		return count;
	}

	//根据id查询
	public Datadictionary selectDatadictionaryByid(int id) {
		Datadictionary data=hibernateTemplate.get(Datadictionary.class, id);
		return data;
	}

}
