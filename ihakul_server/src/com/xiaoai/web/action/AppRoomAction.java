package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.mina.service.push.PushMessage_Room;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IHouseholdService;
import com.xiaoai.service.IRoomService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
import com.xiaoleilu.hutool.util.BeanUtil;

@Controller("appRoomAction")
@Scope("prototype")
public class AppRoomAction extends XiaoaiMessage {
	@Resource(name = "roomService")
	private IRoomService roomService;
	@Resource(name = "familyService")
	private IFamilygroupService familyService;
	@Resource(name = "usersService")
	private IUsersService usersService;

	@Resource(name = "xiaoiService")
	private IXiaoiService xiaoiService;
	
	@Resource(name = "houseHoldService")
	private IHouseholdService houseHoldService;
	
	@Resource(name = "fauserService")
	private IFamilyUserService fauserService;

	private boolean success; // 成功、失败标记
	private String message; // 信息
	private int code; // 标记
	private static Logger logger = Logger.getLogger(AppRoomAction.class);

	/**
	 * 添加房间信息
	 * 
	 * @param groupNumber
	 *            家庭组编号
	 * @param userId
	 *            用户id
	 * @param roomName
	 *            房间名字
	 * @param roomNickName
	 *            房间昵称
	 * @param floor
	 *            房间楼层(默认 0)
	 * @param parentId
	 *            父节点标识
	 * @param roomNumber
	 *            房间编号
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes","unused" })
	public String insert() throws IOException {
		success = true;
		message = null;
		code = OK;
		JSONObject json = new JSONObject();
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("添加房间的入参");
		String groupNumber = request.getParameter("groupNumber");
		String userId = request.getParameter("userId");
		String roomName = request.getParameter("roomName");
		String roomNickName = request.getParameter("roomNickName");
		String floor = request.getParameter("floor");
		String parentId = request.getParameter("parentId");
		//String roomNumber = request.getParameter("roomNumber");
		String xiaoNumber = request.getParameter("xiaoNumber");
		//String versionNumber = request.getParameter("versionNumber"); // 档案版本号
		String robot = request.getParameter("robot"); // 终端绑定
		Room room = new Room();
		Users users = null;
		FamilyUser fu1 = null;
		JSONObject jsonObject = null;
		if (XATools.isNull(userId)) {
			code = UserCode.emptyId;
			message = "用户id不能为空";
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
		if (XATools.isNull(roomName)) {
			code = RoomCode.emptyName;
			success = false;
			message = "房间名称不能为空!";
		}
	
		if (XATools.isNull(xiaoNumber)) {
			code = XiaoiCode.emptyId;
			success = false;
			message = "小艾编号不能为空!";
		}

		if (!XATools.isNull(roomNickName)) {
			room.setRoomNickName(roomNickName);
		}

		if (!XATools.isNull(floor)) {
			if (XATools.isInteger(floor)) {
				room.setFloor(Integer.parseInt(floor));
			} else {
				code = RoomCode.floorFormatIsfalse;
				success = false;
				message = "房间楼层格式不符 !";
			}
		}
		if (!XATools.isNull(parentId)) {
			room.setParentId(parentId);
		}

		if (!XATools.isNull(robot)) {
			room.setRobot(robot);
		}

		if (success) {
			try {
				users = usersService.selectUsersByid(Integer.parseInt(userId));
				if (users != null) {
					Familygroup family = familyService
							.getFamilygroupByNumber(Integer.parseInt(groupNumber));
					if (family != null) {

						fu1 = fauserService.selectFamilyUserByGNU(users, family);
						if (fu1 != null) {
							if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
								
								Xiaoi xil =xiaoiService.selectXiaoiByNumber(xiaoNumber);
								
								if (xil != null) {
									//拼接roomNumber
									StringBuffer roomNumber = new StringBuffer("0-0-");
									roomNumber.append(roomName);
				
									Room kk = roomService.getRoomByGroupId(family,roomNumber.toString());
									if (kk == null) { // 判断该家庭组是否已经添加了该房间
										room.setRoomNumber(roomNumber.toString());
										room.setFamilygroup(family);
										room.setRoomName(roomName);
										room.setCreateTime(XATools.getTNowTime());
										room.setCreator(xiaoNumber);
										
										List<Xiaoi> allOnlineXiaois =xiaoiService.selectXiaoiByFaAll(family);
										com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
										HashMap hashMap = null;
										boolean pushState = false;
										boolean flag = false;
										for (Xiaoi xiaoi : allOnlineXiaois) {
											//判断当前小艾是否在线
											if(xiaoi.getState()==1){
												//在线，推送
												hashMap = new HashMap();
												
												Map<String, Object> beanToMap = BeanUtil.beanToMap(room);
												HashMap sMap = (HashMap) beanToMap;
												//剔除
												sMap.remove("households");
												sMap.remove("familygroup");
												
												
												//hashMap.put("room", json3);
												json2.put("key", "appaddRoom");
												json2.put("code", 0);
												json2.put("xiaoNumber", xil.getXiaoNumber());
												pushState = PushMessage_Room.push2Xiao(json2, sMap);
												if(pushState){
													flag = true;
												}
											}
										}
										if(flag){
											//当前家庭组有在线小艾推送成功,执行添加房间
											success = roomService.insertRoom(room);
											if (success == false) {
												code = RoomCode.insertFalse;
												message = "创建房间失败!";
											}
											//
											jsonObject = new JSONObject();
											jsonObject.put("groupNumber", room.getFamilygroup().getGroupNumber());
											jsonObject.put("id", room.getId());
											jsonObject.put("roomName", room.getRoomName());
											jsonObject.put("createTime", room.getCreateTime());
											jsonObject.put("roomNickName", room.getRoomNickName());
											jsonObject.put("floor", room.getFloor());
											jsonObject.put("parentId", room.getParentId());
											jsonObject.put("roomNumber", room.getRoomNumber());
											jsonObject.put("robot", room.getRobot());
											jsonObject.put("creator", room.getCreator());
										}else{
											//当前家庭组没有任何一台小艾在线，推送失败，拒绝添加房间！
											code = XiaoiCode.noExistOnlinBean;
											message = "没有在线小艾!";
										}
									} else {
										code = RoomCode.conflictBean;
										success = false;
										message = "该房间已存在!";
									}
								} else {
									code = XiaoiCode.noExistBean;
									success = false;
									message = "没有该小艾!";
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
						message = "没有该家庭组!";
					}
				} else {
					code = UserCode.noExistBean;
					success = false;
					message = "没有该用户!";
				}
			} catch (Exception e) {
				code = RoomCode.insertFalse;
				message = "创建房间失败!";
				e.printStackTrace();
			}
		}

		json.put("code", code);
		json.put("message", message);
		json.put("result", jsonObject); // 房间信息
		logger.info("添加房间出参:" + json);
		out.print(json);
		return null;

	}

	/**
	 * 修改房间信息
	 * 
	 * @param roomNumber
	 *            房间编号
	 * @param userId
	 *            用户Id
	 * @param roomName
	 *            房间名字
	 * @return success=true(修改成功)或者success=false(修改失败)
	 * @throws IOException
	 */
	@SuppressWarnings({"rawtypes", "static-access", "unused" })
	public String update() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("修改房间的入参");
		String roomNumber = request.getParameter("roomNumber");
		String roomName = request.getParameter("roomName");
		String userId = request.getParameter("userId");
		Users users = null;
		Room room = null;
		JSONObject json = new JSONObject();
		if (XATools.isNull(userId)) {
			code = UserCode.emptyId;
			message = "用户id不能为空";
			success = false;
		}
		if (XATools.isNull(roomName)) {
			code = RoomCode.emptyName;
			success = false;
			message = "房间名称不能为空!";
		}
		if (XATools.isNull(roomNumber)) {
			code = RoomCode.emptyId;
			success = false;
			message = "房间编号不能为空!";
		}

