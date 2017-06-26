package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Xiaoitask;


/**
 * @author ZERO
 * @Description  定时任务持久层
 */
public interface IXiaoitaskDao {
	
	
	/**
	 * 添加
	 * @param xiaoitask
	 */
	public void  insertXiaoitask(Xiaoitask xiaoitask);
	
	/**
	 * 修改
	 * @param xiaoitask
	 */
	public void updateXiaoitask(Xiaoitask xiaoitask);
	
	/**
	 * 删除
	 * @param xiaoitask 
	 */
	public void deleteXiaoitask(Xiaoitask xiaoitask);
	
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
	 * @return
	 */
	public List<Xiaoitask> findAllXiaoitasks();
}
