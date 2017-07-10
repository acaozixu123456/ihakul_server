package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoai.dao.IFamilygroupDao;
import com.xiaoai.dao.IHouseholdDao;
import com.xiaoai.dao.impl.HouseholdDao;
import com.xiaoai.dao.impl.RoomDao;
import com.xiaoai.dao.impl.XiaoiDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.mina.service.push.PushMessage_Room;
import com.xiaoai.mina.service.push.PushMessage_houseHoldController;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IHouseholdService;
import com.xiaoai.service.IRoomService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
import com.xiaoai.util.XiaoiResult;
import com.xiaoleilu.hutool.util.BeanUtil;

/**
 * 家电管理操作实现类
 * 
 * @author Administrator
 * 
 */
@Controller("apphouseAction")
@Scope("prototype")
public class AppHouseholdAction extends XiaoaiMessage {
	@Resource(name = "houseHoldService")
	private IHouseholdService houseHoldService;

	@Resource(name = "roomService")
	private IRoomService roomService;
	@Resource(name = "usersService")
	private IUsersService usersService;
	@Resource(name = "roomDao")
	private RoomDao roomDao;
	@Resource(name = "houseHoldDao")
	private IHouseholdDao householdDao;
	@Resource(name = "familyDao")
	private IFamilygroupDao familyDao;
	@Resource(name = "familyService")
	private IFamilygroupService familyService;
	@Resource(name = "xiaoiService")
	private IXiaoiService xiaoiService;
	@Resource(name = "xiaoiDao")
	private XiaoiDao xiaoiDao;
	
	@Resource(name = "fauserService")
	private IFamilyUserService fauserService;
	private boolean success; // 成功、失败标记
	private String message; // 信息
	private int code; // 标记
	private static Logger logger = Logger.getLogger(AppHouseholdAction.class);

