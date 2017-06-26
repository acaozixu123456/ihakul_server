package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IXiaoitaskDao;
import com.xiaoai.entity.Xiaoitask;

/**
 * @author ZERO
 * @Description 定时任务操作实现类
 */
@Repository("xiaoitaskDao")
public class XiaoitaskDao implements IXiaoitaskDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void insertXiaoitask(Xiaoitask xiaoitask) {
		hibernateTemplate.save(xiaoitask);
	}

	@Override
	public void updateXiaoitask(Xiaoitask xiaoitask) {
		hibernateTemplate.update(xiaoitask);
	}

	@Override
	public void deleteXiaoitask(Xiaoitask xiaoitask) {
		hibernateTemplate.delete(xiaoitask);
	}

	@Override
	public Xiaoitask selectXiaoitaskById(long id) {
		return hibernateTemplate.get(Xiaoitask.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Xiaoitask> selectXiaoitaskByGroupId(int groupId) {
		return hibernateTemplate.find("from Xiaoitask where groupId=?", groupId);
	}
	
	

}
