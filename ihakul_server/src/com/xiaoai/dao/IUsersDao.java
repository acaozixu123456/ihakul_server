package com.xiaoai.dao;

import java.util.List;



import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;


/**
 * 用户类操作
 * @author Administrator
 *
 */
public interface IUsersDao {
	/**
	 * 根据家庭组id查询用户
	 * @param fid 家庭组id
	 * @return
	 */
	public List<Users> selectUsersByfid(int fid);
	
	
	/**
	 * 查询所有用户
	 * @param 
	 * @return
	 */
	public List<Users> selectUsersAll();
	/**
	 * 删除
	 * @param users 
	 */
	public void deleteUsersByid(Users users);
	/**
	 * 修改
	 * @param users
	 */
	public void updateUsersByid(Users users);
	/**
	 * 根据id查询
	 * @param usersid
	 * @return
	 */
	public Users selectUsersByid(int usersid);
	
	/**
	 * 条件查询
	 * @param hql 查询条件
	 * @param offset 开始记录数
	 * @param length 一次查询记录数
	 * @return
	 */
	public List<Users> queryUsers(String hql, int offset, int showPage,Users users);

	/**
	 * 得到总记录数
	 * 
	 * @return 
	 */
	public int getAllUsersCount(String hql,Users users);
	
	/**
	 * 用户登入检查
	 * @param userLogin 用户账号(可以为手机号或用户名)
	 * @param userPassword 用户密码
	 * @return
	 */
	public Users usersLogin(String userLogin,String userPassword);
	
	/**
	 * 用户登入检查账号
	 * @param userLogin 用户账号(可以为手机号或用户名)
	 * @return
	 */
	public Users usersByuserLogin(String userLogin);
	
	
	/**
	 * 添加用户
	 * @param users
	 */
	public void saveUsers(Users users);
	/**
	 * 查询某个家庭组下的用户记录数
	 * @param fg 家庭组实体类
	 * @return
	 */
	public int getUsersCountByfamily(Familygroup familygroup);
	
	/**
	 * 分页查询某个家庭组下的用户
	 * @param fg  
	 * @param hql 查询的hql语句
	 * @param showPage 每页显示最大记录数
	 * @param beginPage 每页开始记录数
	 * @return 用户对象集合
	 */
	public List<Users> queryUsersByfamily(Familygroup familygroup,int showPage,int beginPage);

	
	/**
	 * 查询某个家庭组下的所有用户
	 * @param familygroup 家庭组对象
	 * @return 用户对象集合
	 */
	public List<Users> selectUsersByfg(Familygroup familygroup);
	
	
	/**
	 * 通过用户手机号来查询用户信息
	 * @param userPhoneNumber
	 * @return
	 */
	public List<Users> selectUsersByPh(String userPhoneNumber);
	
	
} 
