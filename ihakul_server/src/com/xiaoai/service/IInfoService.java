package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Info;

/**
 * @author ZERO
 * @Data 2017-7-3 上午10:40:18
 * @Description 公告service
 */
public interface IInfoService {

	/**
	 * 获得所有公告
	 */
	public List<Info> getAllInfo();

	/**
	 * 新增公告
	 */
	public void insertInfo(Info info);
}
