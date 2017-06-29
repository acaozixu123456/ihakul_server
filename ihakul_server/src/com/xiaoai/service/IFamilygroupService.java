package com.xiaoai.service;



import java.util.List;




import com.alibaba.fastjson.JSONObject;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
/**
 * 家庭组service层
 * @author Administrator
 *
 */
public interface IFamilygroupService {
	/**
	 * 按条件分页查询
	 * @param offset 每页开始记录数
	 * @param length 每页显示最大记录数
	 * @param familygroup 家庭组对象
	 * @return 返回查询出的家庭组结果集合
	 */
	public List<Familygroup> queryFamilygroup( int offset,int length,Familygroup familygroup);
	/**
	 * 条件分页查询得到总记录数
	 * @param familygroup 家庭组对象
 	 * @return 返回查询的总记录数
	 */
	public int getAllRowCount(Familygroup familygroup);
	/**
	 * 添加
	 * @param family 家庭组对象
	 * @return 返回添加成功(true)与失败(false)的判断标志位
	 */
	public boolean insertFamilygroup(Familygroup familygroup);
	/**
	 * 删除
	 * @param familygroup 家庭组对象
	 * @return 返回删除成功(true)与失败(false)的判断标志位
	 */
	public boolean deleteFamilygroup(Familygroup familygroup);
	/**
	 * 根据id查询家庭组
	 * @param id 家庭组id
	 * @return 返回家庭组对象
	 */
	public Familygroup getFamilygroupByid(int id);
	/**
	 * 根据编号查询家庭组
	 * @param groupNumber 家庭组编号
	 * @return 返回家庭组对象
	 */
	public Familygroup getFamilygroupByNumber(int groupNumber);
	/**
	 * 修改
	 * @param familygroup 家庭组对象
	 * @return 返回修改成功(true)与失败(false)的判断标志位 
	 */
	public boolean updateFamily(Familygroup familygroup);
	/**
	 *  根据家庭组名字查询
	 * @param groupName 家庭组名字
	 * @return 返回家庭组对象
	 */
	public Familygroup getFamilygroupByName(String groupName);
	/**
	 * 根据用户信息查询关联家庭组信息
	 * @param users 用户对象
	 * @return 返回(多对多)查询出的家庭组集合
	 */
	public List<Familygroup> selectFamilygroupByusers(Users users);
	
	
	/**
	 * 根据 家庭组信息查询关联用户信息
	 * @param Familygroup 用户对象
	 * @return 返回(多对多)查询出的家庭组集合
	 */
	public List<Users> selectusersByFamilygroup(Familygroup fa);
	
	public JSONObject getFamilyGroup(String groupNumber);
	/**
	 * 获得家庭组的所有信息（级联其他）
	 * @param parseInt
	 * @return
	 */
	public Familygroup getFamilygroupByNumberNow(int groupNumber);
}

