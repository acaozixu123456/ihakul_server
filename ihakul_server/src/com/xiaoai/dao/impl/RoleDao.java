package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IRoleDao;
import com.xiaoai.entity.Role;
/**
 * 权限管理操作类
 * @author Administrator
 *
 */
@Repository("roleDao")
@Transactional
public class RoleDao implements IRoleDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	/**
	 * 根据id查询权限
	 */
	public Role selectRoleByid(int rid) {
		
		return hibernateTemplate.get(Role.class, rid);
	}
	//根据角色名字查询
	@SuppressWarnings("unchecked")
	public List<Role> selectRoleByname(String name) {
		String hql="from Role where rolename=?";
		return  hibernateTemplate.find(hql,name);
	}
	@Override
	public void InitRole() {
		Role super_role = new Role();
		super_role.setRid(1);
		super_role.setRolename("高级管理员");
		
		Role simple_role = new Role();
		simple_role.setRid(2);
		simple_role.setRolename("普通管理员");
		hibernateTemplate.save(super_role);
		hibernateTemplate.save(simple_role);
	}

}
