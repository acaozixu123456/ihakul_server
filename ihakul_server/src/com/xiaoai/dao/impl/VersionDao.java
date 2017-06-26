package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IVersionDao;
import com.xiaoai.entity.Versions;
/**
 * 版本持久化操作实现类
 * @author Administrator
 *
 */
@Repository("versionDao")
@Transactional
public class VersionDao implements IVersionDao {
	
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	//条件分页查询
	@SuppressWarnings("unchecked")
	public List<Versions> findAllVersion(String hql, int showPage,
			int beginNumber, Versions version) {
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
	//	Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setMaxResults(showPage);
		query.setFirstResult(beginNumber);
		query.setProperties(version);
		List<Versions> list=query.list();
		
		return list;
	}

	//得到总记录数
	public int getCountVersion(String hql, Versions version) {
		
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
	//	Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setProperties(version);
		int  count=((Long)query.uniqueResult()).intValue();
		return count;
	}

	//添加
	public void insertVersion(Versions version) {
		hibernateTemplate.save(version);
		
	}

	//根据id查询
	public Versions selectVersionByid(int id) {
		
		return hibernateTemplate.get(Versions.class, id);
	}

	//修改
	public void updateVersion(Versions version) {
		hibernateTemplate.update(version);
		
	}

	//删除
	public void deleteVrtsion(Versions version) {
		hibernateTemplate.delete(version);
		
	}

	
	@SuppressWarnings("unchecked")
	public List<Versions> selectVersionByverNumber(Versions versions) {
		String sql="from Versions as v  where  v.versionNumber=:versionNumber";
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(sql);
		query.setProperties(versions);
		List<Versions> list=query.list();	
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Versions> selectVersionByvr(Versions versions) {
		String sql="from Versions as v  where  v.versionNumber=:versionNumber and v.versionPackage=:versionPackage";
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(sql);
		query.setProperties(versions);
		List<Versions> list=query.list();	
		return list;
	}
	
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<Versions> selectVersion() {
		
		return hibernateTemplate.find("from Versions");
	}
 
	
	@SuppressWarnings("unchecked")
	@Override
	public Versions selectMaxVersions(String versionPackage) {
		String hql="from Versions vs where vs.versionNumber=(select max(v.versionNumber) from Versions v where v.versionPackage=?)" +
				" AND vs.versionPackage=?  ";
		List<Versions> vs=hibernateTemplate.find(hql,versionPackage,versionPackage);
		if(vs.size()>0){
			return vs.get(0);
		}
		return null;
	}
	
	
}
