package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Room;

/**
 * 房间业务操作类
 * @author Administrator
 *
 */
public interface IRoomService {
	/**
	 * 分页查询
	 * @param room 房间对象
	 * @param showPage 每页显示记录数
	 * @param begin    每页开始记录数
	 * @return  查询出的房间对象集合
	 */
	public List<Room> selectRoom(Room room,int showPage,int begin);
	/**
	 * 得到总记录数
	 * @param room 房间对象
	 * @return 查询出的总记录数
	 */
	public int getCountRoom(Room room);
	/**
	 * 添加
	 * @param room 房间对象
	 * @return 添加房间成功(true)或失败(false)的判断标志位
	 */
	public boolean insertRoom(Room room);
	/**
	 * 删除
	 * @param room 房间对象
	 * @return 删除房间成功(true)或失败(false)的判断标志位
	 */
	public boolean deleteRoom(Room room);
	/**
	 * 根据id得到Room
	 * @param id 房间id
	 * @return 房间对象
	 */
	public Room getRoomByid(int id);
	/**
	 * 修改
	 * @param room 房间对象
	 * @return 修改房间成功(true)或失败(false)的判断标志位
	 */
	public boolean updateRoom(Room room);
	
	/**
	 * 根据家庭组id查询
	 * @param groupId 家庭组
	 * @return 查询出的房间对象集合
	 */
	public List<Room> getRoomByGroupId(int groupId);
}