		if (success) {
			try {
				// 根据用户id查到用户信息
				users = usersService.selectUsersByid(Integer.parseInt(userId));
				if (users != null) {
					room = roomService.getRoomByid(Integer.parseInt(roomNumber));
					if (room != null) {
						room.setRoomName(roomName);
						success = roomService.updateRoom(room);
						//推送
						List<Xiaoi> allOnlineXiaois = xiaoiService.selectXiaoiByFaAll(room.getFamilygroup());
						com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
						HashMap hashMap = null;
						for (Xiaoi xiaoi : allOnlineXiaois) {
							//判断当前小艾是否在线
							if(xiaoi.getState()==1){
								//在线，推送
								hashMap = new HashMap();
								com.alibaba.fastjson.JSONObject j_ = new com.alibaba.fastjson.JSONObject();
								Object json3 = j_.toJSON(room);
								
								//hashMap.put("room", json3);
								Map<String, Object> beanToMap = BeanUtil.beanToMap(room);
								HashMap sMap = (HashMap) beanToMap;
								//剔除
								sMap.remove("households");
								sMap.remove("familygroup");
								
								json2.put("key", "appupdateRoom");
								json2.put("code", 0);
								json2.put("xiaoNumber", xiaoi.getXiaoNumber());
								PushMessage_Room.push2Xiao(json2, hashMap);
							}
						}
						
						if (success == false) {
							code = RoomCode.updateFalse;
							success = false;
							message = "修改房间失败!";
						}
					} else {
						code = RoomCode.noExistBean;
						success = false;
						message = "没有该房间!";
					}
				} else {
					code = FamilyCode.privilegeMaster;
					success = false;
					message = "您不是群主，没有权限修改!";
				}
			} catch (Exception e) {
				code = RoomCode.updateFalse;
				success = false;
				message = "修改房间失败!";
				e.printStackTrace();
			}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("修改房间的出参:" + json);
		out.print(json.toString());
		return null;
	}

