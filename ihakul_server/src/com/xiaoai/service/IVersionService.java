package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Versions;




/**
 * 版本业务接口
 * @author Administrator
 *
 */
public interface IVersionService {
	/**
	 * 条件分页查询
	 * @param version 版本实体类对象
	 * @param showPage 每页显示最大记录数
	 * @param beginNumber 每页显示开始记录数
	 * @return 查询出的版本对象集合
	 */
	public List<Versions> findAllVersion(Versions version,int showPage,int beginNumber);
	
	/**
	 * 得到总记录数
	 * @param version 版本对象
	 * @return 版本对象的总记录数
	 */
	public int getCountVersion(Versions version);
	/**
	 * 添加
	 * @param version 版本对象
	 * @return 添加版本成功(true)与失败(false)的判断标志位
	 */
	public boolean insertVersion(Versions version);
	/**
	 * 根据id查询
	 * @param id 版本id
	 * @return 版本对象
	 */
	public Versions selectVersionByid(int id);
	
	/**
	 * 修改
	 * @param version 版本对象
	 * @return 修改版本成功(true)与失败(false)的判断标志位
	 */
	public boolean updateVersion(Versions version);
	/**
	 * 删除
	 * @param version 版本对象
	 * @return 删除版本成功(true)与失败(false)的判断标志位
	 */
	public boolean deleteVrtsion(Versions version);
	/**
	 * 根据版本号查询
	 * @param versionNumber 版本号
	 * @return 版本对象
	 */
	public List<Versions> selectVersionByverNumber(Versions versions);
  
	
	/**
	 * 根据版本号和包名查询查询
	 * @param versionNumber 版本号
	 * @param versionPackage 版本包名 
	 * @return 版本对象
	 */
	public List<Versions> selectVersionByvr(Versions versions);

	
	/**
	 * 查询所有版本类容
	 * @return
	 */
	public List<Versions> selectVersion();
	/**
	 * 查询包名下最大版本号的值
	 * @return
	 */
	public Versions selectMaxVersions(String versionPackage);
}
