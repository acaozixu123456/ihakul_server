package com.xiaoai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IRoleDao;
import com.xiaoai.entity.Role;
import com.xiaoai.service.IRoleService;
@Service("roleService")
public class RoleService implements IRoleService {
	@Resource(name="roleDao")
	private IRoleDao roleDao;
	/**
	 * 根据id查询
	 */
	public Role selectRoleByid(int rid) {
		
		return roleDao.selectRoleByid(rid);
	}
	//根据角色名字查询
	public Role selectRoleByname(String name) {
		
		return roleDao.selectRoleByname(name).get(0);
	}
	@Transactional
	@Override
	public Role InitRole() {
		roleDao.InitRole();
		return null;
	}

}
