package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Role;

/**
 * 权限管理类
 * @author Administrator
 *
 */
public interface IRoleDao {
	/**
	 * 根据id查询
	 * @param rid
	 * @return
	 */
	public Role selectRoleByid(int rid);
	/**
	 * 根据角色名字查询
	 * @param name
	 * @return
	 */
	public List<Role> selectRoleByname(String name);
	/**
	 * 初始化角色
	 */
	public void InitRole();
}
