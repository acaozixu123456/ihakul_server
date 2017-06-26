package com.xiaoai.dao;

import java.util.List;



import com.xiaoai.entity.Versions;

/**
 * 版本持久化操作接口
 * @author Administrator
 *
 */
public interface IVersionDao {
	/**
	 * 分页条件查询
	 * @param hql sql语句
	 * @param showPage 每页显示做单记录数
	 * @param beginNumber 每页显示开始记录数
	 * @param version 版本实体类对象
	 * @return
	 */
	public List<Versions> findAllVersion(String hql,int showPage,int beginNumber,Versions version);
	/**
	 * 得到总记录数
	 * @param hql sql语句
	 * @param version  版本实体类对象
	 * @return
	 */
	public int getCountVersion(String hql,Versions version);
	
	/**
	 * 添加
	 * @param version
	 */
	public void insertVersion(Versions version);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Versions selectVersionByid(int id);
	
	/**
	 * 修改
	 * @param version
	 */
	public void updateVersion(Versions version);
	/**
	 * 删除
	 * @param version
	 */
	public void deleteVrtsion(Versions version);
	
	/**
	 * 根据版本号查询版本对象
	 * @param version 版本对象
	 * @return
	 */
	public List<Versions> selectVersionByverNumber(Versions versions);
	
	
	
	/**
	 * 根据版本号和包名查询版本对象
	 * @param version 版本对象
	 * @return
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
