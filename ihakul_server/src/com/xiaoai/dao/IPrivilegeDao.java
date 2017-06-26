package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Privilege;

/**
 * 权限操作
 * @author Administrator
 *
 */
public interface IPrivilegeDao {
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public List<Privilege> findPrivilegeByid(int id);
}
