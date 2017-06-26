package com.xiaoai.service;

import java.util.List;



import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Xiaoi;

/**
 * 机器人小艾业务管理操作类
 * @author Administrator
 *
 */
public interface IXiaoiService {
	/**
	 * 根据家庭组id查询
	 * @param fid 家庭组id
	 * @return 机器人小艾对象集合
	 */
	public List<Xiaoi> selectXiaoiByid(int fid);
	
	/**
	 * 删除
	 * @param xiaoi 终端小艾对象
	 * @return 删除终端对象成功(true)与失败(false)的判断标志位
	 */
	public boolean delete(Xiaoi xiaoi);
	
	
	/**
	 * 更换终端
	 * @param xiaoNumber 终端编号
	 * @param newxiaoNumber 更改的终端编号
	 * @return 删除终端对象成功(true)与失败(false)的判断标志位
	 */
	public boolean change(String xiaoNumber,String newxiaoNumber);
	
	/**
	 * 根据编号查询
	 * @param xiaoNumber 终端小艾编号
	 * @return 终端小艾对象
	 */
	public Xiaoi selectXiaoiByNumber(String xiaoNumber);
	/**
	 * 修改
	 * @param xiaoi 终端小艾
	 * @return 修改终端小艾对象成功(true)与失败(false)的判断标志位
	 */
	public boolean updateXiaoi(Xiaoi xiaoi);
	/**
	 * 条件分页查询
	 * @param offset 每页开始记录数 
	 * @param showPage 每页显示最大记录数
	 * @param xiao 终端小艾对象
	 * @return 查询出的终端小艾集合
	 */
	public List<Xiaoi> findAll(int offset,int showPage,Xiaoi xiao); 
	
	/**
	 * 得到总记录数
	 * @param xiao 终端小艾对象
	 * @return 终端小艾对象的总记录数
	 */
	public int getXiaoiCount(Xiaoi xiao);
	/**
	 * 根据id查询
	 * @param xid 终端小艾id 
	 * @return 终端小艾对象
	 */
	public Xiaoi getXiaoiByid(int xid);
	/**
	 * 添加
	 * @param xiaoi 终端小艾对象
	 * @return 添加终端小艾对象成功(true)与失败(false)的判断标志位
	 */
	public boolean insertXiaoi(Xiaoi xiaoi);
	
	/**
	 * 根据房间id查询
	 * @param roomId 房间id
	 * @return 查询出的终端小艾对象集合
	 */
	public List<Xiaoi> selectXiaoiByroomId(int roomId);
	/**
	 * 根据ip地址查询
	 * @param ip ip地址
	 * @return 查询出的终端小艾对象集合
	 */
	public List<Xiaoi> selectXiaoiByIp(String ip);
	
	
	/**
	 * 查询家庭组下的在线的终端
	 * @param Familygroup
	 * @return Xiaoi
	 */
	public Xiaoi selectXiaoiByFa(Familygroup fa);
	
}
