package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;

/**
 * 用户业务操作类
 * @author Administrator
 *
 */
public interface IUsersService {
	/**
	 * 根据家庭组id查询用户
	 * @param fid 家庭组id
	 * @return 查询出的用户对象集合
	 */
	public List<Users> selectUsersByfid(int fid);
	/**
	 * 删除
	 * @param users 用户对象
	 * @return 删除用户成功(true)与失败(false)的判断标志位
	 */
	public boolean deleteUsersByid(Users users);
	/**
	 * 修改
	 * @param users 用户对象
	 * @return 修改用户成功(true)与失败(false)的判断标志位
	 */
	public boolean updateUsersByid(Users users);
	/**
	 * 根据id查询
	 * @param usersId 用户Id
	 * @return 用户对象
	 */
	public Users selectUsersByid(int usersId);
	
	/**
	 * 分页查询
	 * @param hql 查询条件
	 * @param offset 开始记录数
	 * @param length 一次查询记录数
	 * @return
	 */
	public List<Users> queryUsers(int offset,int showPage,Users user);

	/**
	 * 条件查询得到总记录数
	 * @param users 用户对象
	 * @return 查询出的总记录数
	 */
	public int getAllUsersCount(Users users);
	
	/**
	 * 通过用户手机号来查询用户信息
	 * @param userPhoneNumber
	 * @return
	 */
	public Users selectUsersByPh(String userPhoneNumber);
	
	/**
	 * 用户表登入
	 * @param userPhoneNumber 用户手机号
	 * @param userPassword 用户密码
	 * @return 用户对象
	 */
	public Users UsersLogin(String userPhoneNumber,String userPassword);
	
	
	/**
	 * 添加用户
	 * @param users 用户对象
	 * @return 添加用户成功(true)与失败(false)的判断标志位
	 */
	public boolean saveUsers(Users users);
	
	
	/**
	 * 查询某个家庭组下的用户记总录数
	 * @param fg 家庭组实体类
	 * @return 某个家庭组下的用户总记录
	 */
	public int getUsersCountByfamily(Familygroup familygroup);
	
	/**
	 * 分页查询某个家庭组下的用户(多对多关系)
	 * @param fg  家庭组对象
	 * @param hql 查询的hql语句
	 * @param showPage 每页显示最大记录数
	 * @param beginPage 每页开始记录数
	 * @return 查询出的用户对象集合
	 */
	public List<Users> queryUsersByfamily(Familygroup familygroup,int showPage,int beginPage);

	/**
	 * 查询某个家庭组下的所有用户
	 * @param familygroup 家庭组对象
	 * @return 用户对象集合
	 */
	public List<Users> selectUsersByfg(Familygroup familygroup);
	/**
	 * 用户登入检查账号
	 * @param userLogin 用户账号(可以为手机号或用户名)
	 * @return
	 */
	public Users usersByuserLogin(String userLogin);
}
