package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IXiaoiModeDao;
import com.xiaoai.entity.XiaoiMode;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:25:06
 * @Description 
 */
@Repository
public class XiaoiModeDao implements IXiaoiModeDao {

	@Resource
	private HibernateTemplate hibernateTemplate;
	@Override
	public void insertMode(XiaoiMode xiaoiMode) {
		hibernateTemplate.save(xiaoiMode);
	}
	@Override
	public void deleteMode(XiaoiMode xiaoiMode) {
		hibernateTemplate.delete(xiaoiMode);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<XiaoiMode> findModeById(int id) {
		List<XiaoiMode> list = hibernateTemplate.find("from XiaoiMode where mode = ?",id);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XiaoiMode> findAllModeByGroupNum(int groupNumber) {
		List<XiaoiMode> list = hibernateTemplate.find("from XiaoiMode where groupNumber = ?",groupNumber);
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<XiaoiMode> findById(long id) {
		List<XiaoiMode> list = hibernateTemplate.find("from XiaoiMode where id = ?",id);
		return list;
	}

}