	/**
	 * 添加家电
	 * 
	 * @param brand
	 *            品牌
	 * @param model
	 *            型号
	 * @param hhName
	 *            家电名字
	 * @param typeId
	 *            家电类型id
	 * @param roomNumber
	 *            房间编号
	 * @param groupNumber
	 *            家庭组编号
	 * @param userId
	 *            用户id
	 * @param hidNumber
	 *            家电编号
	 * @param props
	 *            通讯参数，见链路协议
	 * @param stub
	 *            智能索引(Key)
	 * @param groupbbh
	 *            家庭组版本号
	 * @return success=true(修改成功)或者success=false(修改失败)
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String insert() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("添加家电的入参");
		String brand = request.getParameter("brand"); // 品牌
		String model = request.getParameter("model"); // 型号
		String eaName = request.getParameter("eaName"); // 家电昵称
		String classId = request.getParameter("classId"); // 家电类别Id
		String roomNumber = request.getParameter("roomNumber"); // 房间编号
		String groupNumber = request.getParameter("groupNumber"); // 家庭组编号
		String userId = request.getParameter("userId"); // 用户Id
		String prop = request.getParameter("prop"); // 通讯参数
		String stub = request.getParameter("stub"); // 智能索引
		String type = request.getParameter("type"); // 家电类型
		String status = request.getParameter("status"); //

		Users users = null;
		Household households = new Household();
		Room room = null;
		Familygroup family = null;
		FamilyUser fu1 = null;
		JSONObject json = new JSONObject();
		JSONObject jsonObject  = null;
		if (XATools.isNull(userId)) {
			code = UserCode.emptyId;
			message = "用户ID不能为空";
			success = false;
		} else {
			if (!XATools.isInteger(userId)) {
				code = UserCode.formatisInconsistent;
				success = false;
				message = "用户ID格式不符 ";
			}
		}
		if (XATools.isNull(groupNumber)) {
			code = FamilyCode.emptyId;
			success = false;
			message = "家庭组编号不能为空!";
		} else {
			if (!XATools.isInteger(groupNumber)) {
				code = FamilyCode.formatisInconsistent;
				success = false;
				message = "家庭组编号格式不符 ";
			}
		}

		if (XATools.isNull(roomNumber)) {
			code = RoomCode.emptyId;
			success = false;
			message = "房间编号不能为空!";
		}

		if (XATools.isNull(classId)) {
			code = HouseholdCode.emptyClassId;
			success = false;
			message = "家电类别Id不能为空!";
		} else {
			if ("2".equals(classId)) { // 如果是智能就检查呢称
				if (XATools.isNull(eaName)) {
					code = HouseholdCode.emptyName;
					success = false;
					message = "家电呢称不能为空!";
				}
				if (!XATools.isNull(prop)) {
					if (XATools.isInteger(prop)) {
						households.setProp(Long.parseLong(prop));
					} else {
						code = HouseholdCode.propFormatisfalse;
						success = false;
						message = "通讯参数格式不符!";
					}
				}else{
					code = HouseholdCode.emptyProp;
					success = false;
					message = "通讯参数不能为空!";
				}
				//households.setPort(Integer.parseInt(port));
				//households.setStatus(Integer.parseInt(status));
			}
		}

		if (!XATools.isNull(type)) {
			households.setType(type);
		}

		if (!XATools.isNull(brand)) {
			households.setBrand(brand);
		}
		if (!XATools.isNull(model)) {
			households.setModel(model);
		}

		if (!XATools.isNull(stub)) {
			if (XATools.isInteger(stub)) {
				households.setStub(Integer.parseInt(stub));
			} else {
				code = HouseholdCode.stubformatisfalse;
				success = false;
				message = "智能索引格式不符!";
			}
		}

		if (success) {
			try {
				users = usersService.selectUsersByid(Integer.parseInt(userId));
				if (users != null) {
					family = familyDao.getFamilygroupByNumber(Integer
							.parseInt(groupNumber));
					if (family != null) {
						fu1 = fauserService.selectFamilyUserByGNU(users, family);
						if (fu1 != null) {
							if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
								//普通电器
								StringBuilder eaNumber = null;
								if(classId.equals("1")){
									eaNumber = new StringBuilder(roomNumber);
									eaNumber.append("#");
									eaNumber.append(type);
									eaNumber.append(brand);
									eaNumber.append(stub);
								}else{
									eaNumber = new StringBuilder(roomNumber);
									eaNumber.append("#");
									eaNumber.append(prop);
								}
								List<Household> kk = householdDao
										.getRoomByRoomNumber1(family, eaNumber.toString() );
								if (XATools.isNull(kk)) { // 判断该家庭组是否已经添加了该电器
									room = roomDao.getRoomByGroupId(family,
											roomNumber);
									if (room != null) {
										households.setFamilygroup(family);
										households.setRoom(room);
										households.setClassId(Integer
												.parseInt(classId));
										households.setEaName(eaName);
										households.setEaNumber(eaNumber.toString());
										households.setCreateTime(XATools
												.getTNowTime());
										//推送
										List<Xiaoi> allOnlineXiaois = xiaoiDao.selectXiaoiByFa(family);
										com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
										HashMap hashMap = null;
										boolean flag = false;
										boolean pushState = false;
										for (Xiaoi xiaoi : allOnlineXiaois) {
											//判断当前小艾是否在线
											if(xiaoi.getState()==1){
												//在线，推送
												hashMap = new HashMap();
												hashMap.put("households", households);
												
												//com.alibaba.fastjson.JSONObject j_ = new com.alibaba.fastjson.JSONObject();
												//Object json3 = j_.toJSON(households);
												Map<String, Object> beanToMap = BeanUtil.beanToMap(households);
												HashMap sMap = (HashMap) beanToMap;
												
												//JSONObject j_ = new JSONObject();
												//j_.toJavaObject(clazz)
												//排除
												sMap.remove("xiaoiLogs");
												sMap.remove("familygroup");
												sMap.remove("room");
												
												sMap.put("roomNumber", room.getRoomNumber());
												json2.put("key", "appinserthouseHold");
												json2.put("code", 0);
												json2.put("xiaoNumber", xiaoi.getXiaoNumber());
												pushState = PushMessage_Room.push2Xiao(json2, sMap);
												if(pushState){
													flag = true;
												}
											}
										}
										if(flag){
											//当前家庭组有在线小艾推送成功,执行添加电器
											//只有智能电器才要生成逻辑地址
											if(households.getClassId()==2){
												buildPort(households, family);
											}
											
											success = houseHoldService
													.insertHousehold(households);
											if (success==false) {
												code = HouseholdCode.insertFalse;
												success = false;
												message = "新增家电失败!";
											} else {
												//新增成功，不做处理
												jsonObject = new JSONObject();
												jsonObject.put("hid", households.getHid());
												jsonObject.put("roomNumber", households.getRoom().getRoomNumber());
												jsonObject.put("groupNumber", households.getFamilygroup().getGroupNumber());
												jsonObject.put("classId", households.getClassId());
												jsonObject.put("brand", households.getBrand());
												jsonObject.put("model", households.getModel());
												jsonObject.put("createTime", households.getCreateTime());
												jsonObject.put("eaName", households.getEaName());
												jsonObject.put("eaNumber", households.getEaNumber());
												jsonObject.put("type", households.getType());
												jsonObject.put("prop", households.getProp());
												jsonObject.put("stub", households.getStub());
												jsonObject.put("port", households.getPort());
												
												//定死暂时
												jsonObject.put("status",0);
												
											}
										}else{
											//当前家庭组没有任何一台小艾在线，推送失败，拒绝添加房间！
											code = XiaoiCode.noExistOnlinBean;
											message = "没有在线小艾!";
										}
									} else {
										code = RoomCode.noExistBean;
										success = false;
										message = "没有该房间!";
									}
								} else {
									code = HouseholdCode.conflictBean;
									success = false;
									message = "该电器已存在!";
								}
							} else {
								code = FamilyCode.privilegeMaster;
								success = false;
								message = "您不是群主，没有权限操作!";
							}
						} else {
							code = FamilyCode.noExistUser;
							success = false;
							message = "该家庭组中不存在该用户";
						}
					} else {
						code = FamilyCode.noExistBean;
						success = false;
						message = "没有该家庭组";
					}
				} else {
					code = UserCode.noExistBean;
					success = false;
					message = "没有该用户!";
				}
			} catch (Exception e) {
				code = HouseholdCode.insertFalse;
				success = false;
				message = "新增家电失败!";
				e.printStackTrace();
			}
		}
		json.put("code", code);
		json.put("message", message);
		json.put("result", jsonObject); // 家电信息
		logger.info("添加家电的出参:" + json);
		out.print(json.toString());
		return null;
	}

	/**
	 * 生成逻辑地址
	 * @param households
	 * @param family
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void buildPort(Household households, Familygroup family) {
		//生成逻辑地址（port）
		//1.先遍历当前家庭组下的所有家电
		List<Household> hoList = householdDao.selectHouseholdBygroupId(family.getGroupId());
		Set set = new HashSet();
		Set tempSet = new HashSet();
		Integer poInteger = null;
		for (Household household : hoList) {
			set.add(household.getPort());
		}
		for (int i = 31; i < 255; i++) {
			tempSet.add(i);
		}
		//排除
		tempSet.removeAll(set);
		//取第一个数
		Integer port = null;
		for (Iterator iterator = tempSet.iterator(); iterator.hasNext();) {
			port = (Integer) iterator.next();
			break;
		}
		//设置port
		households.setPort(port);
	}

	/**
	 * 在用户登入的前提下查询家电信息
	 * 
	 * @param showPage
	 *            页面显示最大记录数
	 * @param pageNow
	 *            当前页面显示页数
	 * @param groupId
	 *            家庭组id
	 * @return houseList(家电对象集合),pageNow(当前页面显示页数),totalPage(总记录数)
	 * @throws IOException
	 */
	public String findAll() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String showPage = request.getParameter("showPage");
		String pages = request.getParameter("pageNow");// 当前页
		String groupId = request.getParameter("groupId");
		Household houseHolds = new Household();
		Familygroup family = null;
		if (groupId != null && !groupId.equals("")) {
			family = familyDao.getFamilygroupByid(Integer.parseInt(groupId));
		}
		if (family != null) {
			houseHolds.setFamilygroup(family);
		}
		int total = houseHoldService.getCountHouseHold(houseHolds);// 得到总记录数
		JSONArray jsonList = new JSONArray();
		if (total != 0) {try {
			// 如果该家庭组下有家电信息
				Page page = new Page();
				page.setTotal(total);
				if (showPage != null && !showPage.equals("")) {
					page.setShowPage(Integer.parseInt(showPage));
				}
				if (pages != null && !pages.equals("")) {
					page.setPageNow(Integer.parseInt(pages));
				}
				int offset = page.getOfferset();// 得到开始记录数
				int pageNow = page.getpageNow();// 得到当前页数
				int totalPage = page.gettotalPage();// 得到总页数
				List<Household> houseList = houseHoldService.findHouseHold(total,
						offset, houseHolds);

				if (!XATools.isNull(houseList)) {
					// 将查询出的list集合转成json数据
					for (Household houseHold : houseList) {
						JSONObject json = new JSONObject();
						json.put("hid", houseHold.getHid());
						json.put("eaName", houseHold.getEaName());
						json.put("brand", houseHold.getBrand());
						json.put("model", houseHold.getModel());
						json.put("createTime", houseHold.getCreateTime());
						json.put("classId", houseHold.getClassId());
						json.put("familygroup", houseHold.getFamilygroup());
						json.put("pageNow", pageNow);
						json.put("totalPage", totalPage);
						jsonList.add(json);
					}
				}

				out.print(jsonList.toString());
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		} else {
			out.print(jsonList.toString());
			return null;
		}
		return null;

	}

