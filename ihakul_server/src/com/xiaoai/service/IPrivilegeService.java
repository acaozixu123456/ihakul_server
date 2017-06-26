package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Privilege;

public interface IPrivilegeService {
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public List<Privilege> findPrivilegeByid(int id);
}
