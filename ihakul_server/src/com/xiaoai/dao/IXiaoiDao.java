package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Xiaoi;

/**
 * 机器人小艾操作类
 * @author Administrator
 *
 */
public interface IXiaoiDao {
	/**
	 * 根据家庭组id查询
	 * @param fid
	 * @return
	 */
	public List<Xiaoi> selectXiaoiByid(int fid);
	
	/**
	 * 删除
	 * @param xiaoi
	 */
	public void delete(Xiaoi xiaoi);
	
	/**
	 * 根据编号查询
	 * @param fid
	 * @return
	 */
	public Xiaoi selectXiaoiByNumber(String  xiaoNumber);
	/**
	 * 修改
	 * @param xiaoi
	 */
	public void updateXiaoi(Xiaoi xiaoi);
	
	/**
	 * 条件 分页查询
	 * @param hql 查询hql语句
	 * @param offset 每页开始记录数
	 * @param showPage 每页显示记录数
	 * @param xiao 
	 * @return 
	 */
	public List<Xiaoi> findAll(String hql,int offset,int showPage, Xiaoi xiao);
	
	/**
	 * 得到总记录数
	 */
	public int getXiaoiCount(Xiaoi xiao,String hql);
	/**
	 * 根据id查询
	 * @param xid
	 * @return
	 */
	public Xiaoi getXiaoiByid(int xid);
	
	/**
	 * 添加
	 * @param xiaoi
	 */
	public void insertXiaoi(Xiaoi xiaoi);
	/**
	 * 根据房间id查询
	 * @param roomId 房间id
	 * @return
	 */
	public List<Xiaoi> selectXiaoiByroomId(int roomId);
	
	/**
	 * 根据小艾ip查询
	 * @param ip  终端小艾ip
	 * @return  
	 */
	public List<Xiaoi> selectXiaoiByIp(String ip);
	
	/**
	 * 查询房间下的终端
	 * @param room 房间对象
	 * @return
	 */
	public List<Xiaoi> selectXiaoiByroom(Room room);
	
	/**
	 * 查询家庭组下的在线的终端
	 */
	public List<Xiaoi> selectXiaoiByFa(Familygroup fa);

	/**
	 * 根据终端编号查询小艾（包括state=2）
	 * @param xiaoNumber
	 * @return
	 */
	public Xiaoi selectXiaoiByNumberAll(String xiaoNumber);
}
