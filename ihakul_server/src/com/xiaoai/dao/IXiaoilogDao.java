package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Xiaoilog;

/**
 * 小艾工作日志
 * @author Administrator
 *
 */
public interface IXiaoilogDao {
	/**
	 * 删除
	 * @param xiaoilog
	 */
	public void deleteXiaoilogByid(Xiaoilog xiaoilog);
	
	/**
	 * 按条件分页查询 
	 * @param xiaoilog
	 * @param hql 查询的hql语句
	 * @param showPage 每页显示记录数
	 * @param effor 开始记录数
	 * @return
	 */
	public List<Xiaoilog> selectXiaoilog(Xiaoilog xiaoilog,String hql,int showPage,int effor);
	
	/**
	 * 根据id查询
	 * @param id 小艾工作日志id
	 * @return
	 */
	public Xiaoilog selectXiaoilogByid(int id);
	/**
	 * 得到总记录数
	 * @param xiaoilog 
	 * @param hql 
	 * @return
	 */
	public int getCountXiaolog(Xiaoilog xiaoilog,String hql);
	
	/**
	 * 查询导出execl报表
	 * @param xiaoilog
	 * @param hql
	 * @return
	 */
	public List<Xiaoilog> exportExecl(Xiaoilog xiaoilog,String hql);
	
	/**
	 * 添加
	 * @param xiaoilog
	 */
	public void insertXiaoilog(Xiaoilog xiaoilog);
	/**
	 * 修改
	 * @param xiaoilog
	 */
	public void updateXiaoilog(Xiaoilog xiaoilog);
	/**
	 * 根据小艾id查询
	 * @param xid
	 * @return
	 */
	public List<Xiaoilog> selectXiaoilogByxid(int xid);
}
