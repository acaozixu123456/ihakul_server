package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoai.dao.IHouseholdDao;
import com.xiaoai.dao.IRoomDao;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Room;
import com.xiaoai.service.IRoomService;
import com.xiaoai.util.XATools;
/**
 * 房间业务实现类
 * @author Administrator
 *
 */
@Service("roomService")
public class RoomService implements IRoomService {
	@Resource(name="roomDao")
	private IRoomDao roomDao;
	
	@Resource(name="houseHoldDao")
	private IHouseholdDao houseHoldDao;
	
	
	//查询所有房间
	public List<Room> selectRoom(Room room,int showPage,int begin){
		StringBuffer sb=new StringBuffer("from Room as r where 1=1");
		if(room.getFamilygroup() !=null){
			sb.append(" and r.familygroup=:familygroup");
		}
		if(room.getId() !=null){
			sb.append(" and r.id=:id");
		}
		String hql=sb.toString();
		List<Room> list=roomDao.selectRoom(hql, room, showPage, begin);
		return list;
	}
	//得到总记录数
	public int getCountRoom(Room room){
		StringBuffer sb=new StringBuffer("select count(*) from Rooom as r where 1=1 ");
		if(room.getFamilygroup() !=null){
			sb.append(" and r.familygroup=:familygroup");
		}
		if(room.getId() !=null){
			sb.append(" and r.id=:id");
		}
		String hql=sb.toString();
		int count=roomDao.getCountRoom(hql, room);
		return count;
	}
	//添加
	public boolean insertRoom(Room room) {
		boolean fals=true;
		
		try {
			if(room.getFamilygroup() !=null){
				roomDao.insertRoom(room);
			}else{
				fals=false;
			}
			
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		
		return fals;
	}

	//删除
	public boolean deleteRoom(Room room) {
		boolean fals=true;
		int id=room.getId();
		try {
			
			List<Household> houseHoldList=houseHoldDao.selectHouseholdByroomID(id);
			if(!XATools.isNull(houseHoldList)){
				for (Household household : houseHoldList) {
					houseHoldDao.deleteHousehold(household);//删除该房间下关联的家电信息
					
				}
			}
		
			roomDao.deleteRoom(room);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		
		return fals;
	}

	//根据id得到房间信息
	public Room getRoomByid(int id) {
		Room room=roomDao.getRoomByid(id);
		return room;
	}

	//修改
	public boolean updateRoom(Room room) {
		boolean fals=true;
		try {
			roomDao.updateRoom(room);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		
		return fals;
	}
	//根据家庭组id查询
	public List<Room> getRoomByGroupId(int groupId) {
		
		return roomDao.getRoomByGroupId(groupId);
	}

}