	/**
	 * 删除家电信息
	 * 
	 * @param userId
	 *            用户Id
	 * @param hidNumber
	 *            家电编号
	 * @param groupbbh
	 *            家庭组版本号
	 * @return success=true(删除成功)或者success=false(删除失败)
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String delete() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("删除家电的入参");
		String eaNumber = request.getParameter("eaNumber"); // 家电编号
		String groupNumber = request.getParameter("groupNumber"); // 家庭组编号
		String userId = request.getParameter("userId");
		Users users = null;
		FamilyUser fu1 = null;
		Familygroup family = null;
		JSONObject json = new JSONObject();
		if (XATools.isNull(userId)) {
			code = UserCode.emptyId;
			success = false;
			message = "用户id不能为空";
		}

		if (XATools.isNull(eaNumber)) {
			code = HouseholdCode.emptyId;
			success = false;
			message = "家电编号不能为空";
		}
		if (XATools.isNull(groupNumber)) {
			code = FamilyCode.emptyId;
			success = false;
			message = "家庭组编号不能为空!";
		}
		if(!XATools.isNull(userId)){
			if (!XATools.isInteger(userId)) {
				code = UserCode.formatisInconsistent;
				success = false;
				message = "用户ID格式不符 ";
			}
		}else{
			code = UserCode.emptyId;
			success = false;
			message = "用户ID不能为空 ";
		}
		if (!XATools.isInteger(groupNumber)) {
			code = FamilyCode.formatisInconsistent;
			success = false;
			message = "家庭组编号格式不符 ";
		}
		if (success) {
			try {
				users = usersService.selectUsersByid(Integer.parseInt(userId));
				if (users != null) {
					family = familyDao.getFamilygroupByNumber(Integer
							.parseInt(groupNumber));
					if (family != null) {
						fu1 = fauserService.selectFamilyUserByGNU(users, family);
						if (fu1 != null) {
							if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
								Household houseHold = householdDao
										.getRoomByRoomNumber(eaNumber);
								if (houseHold != null) {
									//推送
									List<Xiaoi> allOnlineXiaois = xiaoiDao.selectXiaoiByFa(family);
									com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
									boolean pushState = false;
									boolean flag = false;
									HashMap hashMap = null;
									for (Xiaoi xiaoi : allOnlineXiaois) {
										//判断当前小艾是否在线
										if(xiaoi.getState()==1){
											//在线，推送
											hashMap = new HashMap();
											hashMap.put("eaNumber", houseHold.getEaNumber());
											json2.put("key", "appdeletehouseHold");
											json2.put("code", 0);
											json2.put("xiaoNumber", xiaoi.getXiaoNumber());
											pushState = PushMessage_Room.push2Xiao(json2, hashMap);
											if(pushState){
												flag = true;
											}
										}
									}
									if(flag){
										//当前家庭组有在线小艾推送成功,执行删除电器
										success = householdDao
												.deleteHousehold(houseHold);
										if (success == false) {
											code = HouseholdCode.deleteFalse;
											success = false;
											message = "删除家电失败!";
										}
									}else{
										//当前家庭组没有任何一台小艾在线，推送失败，拒绝添加房间！
										code = XiaoiCode.noExistOnlinBean;
										message = "没有在线小艾!";
									}
								} else {
									code = HouseholdCode.noExistBean;
									success = false;
									message = "没有该家电!";
								}
							} else {
								code = FamilyCode.privilegeMaster;
								success = false;
								message = "您不是群主，没有权限修改!";
							}
						} else {
							code = FamilyCode.noExistUser;
							success = false;
							message = "该家庭组中不存在该用户";
						}
					} else {
						code = FamilyCode.noExistBean;
						success = false;
						message = "没有该家庭组!";
					}
				} else {
					code = UserCode.noExistBean;
					success = false;
					message = "没有该用户!";
				}
			} catch (Exception e) {
				code = HouseholdCode.deleteFalse;
				success = false;
				message = "删除家电失败!";
				e.printStackTrace();
			}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("删除家电的出参:" + json);
		out.print(json);
		return null;

	}

	/**
	 * 根据家电编号查询
	 * 
	 * @param hidNumber
	 *            家电编号
	 * @return houseHold 家电对象
	 * @throws IOException
	 */
	public String selectByid() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("查询家电的入参");
		String eaNumber = request.getParameter("eaNumber"); // 家电编号
		String groupNumber = request.getParameter("groupNumber"); // 家庭组编号
		Household houseHold = null;
		Familygroup family = null;
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray array = new JSONArray();
		if (XATools.isNull(eaNumber)) {
			code = HouseholdCode.emptyId;
			success = false;
			message = "家电编号不能为空!";
		}
		if (XATools.isNull(groupNumber)) {
			code = FamilyCode.emptyId;
			success = false;
			message = "家庭组编号不能为空!";
		}else{
			if (!XATools.isInteger(groupNumber)) {
				code = FamilyCode.formatisInconsistent;
				success = false;
				message = "家庭组编号格式不符 ";
			}
		}

