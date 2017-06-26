package com.xiaoai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xiaoai.entity.XiaoiMode;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:27:58
 * @Description 情景模式
 */
public interface IXiaoiModeService {

	/**
	 * 新增情景模式
	 */
	public boolean insertMode(XiaoiMode xiaoiMode);

	/**
	 * 删除情景模式
	 * @return
	 */
	public boolean deleteMode(XiaoiMode xiaoiMode);

	/**
	 * 根据id查找Mode
	 * @param parseInt
	 * @return
	 */
	public List<XiaoiMode> findModeById(int parseInt);
	
	/**
	 * 根据groupNumber查找情景模式
	 */
	public List<XiaoiMode> findModeByGroupNum(int groupNumber);

	/**
	 * 根据ModeId查情景模式
	 * @param integer
	 * @return
	 */
	//public List<XiaoiMode> findByModeId(Integer integer);
}