	/**
	 * 删除房间信息
	 * 
	 * @param roomNumber
	 *            房间编号
	 * @param userId
	 *            用户Id
	 * @return success=true(删除成功)或者success=false(删除失败)
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String delete() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("删除房间的入参");
		Users users = null;
		Room room = null;
		FamilyUser fu1 = null;
		JSONObject json = new JSONObject();
		String userId = request.getParameter("userId");
		String roomNumber = request.getParameter("roomNumber"); // 房间编号
		String groupNumber = request.getParameter("groupNumber");// 家庭组编号
		if (XATools.isNull(userId)) {
			code = UserCode.emptyId;
			message = "用户id不能为空";
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
		if (success) {
			try {
				// 根据用户id查到用户信息
				users = usersService.selectUsersByid(Integer.parseInt(userId));
				if (users != null) {
					Familygroup family = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
					if (family != null) { // 通过家庭组和房间编号查询房间信息
						fu1 = fauserService.selectFamilyUserByGNU(users, family);
						if (fu1 != null) {
							if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
								room = roomService.getRoomByGroupId(family, roomNumber);
								if (room != null) { // 判断该家庭组是否已经添加了该房间
									//推送
									List<Xiaoi> allOnlineXiaois =xiaoiService.selectXiaoiByFaAll(family);
									com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
									
									boolean pushState = false;
									boolean flag = false;
									HashMap hashMap = null;
									hashMap = new HashMap();
									hashMap.put("roomNumber", room.getRoomNumber());
									for (Xiaoi xiaoi : allOnlineXiaois) {
										//判断当前小艾是否在线
										if(xiaoi.getState()==1){
											//在线，推送
											json2.put("key", "appdeleteRoom");
											json2.put("code", 0);
											json2.put("xiaoNumber", xiaoi.getXiaoNumber());
											pushState = PushMessage_Room.push2Xiao(json2, hashMap);
											if(pushState){
												flag = true;
											}
										}
									}
									if(flag){
										//当前家庭组有在线小艾推送成功,执行删除房间
										success = roomService.deleteRoom(room);
										if (success == false) {
											code = RoomCode.deleteFalse;
											message = "删除房间失败!";
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
				code = RoomCode.deleteFalse;
				message = "删除房间失败!";
				e.printStackTrace();
			}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("删除房间出参:" + json);
		out.print(json.toString());
		return null;
	}

	/**
	 * 根据房间编号查询房间信息
	 * 
	 * @param roomNumber
	 *            房间编号
	 * @return room 房间对象
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public String getRoomByid() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("查询房间的入参");
		String roomNumber = request.getParameter("roomNumber");
		String groupNumber = request.getParameter("groupNumber");
		
		Room room = null;
		Familygroup family = null;
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray array = new JSONArray();
		if (XATools.isNull(roomNumber)) {
			code = RoomCode.emptyId;
			success = false;
			message = "房间编号不能为空!";
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

		if (success) {
			try {
				family = familyService.getFamilygroupByNumber(Integer
						.parseInt(groupNumber));
				json.put("versionNumber", family.getVersionNumber()); // 版本号
				if (family != null) {
					room = roomService.getRoomByGroupId(family, roomNumber);
					if (room != null) { // 判断该家庭组是否已经添加了该房间
						JSONArray array4 = new JSONArray();
						json2.put("roomName", room.getRoomName()); // 房间名称
						json2.put("roomNickName", room.getRoomNickName()); // 房间昵称
						json2.put("floor", room.getFloor()); // 房间楼层(默认 0)
						json2.put("parentId", room.getParentId()); // 父节点标识
						json2.put("roomNumber", room.getRoomNumber()); // 房间编号
						json2.put("robot", room.getRobot()); // 终端绑定的编号

						List<Household> householdList =houseHoldService.selectHouseholdByroomID(room.getId());
						
						if (!XATools.isNull(householdList)) {
							for (Household household : householdList) {
								JSONObject json4 = new JSONObject();
								json4.put("roomNumber", room.getRoomNumber()); // 房间编号
								json4.put("eaName", household.getEaName()); // 家电名称
								json4.put("classId", household.getClassId());// 家电类别
																				// 1
																				// 智能家电,2红外线家电
								json4.put("brand", household.getBrand()); // 品牌
								json4.put("model", household.getHid()); // 型号
								json4.put("eaNumber", household.getEaNumber()); // 家电编号
								json4.put("type", household.getType()); // 家电类型
								json4.put("prop", household.getProp()); // 通讯参数
								json4.put("stub", household.getStub()); // 智能索引
								json4.put("status", household.getStatus()); //
								json4.put("port", household.getPort()); //
								array4.add(json4);
								json2.put("household", array4);// 家电信息
							}
						}
						array.add(json2);
					} else {
						code = RoomCode.noExistBean;
						success = false;
						message = "没有该房间!";
					}
				} else {
					code = FamilyCode.noExistBean;
					success = false;
					message = "没有该家庭组!";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json.put("code", code);
		json.put("message", message);
		json.put("result", array); // 房间信息
		logger.info("查询房间的出参:" + json);
		out.print(json.toString());
		return null;
	}

	/**
	 * 切换默认房间
	 * 
	 * @param roomNumber
	 *            房间编号
	 * @param userId
	 *            用户Id
	 * @param xiaoNumber
	 *            终端编号
	 * @return success=true(修改成功)或者success=false(修改失败)
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String changeRoom() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("切换默认房间入参");
		String roomNumber = request.getParameter("roomNumber");
		String xiaoNumber = request.getParameter("xiaoNumber");
		String groupNumber = request.getParameter("groupNumber");
		String userId = request.getParameter("userId");
		Room room = null;
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			if (XATools.isNull(xiaoNumber)) {
				code = XiaoiCode.emptyId;
				success = false;
				message = "小艾编号不能为空!";
			}
			if (XATools.isNull(roomNumber)) {
				code = RoomCode.emptyId;
				success = false;
				message = "房间编号不能为空!";
			}
			if (XATools.isNull(userId)) {
				code = UserCode.emptyId;
				success = false;
				message = "用户id不能为空!";
			}else{
				if(!XATools.isInteger(userId)){
					code = UserCode.formatisInconsistent;
					success = false;
					message = "用户id格式不符!";
				}
			}
			if (XATools.isNull(groupNumber)) {
				code = FamilyCode.emptyId;
				success = false;
				message = "家庭组编号不能为空!";
			}else{
				if(!XATools.isInteger(groupNumber)){
					code = FamilyCode.formatisInconsistent;
					success = false;
					message = "家庭组编号格式不符!";
				}
			}
			
			if (success) {
				//权限判定
				//取到当前家庭组
				Familygroup family = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				if(family!=null){
					//查询user
					Users users = usersService.selectUsersByid(Integer.parseInt(userId));
					if(users!=null){
						FamilyUser fau = fauserService.selectFamilyUserByGNU(users, family);
						if(fau!=null){
							if(fau.getDna().equals(userId)){
								room = roomService.getRoomByRoomNumber(roomNumber,family);
								List<Room> rooms = roomService.getRoomByGroupId(family.getGroupId());
								String robot = null;

								if (room != null) {
									if (XATools.isNull(room.getRobot())) {
										//遍历该家庭组下的所有房间，看看是否一个终端绑定了两个房间，有的话解除前一个
										////推送
										//推送
										List<Xiaoi> allOnlineXiaois =xiaoiService.selectXiaoiByFaAll(family);
										com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
										
										boolean pushState = false;
										boolean flag = false;
										HashMap hashMap = null;
										hashMap = new HashMap();
										hashMap.put("roomNumber", room.getRoomNumber());
										hashMap.put("groupNumber", groupNumber);
										hashMap.put("xiaoNumber", xiaoNumber);
										for (Xiaoi xiaoi : allOnlineXiaois) {
											//判断当前小艾是否在线
											if(xiaoi.getState()==1){
												//在线，推送
												json2.put("key", "appchangeRoom");
												json2.put("code", 0);
												json2.put("xiaoNumber", xiaoi.getXiaoNumber());
												pushState = PushMessage_Room.push2Xiao(json2, hashMap);
												if(pushState){
													flag = true;
												}
											}
										}
										if(flag){
											//当前家庭组有在线小艾推送成功,执行切换默认房间
											//执行
											try {
												for (Room room_ : rooms) {
													robot = room_.getRobot();
													if(robot.equals(xiaoNumber)){
														//解除绑定
														room_.setRobot("");
														roomService.updateRoom(room_);
													}
												}
												room.setRobot(xiaoNumber);
												roomService.updateRoom(room);
											} catch (Exception e) {
												if (success == false) {
													code = RoomCode.changeFalse;
													message = "切换默认房间失败!";
												}
												e.printStackTrace();
											}
										}else{
											//当前家庭组没有任何一台小艾在线，推送失败，拒绝添加房间！
											code = XiaoiCode.noExistOnlinBean;
											message = "没有在线小艾，推送终端失败";
										}
										
									}else{
										JSONObject jsonObject = null;
										for (Room room2 : rooms) {
											jsonObject = new JSONObject();
											jsonObject.put("id", room2.getId());
											jsonObject.put("groupNumber", room2.getFamilygroup().getGroupId());
											jsonObject.put("roomName", room2.getRoomName());
											jsonObject.put("createTime", room2.getCreateTime());
											jsonObject.put("roomNickName", room2.getRoomNickName());
											jsonObject.put("floor", room2.getFloor());
											jsonObject.put("parentId", room2.getParentId());
											jsonObject.put("roomNumber", room2.getRoomNumber());
											jsonObject.put("robot", room2.getRobot());
											jsonObject.put("creator", room2.getCreator());
											jsonArray.add(jsonObject);
										}
										code = RoomCode.conflictAbortBind;
										success = false;
										message = "该房间已绑定终端 !";
									}
								} else {
									code = RoomCode.noExistBean;
									success = false;
									message = "没有该房间!";
								}
							}else{
								code = FamilyCode.privilegeMaster;
								success = false;
								message = "您不是群主，没有权限操作!";
							}
						}else{
							code = FamilyCode.noExistUser;
							success = false;
							message = "该家庭组中不存在该用户!";
						}
					}else{
						code = UserCode.noExistBean;
						success = false;
						message = "没有该用户!";
					}
				}else{
					code = FamilyCode.noExistBean;
					success = false;
					message = "没有该家庭组!";
				}
				
			}
		} catch (Exception e) {
			code = Other;
			success = false;
			message = "未知错误";
			e.printStackTrace();
		}
		
		json.put("code", code);
		json.put("message", message);
		json.put("result", jsonArray);
		logger.info("切换默认房间的出参:" + json);
		out.print(json.toString());
		return null;
	}

}
