package com.xiaoai.dao.impl;

import java.util.List;


import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IRoomDao;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.util.XATools;
@Repository("roomDao")
public class RoomDao implements IRoomDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	//查询所有房间
	@SuppressWarnings("unchecked")
	public List<Room> selectRoom(String hql,Room room,int showPage,int begin) {
		
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql);
		query.setFirstResult(begin);
		query.setMaxResults(showPage);
		query.setProperties(room);
		List<Room> list=query.list();
		return list;
		
	}
	//得到总记录数
	public int getCountRoom(String sql,Room room){
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(sql);
		query.setProperties(room);
		int count=((Long)query.uniqueResult()).intValue();
		return count;
	}
	//添加
	public void insertRoom(Room room) {
		hibernateTemplate.save(room);

	}

	//删除
	public void deleteRoom(Room room) {
		hibernateTemplate.delete(room);

	}

	//根据id得到Room
	public Room getRoomByid(int id) {
		Room room=hibernateTemplate.get(Room.class, id);
		return room;
	}

	//修改
	public void updateRoom(Room room) {
		hibernateTemplate.update(room);

	}
	//根据家庭组查询
	@SuppressWarnings("unchecked")
	public List<Room> getRoomByGroupId(int groupId) {
		String hql="from Room where groupId=?";
		List<Room> list=hibernateTemplate.find(hql,groupId);
		return list;
	}
	
	//根据房间编号来查询
	@SuppressWarnings("unchecked")
	public Room getRoomByRoomNumber(String roomNumber,Familygroup familygroup) {
		String hql="from Room where roomNumber=? and groupId=?";
		List<Room> list=hibernateTemplate.find(hql,roomNumber,familygroup.getGroupId());
		if(!XATools.isNull(list)){
			return list.get(0);
		}
		return null;
	}
	
	//根据终端编号来查询
	@SuppressWarnings("unchecked")
	public Room getRoomByRobot(String robot) {
		String hql="from Room where robot=?";
		List<Room> list=hibernateTemplate.find(hql,robot);
		if(!XATools.isNull(list)){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Room> getRoomByCreateor(String createor) {
		String hql="from Room where creator=?";
		return hibernateTemplate.find(hql,createor);
	}
	
	/*
	 * 根据家庭组和房间编号来查询*/
	 // 在hi中可以使用别名的方式来查询，格式是 :xxx 通过setParameter来设置别名
	@SuppressWarnings("unchecked")
	public Room getRoomByGroupId(Familygroup familygroup,String roomNumber) {
		String hql="from Room where familygroup=? and roomNumber=?";
		List<Room> list=hibernateTemplate.find(hql,familygroup,roomNumber);
		if(!XATools.isNull(list)){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Room> selectRoomByxiaoi(Xiaoi xiaoi) {
		String sql="select rooms from Room as rooms join rooms.xiaois as xiao where xiao=?";
		List<Room> list = hibernateTemplate.find(sql,xiaoi);
		return list;
	}
	

}
