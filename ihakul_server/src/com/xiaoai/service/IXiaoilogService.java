package com.xiaoai.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xiaoai.entity.Xiaoilog;

/**
 * 工作日志业务操作类
 * @author Administrator
 *
 */
public interface IXiaoilogService {
	/**
	 * 删除
	 * @param xiaoilog 工作日志对象
	 * @return 删除工作日志成功(true)与失败(false)的判断标志位
	 */
	public boolean deleteXiaoilogByid(Xiaoilog xiaoilog);
	
	
	/**
	 * 按条件查询
	 * @param xiaoilog
	 * @return
	 */
	public List<Xiaoilog> selectXiaoilog(Xiaoilog xiaoilog,int showPage,int effor);
	
	/**
	 * 得到总记录数
	 * @param xiaoilog
	 * @return
	 */
	public int getCountXiaoilog(Xiaoilog xiaoilog);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Xiaoilog selectXiaoilogByid(int id);
	/**
	 * 查询导出报表
	 * @param xiaoilog
	 * @return
	 */
	public HSSFWorkbook exportExecl(Xiaoilog xiaoilog);
	
	/**
	 * 添加
	 * @param xiaoilog
	 */
	public boolean insertXiaoilog(Xiaoilog xiaoilog);
	/**
	 * 修改
	 * @param xiaoilog
	 */
	public boolean updateXiaoilog(Xiaoilog xiaoilog) ;
	/**
	 * 根据小艾id查询
	 * @param xid
	 * @return
	 */
	public List<Xiaoilog> selectXiaoilogByxid(int xid) ;
}
