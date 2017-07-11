package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IHouseholdDao;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.util.XATools;
/**
 * 家电持久化操作实现类
 * @author Administrator
 *
 */

@Repository("houseHoldDao")
public class HouseholdDao implements IHouseholdDao {
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	//条件分页查询
	@SuppressWarnings({ "unused", "unchecked" })
	public List<Household> findHousehold(int showPage, int effor, String hql,
			Household houseHold) {
//		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(hql);
		query.setFirstResult(effor);
		query.setMaxResults(showPage);
		query.setProperties(houseHold);
		List<Household> list=query.list(); 
		return list;
	}

	//得到总记录数
	@SuppressWarnings("unused")
	public int getCountHouseHold(String sql,Household houseHold) {
//		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Transaction trans=session.beginTransaction();
		Query query=session.createQuery(sql);
		query.setProperties(houseHold);
		int  count=((Long)query.uniqueResult()).intValue();
		return count;
	}

	//删除
	public boolean deleteHousehold(Household houseHold) {
		boolean success=true;
		try{
			hibernateTemplate.delete(houseHold);
		}catch(Exception e){
			e.printStackTrace();
			success=false;
		}
		return success;
	}

	//根据id查询
	public Household selectHouseholdByid(int id) {
		
		Household houseHold=hibernateTemplate.get(Household.class, id);
		return houseHold;
	}

	//修改
	public void updateHouseholdByid(Household houseHold) {
		hibernateTemplate.update(houseHold);
		
	}

	//添加
	public void insertHousehold(Household houseHold) {
		hibernateTemplate.save(houseHold);
		
	}

	//根据房间id查询
	@SuppressWarnings("unchecked")
	public List<Household> selectHouseholdByroomID(int roomId) {
		String hql="from Household where roomId=?";
		List<Household> list=hibernateTemplate.find(hql,roomId);
		return list;
	}
    
	@SuppressWarnings("unchecked")
	public List<Household> selectHouseholdByroomIDandGroupId(int roomId,int groupId) {
		String hql="from Household where roomId=? and groupId=?";
		List<Household> list=hibernateTemplate.find(hql,roomId,groupId);
		return list;
	}
	
	//根据家电名字查询
	@SuppressWarnings("unchecked")
	public List<Household> selectHouseholdByeaName(String eaName) {
		String hql="from Household where eaName";
		List<Household> list=hibernateTemplate.find(hql);
		return list;
	}
    //根据家电编号查询
	@SuppressWarnings("unchecked")
	public Household getRoomByRoomNumber(String eaNumber) {
		String hql="from Household where eaNumber=?";
		List<Household> list=hibernateTemplate.find(hql,eaNumber);
		if(!XATools.isNull(list)){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Household> getRoomByRoomNumber1(Familygroup familygroup, String eaNumber) {
		 String hql="from Household where familygroup=? and eaNumber=?";
		 List<Household> list=hibernateTemplate.find(hql,familygroup,eaNumber);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Household> selectHouseholdBygroupId(int groupId) {
		String hql="from Household where groupId=?";
		return hibernateTemplate.find(hql,groupId);
	}
	
}
