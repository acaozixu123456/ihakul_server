package com.xiaoai.service;




import com.xiaoai.entity.Role;

/**
 * 权限业务操作类
 * @author Administrator
 *
 */
public interface IRoleService {
	/**
	 * 根据id查询权限
	 * @param rid
	 * @return
	 */
	public Role selectRoleByid(int rid);
	/**
	 * 根据名字查询
	 * @param name 角色名字
	 * @return
	 */
	public Role selectRoleByname(String name);
	/**
	 * 初始化角色
	 * @return
	 */
	public Role InitRole();
}
