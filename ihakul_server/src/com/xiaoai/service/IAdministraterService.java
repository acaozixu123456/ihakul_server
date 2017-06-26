package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Administrate;

public interface IAdministraterService {
		
		/**
		 * 管理员登入检查
		 * @param aname 用户名
		 * @return  
		 */
	   public Administrate selectAdmiByad(String aname);
	
	   /**
		 * 管理员登入
		 * @param aname 用户名
		 * @param password  登入密码
		 * @return  
		 */
		public Administrate selectAdmiByadmin(String aname,String password);
		/**
		 * 分页查询所有
		 * @param offset 开始记录数
		 * @param length 每页显示最大记录数
		 * @return 返回查询出的管理员接口集
		 */
		public List<Administrate> selectAll(int offset,int length );
		/**
		 * 得到总记录数
		 * @return 
		 */
		public int getCountAdmin();
		/**
		 * 根据管理员id查询
		 * @param aid  管理员id
		 * @return 返回管理员对象
		 */
		public Administrate selectAdminByid(int aid);
		/**
		 * 修改
		 * @param admin 管理员对象
		 * @return 返回修改成功(true)与失败(false)的判断标志位
		 */
		public boolean updateAdmin(Administrate admin);
		/**
		 * 添加管理员
		 * @param admin 管理员对象
		 * @return 返回添加成功(true)与失败(false)的判断标志位
		 */
		public boolean insertAdmin(Administrate admin);
		/**
		 * 删除
		 * @param admin  管理员对象
		 * @return 返回删除成功(true)与失败(false)的判断标志位
		 */
		public boolean deleteAdminByid(Administrate admin);
	
}
