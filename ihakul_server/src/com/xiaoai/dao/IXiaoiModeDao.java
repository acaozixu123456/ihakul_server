package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.XiaoiMode;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:23:05
 * @Description 情景模式操作
 */
public interface IXiaoiModeDao {

	/**
	 * 增加情景模式
	 * @param xiaoiMode
	 */
	public void insertMode(XiaoiMode xiaoiMode);
	
	/**
	 * 删除情景模式
	 * @param xiaoiMode
	 */
	public void deleteMode(XiaoiMode xiaoiMode);

	/**
	 * 根据id查找Mode
	 * @param id
	 * @return
	 */
	public XiaoiMode findModeById(int id);
	
	/**
	 * 根据groupNumer查询所有Mode
	 */
	public List<XiaoiMode> findAllModeByGroupNum(int groupNumber);
}
