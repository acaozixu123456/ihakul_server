package com.xiaoai.service;

import java.util.List;



import com.xiaoai.entity.Household;

/**
 * 家电业务操作接口
 * @author Administrator
 *
 */
public interface IHouseholdService {
	/**
	 * 条件分页查询
	 * @param showPage 每页显示最大记录数
	 * @param effor  每页开始记录数
	 * @param houseHold 家电对象
	 * @return 返回查询的家电对象集合
	 */
	public List<Household> findHouseHold(int showPage,int effor,Household houseHold);
	/**
	 * 得到总记录数
	 * @param houseHold 家电对象
	 * @return 查询出的总记录数
	 */
	public int getCountHouseHold(Household houseHold);
	
	/**
	 * 删除
	 * @param houseHold 家电对象
	 * @return 删除成功(true)与失败(false)的判断标志位
	 */
	public boolean deleteHousehold(int  hid);
	/**
	 * 根据id查询
	 * @param id 家电id
	 * @return 家电对象
	 */
	public Household selectHouseholdByid(int id);
	
	/**
	 * 修改
	 * @param houseHold 家电对象
	 * @return 修改成功(true)与失败(false)的判断标志位 
	 */
	public boolean updateHouseholdByid(Household houseHold);
	
	/**
	 * 添加
	 * @param houseHold 家电对象
	 * @return 添加成功(true)与失败(false)的判断标志位
	 */
	public boolean insertHousehold(Household houseHold);
	/**
	 * 根据房间号查询家电
	 * @param roomId 房间id
	 * @return 查询出的家电对象集合
	 */
	public List<Household> selectHouseholdByroomID(int roomId) ;
	/**
	 * 根据家电名字查询
	 * @param eaName 家电名字
	 * @return 查询出的家电对象集合
	 */
	public List<Household> selectHouseholdByeaName(String eaName);
}
