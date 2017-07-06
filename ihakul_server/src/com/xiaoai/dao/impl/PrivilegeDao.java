package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IPrivilegeDao;
import com.xiaoai.entity.Privilege;
@Repository("privilegeDao")
public class PrivilegeDao implements IPrivilegeDao{
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	/**
	 * 根据id查询
	 */
	@SuppressWarnings("unchecked")
	public List<Privilege> findPrivilegeByid(int id) {
		String hql="from Privilege where rid=?";
		return hibernateTemplate.find(hql,id);
	}

}
