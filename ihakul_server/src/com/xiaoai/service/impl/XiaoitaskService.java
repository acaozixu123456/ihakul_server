package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IXiaoitaskDao;
import com.xiaoai.entity.Xiaoitask;
import com.xiaoai.service.IXiaoitaskService;

/**
 * @author ZERO
 * @Description 定时任务
 */
@Service("xiaoitaskService")
public class XiaoitaskService implements IXiaoitaskService {

	@Resource(name="xiaoitaskDao")
	private IXiaoitaskDao xiaoitaskDao;
	
	
	@Override
	@Transactional
	public boolean insertXiaoitask(Xiaoitask xiaoitask) {
		 boolean fals=true;
		 try{
			 xiaoitaskDao.insertXiaoitask(xiaoitask);
		 }catch(Exception e){
			 e.printStackTrace();
			 fals=false;
		 }
		return fals;
	}

	@Override
	@Transactional
	public boolean updateXiaoitask(Xiaoitask xiaoitask) {
		 boolean fals=true;
		 try{
			 xiaoitaskDao.updateXiaoitask(xiaoitask);
		 }catch(Exception e){
			 e.printStackTrace();
			 fals=false;
		 }
		return fals;
	}

	@Override
	@Transactional
	public boolean deleteXiaoitask(Xiaoitask xiaoitask) {
		 boolean fals=true;
		 try{
			 xiaoitaskDao.deleteXiaoitask(xiaoitask);
		 }catch(Exception e){
			 e.printStackTrace();
			 fals=false;
		 }
		return fals;
	}


	@Override
	public Xiaoitask selectXiaoitaskById(long id) {
		return xiaoitaskDao.selectXiaoitaskById(id);
	}

	@Override
	public List<Xiaoitask> selectXiaoitaskByGroupId(int groupId) {
		return xiaoitaskDao.selectXiaoitaskByGroupId(groupId);
	}

	@Override
	public List<Xiaoitask> findAllXiaoitasks() {
		return xiaoitaskDao.findAllXiaoitasks();
	}

}
