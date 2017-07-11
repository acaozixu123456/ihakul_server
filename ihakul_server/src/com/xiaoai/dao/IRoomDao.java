package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Xiaoi;

/**
 * 房间
 * @author Administrator
 *
 */
public interface IRoomDao {
	/**
	 * 查询所有房间
	 * @param room
	 * @param showPage 每页 显示记录数
	 * @param begin    煤业开始记录数
	 * @return
	 */
	public List<Room> selectRoom(String hql,Room room,int showPage,int begin);
	
	/**
	 * 得到总记录数
	 * @return
	 */
	public int getCountRoom(String sql,Room room);
	
	/**
	 * 添加房间
	 * @param room
	 */
	public void insertRoom(Room room);
	/**
	 * 删除
	 * @param room
	 */
	public void deleteRoom(Room room);
	
	
	/**
	 * 根据id查询得到Room
	 * @param id
	 * @return
	 */
	public Room getRoomByid(int id);
	/**
	 * 修改
	 * @param room
	 */
	public void updateRoom(Room room);
	
	/**
	 * 根据家庭组id查询
	 * @param groupId
	 * @return
	 */
	public List<Room> getRoomByGroupId(int groupId);
	
	/**
	 * 查询终端属于那几个房间
	 * @param xiaoi 终端对象
	 * @return 
	 */
	public List<Room> selectRoomByxiaoi(Xiaoi xiaoi);
	
	/**
	 * 通过终端绑定编号查询房间
	 * @param robot
	 * @return
	 */
	public Room getRoomByRobot(String robot);
	
	
	/**
	 * 通过终端创建编号查询房间
	 * @param createor
	 * @return
	 */
	public List<Room> getRoomByCreateor(String createor);

	public Room getRoomByGroupId(Familygroup family, String roomNumber);

	public Room getRoomByRoomNumber(String roomNumber, Familygroup family);
	
}
