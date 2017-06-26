package com.xiaoai.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IXiaoiSMSDao;
import com.xiaoai.entity.XiaoiSMS;

/**
 * @author ZERO
 * @version 2017-4-18 下午3:47:50
 * 短信平台用户实现类
 */
@Repository("xiaoiSMSDao")
public class XiaoiSMSDao implements IXiaoiSMSDao{
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	
	@Override
	public XiaoiSMS selectXiaoiSMSByid(int id) {
		return hibernateTemplate.get(XiaoiSMS.class, id);
	}

}
