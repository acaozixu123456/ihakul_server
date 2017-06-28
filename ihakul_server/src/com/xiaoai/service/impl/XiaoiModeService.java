package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IXiaoiModeDao;
import com.xiaoai.entity.XiaoiMode;
import com.xiaoai.service.IXiaoiModeService;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:30:26
 * @Description 
 */
@Service("xiaoiModeService")
@Transactional
public class XiaoiModeService implements IXiaoiModeService {

	@Resource
	private IXiaoiModeDao xiaoiModeDao;
	@Override
	public boolean insertMode(XiaoiMode xiaoiMode) {
		boolean flag = true;
		try {
			xiaoiModeDao.insertMode(xiaoiMode);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	@Override
	public boolean deleteMode(XiaoiMode xiaoiMode) {
		boolean flag = true;
		try {
			xiaoiModeDao.deleteMode(xiaoiMode);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public List<XiaoiMode> findModeById(int id) {
		return xiaoiModeDao.findModeById(id);
	}

	@Override
	public List<XiaoiMode> findModeByGroupNum(int groupNumber) {
		 return xiaoiModeDao.findAllModeByGroupNum(groupNumber);
	}

	@Override
	public List<XiaoiMode> findById(int parseInt) {
		// TODO Auto-generated method stub
		return xiaoiModeDao.findById(parseInt);
	}

	/*@Override
	public List<XiaoiMode> findByModeId(Integer integer) {
		
		return xiaoiModeDao.findModeById(integer);
	}*/

}
