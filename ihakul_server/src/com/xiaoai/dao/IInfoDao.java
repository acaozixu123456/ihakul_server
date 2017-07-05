package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Info;

/**
 * @author ZERO
 * @Data 2017-7-3 上午10:34:08
 * @Description 公告Dao
 */
public interface IInfoDao {

	/**
	 * 获得所有公告
	 */
	public List<Info> getAllInfo();

	/**
	 * 新增公告
	 * @param info
	 */
	public void insertInfo(Info info);
}
