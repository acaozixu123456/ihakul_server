package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Xiaoitask;

/**
 * @author ZERO
 * @Description 定时任务操作类
 */
public interface IXiaoitaskService {
   

	/**
	 * 添加
	 * @param xiaoitask
	 */
	public boolean  insertXiaoitask(Xiaoitask xiaoitask);
	
	/**
	 * 修改
	 * @param xiaoitask
	 */
	public boolean updateXiaoitask(Xiaoitask xiaoitask);
	
	/**
	 * 删除
	 * @param xiaoitask 
	 */
	public boolean deleteXiaoitask(Xiaoitask xiaoitask);
	
	/**
	 * 根据id查询
	 * @param id
	 */
	public Xiaoitask selectXiaoitaskById(long id);
	
	
	/**
	 * 根据groupId查询
	 * @param id
	 */
	public List<Xiaoitask> selectXiaoitaskByGroupId(int groupId);
	
	/**
	 * 查找所有计划任务
	 */
	public List<Xiaoitask> findAllXiaoitasks();
}
