package com.xiaoai.dao.impl;

import javax.annotation.Resource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.xiaoai.dao.IXiaoiSMSMessageDao;
import com.xiaoai.entity.XiaoiSMSMessage;

/**
 * @author ZERO
 * @version 2017-4-18 下午3:53:37
 * 类说明
 */
@Repository("xiaoSMSMessageDao")
public class XiaoSMSMessageDao implements IXiaoiSMSMessageDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	
	@Override
	public XiaoiSMSMessage selectXiaoiSMSMessageByid(int id) {
		return hibernateTemplate.get(XiaoiSMSMessage.class, id);
	}

}
