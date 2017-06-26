package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IXiaoiurl;
import com.xiaoai.entity.Xiaoiurl;
import com.xiaoai.util.XATools;

/**
 * @author ZERO
 * @Data 2017-5-25 上午10:49:33
 * @Description 
 */
@Repository("XiaoiurlDao")
public class XiaoiurlDao implements IXiaoiurl {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Xiaoiurl selectXiaoiupdateByid(int id) {
		return hibernateTemplate.get(Xiaoiurl.class, id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Xiaoiurl selectXiaoiupdateBynumber(int number) {
		String sql="from Xiaoiurl where urlNumber=?";
		List<Xiaoiurl> list=hibernateTemplate.find(sql,number);
		if(!XATools.isNull(list)){
			return list.get(0);
		}
		return null;
	}

}
