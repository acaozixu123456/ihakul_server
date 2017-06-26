package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Datadictionary;

/**
 * 数据字典
 * @author Administrator
 *
 */
public interface IDatadictionaryDao {
	/**
	 * 查询所有数据字典数据
	 * @return
	 */
	public List<Datadictionary> findAll();
	
	/**
	 * 修改数据字典
	 * @param dd
	 */
	public void update(Datadictionary datadictionary);
	
	/**
	 * 添加数据字典
	 * @param datadictionary
	 */
	public void insert(Datadictionary datadictionary);
	
	/**
	 * 删除数据字典
	 * @param datadictionary
	 */
	public void delete(Datadictionary datadictionary);
	
	/**
	 * 分页查询
	 * @param begin  每页开始记录数
	 * @param showPage 每页显示最大记录数
	 * @param datadictionary  数据字典对象
	 * @return
	 */
	public List<Datadictionary> paginFind(int begin,int showPage,Datadictionary datadictionary,String hql);
	
	
	/**
	 * 查询总记录数
	 * @return
	 */
	public int getCountDatadictionary(String hql,Datadictionary datadictionary);
	
	/**
	 * 根据id查询
	 * @param id 数据字典的id
	 * @return
	 */
	public Datadictionary selectDatadictionaryByid(int id);
}