		if (success) {
			try {
				family = familyDao.getFamilygroupByNumber(Integer
						.parseInt(groupNumber));
				if (family != null) {
					json.put("versionNumber", family.getVersionNumber()); // 版本号
					List<Household> lh = householdDao.getRoomByRoomNumber1(family,
							eaNumber);
					if (!XATools.isNull(lh)) {
						houseHold = lh.get(0);
						json2.put("eaNumber", houseHold.getEaNumber()); // 家电编号
						json2.put("eaName", houseHold.getEaName());
						json2.put("roomId", houseHold.getRoom().getId());
						json2.put("roomNumber", houseHold.getRoom().getRoomNumber());
						json2.put("groupNumber", groupNumber);
						json2.put("classId", houseHold.getClassId()); // 家电类别
						json2.put("brand", houseHold.getBrand());
						json2.put("model", houseHold.getModel());
						//json2.put("type", houseHold.getType()); // 家电类型
						json2.put("prop", houseHold.getProp()); // 通讯参数
						json2.put("stub", houseHold.getStub()); // 智能索引
						json2.put("status", houseHold.getStatus()); //
						json2.put("port", houseHold.getPort()); //
						array.add(json2);
					} else {
						code = HouseholdCode.noExistBean;
						success = false;
						message = "没有该家电!";
					}
				} else {
					code = FamilyCode.noExistBean;
					success = false;
					message = "没有该家庭组";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json.put("code", code);
		json.put("message", message);
		json.put("Result", array); // 家电信息
		logger.info("查询家电的出参:" + json);
		out.print(json.toString());
		return null;
	}

	/**
	 * 修改家电信息
	 * 
	 * @param userId
	 *            用户Id
	 * @param hhName
	 *            家电名字
	 * @param houseType
	 *            家电类型
	 * @param brand
	 *            品牌
	 * @param model
	 *            型号
	 * @param hid
	 *            家电id
	 * @param roomId
	 *            房间id
	 * @param groupId
	 *            家庭组id
	 * @return success=true(修改成功)或者success=false(修改失败)
	 * @throws Exception
	 */
	public String update() throws Exception {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String eaName = request.getParameter("eaName");
		String classId = request.getParameter("classId");
		String brand = request.getParameter("brand");
		String model = request.getParameter("model");
		String hid = request.getParameter("hid");
		String roomId = request.getParameter("roomId");
		String groupId = request.getParameter("groupId");
		String userId = request.getParameter("userId");
		Household houseHold = null;
		Users users = null;
		JSONObject json = new JSONObject();
		if (!XATools.isNull(userId)) {
			users = usersService.selectUsersByid(Integer.parseInt(userId));
		}
		if (users != null) {
			try {
				// 根据家电id查询家电实体对象
				if (hid != null && !hid.equals("")) {
					int houseId = Integer.parseInt(hid);
					houseHold = houseHoldService.selectHouseholdByid(houseId);
				}
				// 在家电名字不为空
				if (!XATools.isNull(eaName)) {
					houseHold.setEaName(eaName);
				}
				// 在家电所在房间id不为空
				if (roomId != null && !roomId.equals("")) {
					Room room = roomService.getRoomByid(Integer.parseInt(roomId));
					houseHold.setRoom(room);
				}
				if (classId != null && !classId.equals("")) {
					// 根据家电类型名字得到家电实体类
					houseHold.setClassId(Integer.parseInt(classId));
				}
				// 家电品牌不为空
				if (brand != null && !brand.equals("")) {
					houseHold.setBrand(brand);
				}
				// 家电型号不为空
				if (model != null && !model.equals("")) {
					houseHold.setModel(model);
				}
				Familygroup family = null;
				if (groupId != null && !groupId.equals("")) {
					family = familyDao
							.getFamilygroupByid(Integer.parseInt(groupId));
					houseHold.setFamilygroup(family);
				}
				// 执行修改持久化操作
				success = houseHoldService.updateHouseholdByid(houseHold);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		out.print(json.toString());
		return null;
	}

	/**
	 * 控制红外电器（户外）
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String controllerHouseHold() throws IOException {
		PrintWriter out = MyRequest.getResponse();
		HttpServletRequest req = MyRequest.getRequest();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = MyRequest.getParameterNames();
		XiaoiResult xr = new XiaoiResult();
		logger.info("家电控制入参：" + jsonObject);
		// 获取参数
		String groupId = req.getParameter("groupId");
		String eaNumber = req.getParameter("eaNumber");
		String type = req.getParameter("type");
		String stub = req.getParameter("stub");
		String requestUri = req.getParameter("requestUri");
		// String memory = req.getParameter("memory");
		String keys = req.getParameter("keys");
		String classId =req.getParameter("classId");
		//智能电器参数
		String name =req.getParameter("name");
		String command =req.getParameter("command");
		String value =req.getParameter("value");
		
		//共同参数判断
		if (XATools.isNull(groupId)) {
			xr = XiaoiResult.build("家庭组编号不能为空！", FamilyCode.emptyId);
			if (!XATools.isInteger(groupId)) {
				xr = XiaoiResult.build("家庭组编号格式不符！",
						FamilyCode.formatisInconsistent);
			}
		}
		
		//判断是智能电器还是红外电器
		if(XATools.isNull(classId)){
			xr = XiaoiResult.build("电器类别不能为空！", HouseholdCode.emptyClassId);
		}else{
			if(classId.equals("1")){
				//红外电器
				if (XATools.isNull(requestUri)) {
					xr = XiaoiResult.build("requestUri不能为空！",
							HouseholdCode.emptyRequestUri);
				}
				if (XATools.isNull(keys)) {
					xr = XiaoiResult.build("keys不能为空！", HouseholdCode.emptyKeys);
				}
			}else if (classId.equals("2")) {
				//智能电器
				if (XATools.isNull(name)) {
					xr = XiaoiResult.build("电器昵称不能为空！", HouseholdCode.emptyName);
				}
				if (XATools.isNull(command)) {
					xr = XiaoiResult.build("功能键值不能为空！", HouseholdCode.emptyCommand);
				}
				if (XATools.isNull(value)) {
					xr = XiaoiResult.build("执行参数不能为空！", HouseholdCode.emptyValue);
				}
			}
		}
		HashMap data = null;
		if (xr.isSuccess()) {
			try {
				Familygroup familygroup = familyService.getFamilygroupByNumber(Integer
						.parseInt(groupId));
				if (familygroup != null) {
					// 取得小艾
					Xiaoi xiaoi = xiaoiService.selectXiaoiByFa(familygroup);
					if (xiaoi != null) {
						/*Household household = householdDao
								.getRoomByRoomNumber(eaNumber);*/
						if(classId.equals("1")){
							//红外电器推送
							data = new HashMap();
							if (!XATools.isNull(eaNumber)) {
								data.put("eaNumber", eaNumber);
							}
							if (!XATools.isNull(type)) {
								data.put("type", type);
							}
							if (!XATools.isNull(stub)) {
								if (XATools.isInteger(stub)) {
									data.put("stub", stub);
								}
							}

							data.put("keys", keys);
							data.put("requestUri", requestUri);
							json.put("key", "appcontrollerhouseHold");
							json.put("code", xr.getCode());
							json.put("xiaoNumber", xiaoi.getXiaoNumber());
							PushMessage_houseHoldController.push2Xiao(json, data);
						}else if(classId.equals("2")){
							//智能电器推送
							data = new HashMap();
							data.put("eaNumber", eaNumber);
							data.put("name", name);
							data.put("command", command);
							data.put("value", value);
							data.put("requestUri", requestUri);
							
							json.put("key", "appcontrollerhouseHold");
							json.put("code", xr.getCode());
							json.put("xiaoNumber", xiaoi.getXiaoNumber());
							PushMessage_houseHoldController.push2Xiao(json, data);
						}
					} else {
						xr = XiaoiResult
								.build("没有在线小艾", XiaoiCode.noExistOnlinBean);
					}
				} else {
					xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		out.print(json);
		logger.info("家电控制出参:" + json+"data:"+data);
		return null;

	}
	
	/**
	 * 控制智能电器（户外）
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public String controllerSmartHouseHold() throws IOException {
		PrintWriter out = MyRequest.getResponse();
		HttpServletRequest req = MyRequest.getRequest();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = MyRequest.getParameterNames();
		XiaoiResult xr = new XiaoiResult();
		logger.info("智能家电控制入参：" + jsonObject);
		// 获取参数
		String groupId = req.getParameter("groupId");
		String eaNumber = req.getParameter("eaNumber");
		String type = req.getParameter("type");
		String stub = req.getParameter("stub");
		String requestUri = req.getParameter("requestUri");
		// String memory = req.getParameter("memory");
		String keys = req.getParameter("keys");
		if (XATools.isNull(groupId)) {
			xr = XiaoiResult.build("家庭组编号不能为空！", FamilyCode.emptyId);
		}
		if (!XATools.isInteger(groupId)) {
			xr = XiaoiResult.build("家庭组编号格式不符！",
					FamilyCode.formatisInconsistent);
		}
		if (XATools.isNull(requestUri)) {
			xr = XiaoiResult.build("requestUri不能为空！",
					HouseholdCode.emptyRequestUri);
		}
		if (XATools.isNull(keys)) {
			xr = XiaoiResult.build("keys不能为空！", HouseholdCode.emptyKeys);
		}

		if (xr.isSuccess()) {
			try {
				Familygroup familygroup = familyService.getFamilygroupByNumber(Integer
						.parseInt(groupId));
				if (familygroup != null) {
					// 取得小艾
					Xiaoi xiaoi = xiaoiService.selectXiaoiByFa(familygroup);
					if (xiaoi != null) {
						Household household = householdDao
								.getRoomByRoomNumber(eaNumber);
						HashMap data = new HashMap();
						if (!XATools.isNull(eaNumber)) {
							data.put("eaNumber", eaNumber);
						}
						if (!XATools.isNull(type)) {
							data.put("type", type);
						}
						if (!XATools.isNull(stub)) {
							if (XATools.isInteger(stub)) {
								data.put("stub", stub);
							}
						}

						data.put("keys", keys);
						data.put("requestUri", requestUri);
						json.put("keys", "appcontrollerhouseHold");
						json.put("code", xr.getCode());
						json.put("xiaoNumber", xiaoi.getXiaoNumber());
						PushMessage_houseHoldController.push2Xiao(json, data);

					} else {
						xr = XiaoiResult
								.build("没有在线小艾", XiaoiCode.noExistOnlinBean);
					}
				} else {
					xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		out.print(json);
		logger.info("家电控制出参:" + json);
		return null;

	}
}
