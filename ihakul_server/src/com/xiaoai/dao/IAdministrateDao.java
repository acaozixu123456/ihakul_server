package com.xiaoai.dao;



import java.util.List;

import com.xiaoai.entity.Administrate;
/**
 * 管理员管理接口类
 * @author Administrator
 *
 */
public interface IAdministrateDao {
	/**
	 * 登入检查
	 * @param aname 账号
	 * @return 
	 */
	public List<Administrate> selectAdmiByad(String aname);
	
	
	/**
	 * 登入
	 * @param aname 账号
	 * @param password 密码
	 * @return 
	 */
	public List<Administrate> selectAdmiByadmin(String aname,String password);
	
	/**
	 * 查询所有
	 * @param offset 每页开始记录数
	 * @param length 每页显示最大记录数
	 * @return 查询出的管理员结果集
	 */
	public List<Administrate> selectAll(int offset,int length );
	
	/**
	 * 得到总记录数
	 * @return
	 */
	public int getCountAdmin();
	
	/**
	 * 根据id查询管理员
	 * @param aid
	 * @return 查询出的管理员
	 */
	public Administrate selectAdminByid(int aid);
	
	/**
	 * 修改
	 * @param admin 管理员实体类
	 * @return  修改成功与否的判断标志位（true(修改成功)/false(修改失败)）
	 */
	public boolean updateAdmin(Administrate admin);
	
	/**
	 * 添加管理员
	 * @param admin
	 * @return
	 */
	public boolean insertAdmin(Administrate admin);
	
	/**
	 * 删除
	 * @param admin
	 * @return
	 */
	public boolean deleteAdminByid(Administrate admin);
	
	
}
