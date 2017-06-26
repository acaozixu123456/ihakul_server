package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Datadictionary;

/**
 * 数据字典业务层
 * @author Administrator
 *
 */
public interface IDatadictionaryService {
	/**
	 * 查询所有数据字典数据
	 * @return 返回查询出的数据字典集合
	 */
	public List<Datadictionary> findAll();
	/**
	 * 修改数据字典数据
	 * @param datadictionary  数据字典对象
	 * @return  返回修改成功(true)与失败(false)的状态标志位
	 */
	public boolean update(Datadictionary datadictionary);
	/**
	 * 添加数据字典数据
	 * @param datadictionary 数据字典对象
	 * @return 返回添加成功(true)与失败(false)的状态标志位
	 */
	public boolean insert(Datadictionary datadictionary);
	/**
	 * 删除数据字典数据
	 * @param datadictionary 数据字典对象
	 * @return 返回删除成功(true)与失败(false)的状态标志位
	 */
	public boolean delete(Datadictionary datadictionary);
	
	/**
	 * 分页查询
	 * @param begin 每页开始记录数
	 * @param showPage  每页显示最大记录数
	 * @param datadictionary 数据字典对象
	 * @return 
	 */
	public List<Datadictionary> paginFind(int begin,int showPage,Datadictionary datadictionary);
	/**
	 * 得到总记录数
	 * @param datadictionary
	 * @return 返回查询出的记录数总数
	 */
	public int getCountDatadictionary(Datadictionary datadictionary);
	/**
	 * 根据id查询
	 * @param id 数据字典id
	 * @return 返回一个数据字典对象
	 */
	public Datadictionary selectDatadictionaryByid(int id) ;
}
