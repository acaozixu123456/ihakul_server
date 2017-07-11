package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IHouseholdDao;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;

import com.xiaoai.service.IHouseholdService;

/**
 * 家电业务操作实现类
 * 
 * @author Administrator
 * 
 */
@Service("houseHoldService")
public class HouseholdService implements IHouseholdService {
	@Resource(name = "houseHoldDao")
	private IHouseholdDao houseHoldDao;

	// 条件分页查询
	public List<Household> findHouseHold(int showPage, int effor,
			Household houseHold) {
		StringBuffer sql = new StringBuffer("from Household as house where 1=1");
		if (houseHold.getFamilygroup() != null) {
			sql.append(" and house.familygroup=:familygroup");
		}
		if (houseHold.getBrand() != null) {
			sql.append(" and house.brand=:brand");
		}
		if (houseHold.getEaName() != null) {
			sql.append(" and house.eaName=:eaName");
		}
		String hql = sql.toString();
		return houseHoldDao.findHousehold(showPage, effor, hql, houseHold);
	}

	// 得到总记录数
	public int getCountHouseHold(Household houseHold) {
		StringBuffer sql = new StringBuffer(
				"select count(*) from Household as house where 1=1");
		if (houseHold.getFamilygroup() != null) {
			sql.append(" and house.familygroup=:familygroup");
		}
		if (houseHold.getBrand() != null) {
			sql.append(" and house.brand=:brand");
		}
		if (houseHold.getEaName() != null) {
			sql.append(" and house.eaName=:eaName");
		}
		String hql = sql.toString();

		return houseHoldDao.getCountHouseHold(hql, houseHold);
	}

	@Transactional
	// 删除
	@Caching(evict = { @CacheEvict(value = "hakuCache_houseHold", allEntries = true) })
	public boolean deleteHousehold(int hid) {
		boolean fals = true;
		try {
			Household houseHold = houseHoldDao.selectHouseholdByid(hid);
			houseHoldDao.deleteHousehold(houseHold);
		} catch (Exception e) {
			fals = false;
			e.printStackTrace();
		}

		return fals;
	}

	// 根据id查询
	@Cacheable(value="hakuCache_houseHold",key="'selectHouseholdByid'+#id")
	public Household selectHouseholdByid(int id) {
		Household houseHold = houseHoldDao.selectHouseholdByid(id);

		return houseHold;
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "hakuCache_houseHold", key = "'getRoomByRoomNumber1'+#houseHold.getFamilygroup().getGroupNumber()+#houseHold.getEaNumber()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdBygroupId'+#houseHold.getFamilygroup().getGroupId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByroomIDandGroupId'+#houseHold.getRoom().getId()+#houseHold.getFamilygroup().getGroupId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByeaName'+#houseHold.getEaName()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByroomID'+#houseHold.getRoom().getId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByid'+#houseHold.getHid()"), })
	// 修改
	public boolean updateHouseholdByid(Household houseHold) {
		boolean fals = true;
		try {
			houseHoldDao.updateHouseholdByid(houseHold);
		} catch (Exception e) {
			fals = false;
			e.printStackTrace();
		}
		return fals;
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "hakuCache_houseHold", key = "'getRoomByRoomNumber1'+#houseHold.getFamilygroup().getGroupNumber()+#houseHold.getEaNumber()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdBygroupId'+#houseHold.getFamilygroup().getGroupId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByroomIDandGroupId'+#houseHold.getRoom().getId()+#houseHold.getFamilygroup().getGroupId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByeaName'+#houseHold.getEaName()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByroomID'+#houseHold.getRoom().getId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByid'+#houseHold.getHid()"), })
	// 添加
	public boolean insertHousehold(Household houseHold) {
		boolean fals = true;

		try {
			houseHoldDao.insertHousehold(houseHold);
		} catch (Exception e) {
			fals = false;
			e.printStackTrace();
		}

		return fals;
	}

	// 根据房间id查询家电
	@Cacheable(value="hakuCache_houseHold",key="'selectHouseholdByroomID'+#roomId")
	public List<Household> selectHouseholdByroomID(int roomId) {
		List<Household> list = houseHoldDao.selectHouseholdByroomID(roomId);
		return list;
	}

	// 根据家电名字查询家电
	@Cacheable(value = "hakuCache_houseHold", key =
	"'selectHouseholdByeaName'+#eaName")
	public List<Household> selectHouseholdByeaName(String eaName) {

		return houseHoldDao.selectHouseholdByeaName(eaName);
	}

	@Cacheable(value="hakuCache_houseHold",key="'selectHouseholdByroomIDandGroupId'+#id+#groupId")
	@Override
	public List<Household> selectHouseholdByroomIDandGroupId(Integer id,
			Integer groupId) {

		return houseHoldDao.selectHouseholdByroomIDandGroupId(id, groupId);
	}

	@Cacheable(value="hakuCache_houseHold",key="'getRoomByRoomNumber1'+#family.getGroupNumber()+#string")
	@Override
	public List<Household> getRoomByRoomNumber1(Familygroup family,
			String string) {

		return houseHoldDao.getRoomByRoomNumber1(family, string);
	}

	@Cacheable(value="hakuCache_houseHold",key="'selectHouseholdBygroupId'+#groupId")
	@Override
	public List<Household> selectHouseholdBygroupId(Integer groupId) {
		return houseHoldDao.selectHouseholdBygroupId(groupId);
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "hakuCache_houseHold", key = "'getRoomByRoomNumber1'+#houseHold.getFamilygroup().getGroupNumber()+#houseHold.getEaNumber()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdBygroupId'+#houseHold.getFamilygroup().getGroupId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByroomIDandGroupId'+#houseHold.getRoom().getId()+#houseHold.getFamilygroup().getGroupId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByeaName'+#houseHold.getEaName()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByroomID'+#houseHold.getRoom().getId()"),

			@CacheEvict(value = "hakuCache_houseHold", key = "'selectHouseholdByid'+#houseHold.getHid()"), })
	@Override
	public boolean deleteHousehold(Household houseHold) {
		boolean flag = true;
		try {
			houseHoldDao.deleteHousehold(houseHold);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

}
