package com.xiaoai.dao;

import java.util.List;



import com.xiaoai.entity.Household;

/**
 * 家电持久化操作类
 * @author Administrator
 *
 */
public interface IHouseholdDao {
	/**
	 * 条件分页查询
	 * @param showPage 每页显示记录数
	 * @param effor 每页开始记录数
	 * @param hql 执行的hql语句
	 * @param houseHold 家电对象
	 * @return
	 */
	public List<Household> findHousehold(int showPage,int effor,String hql,Household houseHold); 

	/**
	 * 得到总的记录数
	 * @param sql 执行sql语句
	 * @return
	 */
	public int getCountHouseHold(String sql,Household houseHold);
	
	/**
	 * 删除
	 * @param houseHold 
	 * @return 
	 */
	public boolean deleteHousehold(Household houseHold);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Household selectHouseholdByid(int id);
	/**
	 * 修改
	 * @param houseHold
	 */
	public void updateHouseholdByid(Household houseHold);
	
	/**
	 * 添加
	 * @param houseHold
	 */
	public void  insertHousehold(Household houseHold);
	/**
	 * 根据房间 id查询
	 * @param roomId
	 * @return
	 */
	public List<Household> selectHouseholdByroomID(int roomId);

	/**
	 * 根据家电名字查询
	 * @param eaName
	 * @return
	 */
	public List<Household> selectHouseholdByeaName(String eaName);
	
	/**
	 * 根据家电编号查询
	 * @param eaName
	 * @return
	 */
	public Household getRoomByRoomNumber(String hidNumber);
	
	/**
	 * 根据家庭组编号查询
	 * @param groupId
	 * @return
	 */
	public List<Household> selectHouseholdBygroupId(int groupId);
	
	
	/**
	 * 根据家庭组ID和房间ID查询
	 * @param groupId,roomId
	 * @return
	 */
	public List<Household> selectHouseholdByroomIDandGroupId(int roomId,int groupId);
	
}
