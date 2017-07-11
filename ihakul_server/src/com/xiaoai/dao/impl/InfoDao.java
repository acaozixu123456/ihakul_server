package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IInfoDao;
import com.xiaoai.entity.Info;

/**
 * @author ZERO
 * @Data 2017-7-3 上午10:36:20
 * @Description 公告daoImpl
 */
@Repository("infoDao")
public class InfoDao implements IInfoDao{

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Info> getAllInfo() {
		String hql = "from Info";
		List<Info> inList = hibernateTemplate.find(hql);
		return inList;
	}

	@Override
	public void insertInfo(Info info) {
		hibernateTemplate.save(info);
	}

}
