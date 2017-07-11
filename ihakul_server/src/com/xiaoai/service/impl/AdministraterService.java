package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.xiaoai.dao.IAdministrateDao;
import com.xiaoai.entity.Administrate;
import com.xiaoai.service.IAdministraterService;
import com.xiaoai.util.XATools;

/**
 * 管理员业务层实体类
 * @author Administrator
 *
 */
@Service("adminService")
public class AdministraterService implements IAdministraterService {
	@Resource(name="adminDao")
	private IAdministrateDao adminDao;
	
	//判断是否有该用户
	public Administrate selectAdmiByad(String aname) {
		List<Administrate> list=adminDao.selectAdmiByad(aname);
		if(!XATools.isNull(list)){   
			return list.get(0);     
		}
		return null;
	}
	
	
	//判断用户名和密码是否能登录
	public Administrate selectAdmiByadmin(String aname, String password) {
		List<Administrate> list=adminDao.selectAdmiByadmin(aname, password);
		if(!XATools.isNull(list)){   
			return list.get(0);     
		}
		return null;
	}

	//查询所有信息
	public List<Administrate> selectAll(int offset,int length ) {
		List<Administrate> list=adminDao.selectAll(offset,length);
		
		return list;
	}
	
	
	public int getCountAdmin() {
		
		return adminDao.getCountAdmin();
	}
	
	
	public Administrate selectAdminByid(int aid) {
		
		return adminDao.selectAdminByid(aid);
	}

	@Transactional
	public boolean updateAdmin(Administrate admin) {
		
		return adminDao.updateAdmin(admin);
	}

	@Transactional
	public boolean insertAdmin(Administrate admin) {
		admin.setCreatedate(XATools.getNowTime());
		return adminDao.insertAdmin(admin);
	}

	@Transactional
	public boolean deleteAdminByid(Administrate admin) {
		
		return adminDao.deleteAdminByid(admin);
	}

	

	



	
	
}
