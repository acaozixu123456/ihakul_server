package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.xiaoai.dao.IHouseholdDao;
import com.xiaoai.dao.impl.RoomDao;
import com.xiaoai.dao.impl.XiaoiDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Household;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IRoomService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;

@Controller("appRoomAction")
public class AppRoomAction extends XiaoaiMessage {
	@Resource(name = "roomService")
	private IRoomService roomService;
	@Resource(name = "familyService")
	private IFamilygroupService familyService;
	@Resource(name = "usersService")
	private IUsersService usersService;
	@Resource(name = "roomDao")
	private RoomDao roomDao;

	@Resource(name = "xiaoiDao")
	private XiaoiDao xiaoiDao;

	@Resource(name = "houseHoldDao")
	private IHouseholdDao householdDao;

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
	public String insert() throws IOException {
		success = true;
		message = null;
		code = OK;
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("添加房间的入参");
		String groupNumber = request.getParameter("groupNumber");
		String userId = request.getParameter("userId");
		String roomName = request.getParameter("roomName");
		String roomNickName = request.getParameter("roomNickName");
		String floor = request.getParameter("floor");
		String parentId = request.getParameter("parentId");
		String roomNumber = request.getParameter("roomNumber");
		String xiaoNumber = request.getParameter("xiaoNumber");
		String versionNumber = request.getParameter("versionNumber"); // 档案版本号
		String robot = request.getParameter("robot"); // 终端绑定
		Room room = new Room();
		Users users = null;
		FamilyUser fu1 = null;
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
		if (XATools.isNull(roomNumber)) {
			code = RoomCode.emptyId;
			success = false;
			message = "房间编号不能为空!";
		}
		if (XATools.isNull(xiaoNumber)) {
			code = XiaoiCode.emptyId;
			success = false;
			message = "小艾编号不能为空!";
		}
		if (XATools.isNull(versionNumber)) {
			code = FamilyCode.emptyVersion;
			success = false;
			message = "家庭组版本号不能为空 !";
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
			users = usersService.selectUsersByid(Integer.parseInt(userId));
			if (users != null) {
				Familygroup family = familyService
						.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				if (family != null) {
					 if(Integer.parseInt(versionNumber)>family.getVersionNumber()){ //如果终端的版本号大于服务端
						 family.setVersionNumber(Integer.parseInt(versionNumber));
						 familyService.updateFamily(family);
					  }
					fu1 = fauserService.selectFamilyUserByGNU(users, family);
					if (fu1 != null) {
						if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
							Xiaoi xil = xiaoiDao
									.selectXiaoiByNumber(xiaoNumber);
							if (xil != null) {
								Room kk = roomDao.getRoomByGroupId(family,
										roomNumber);
								if (kk == null) { // 判断该家庭组是否已经添加了该房间
									room.setRoomNumber(roomNumber);
									room.setFamilygroup(family);
									room.setRoomName(roomName);
									room.setCreateTime(XATools.getTNowTime());
									room.setCreator(xiaoNumber);
									success = roomService.insertRoom(room);
									if (success == false) {
										code = RoomCode.insertFalse;
										message = "创建房间失败!";
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
		}

		json.put("code", code);
		json.put("message", message);
		json.put("result", array); // 房间信息
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
			// 根据用户id查到用户信息
			users = usersService.selectUsersByid(Integer.parseInt(userId));
			if (users != null) {
				room = roomService.getRoomByid(Integer.parseInt(roomNumber));
				if (room != null) {
					room.setRoomName(roomName);
					success = roomService.updateRoom(room);
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
		String versionNumber = request.getParameter("versionNumber");
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
		if (XATools.isNull(versionNumber)) {
			code = FamilyCode.emptyVersion;
			success = false;
			message = "家庭组版本号不能为空 !";
		}
		if (success) {
			// 根据用户id查到用户信息
			users = usersService.selectUsersByid(Integer.parseInt(userId));
			if (users != null) {
				Familygroup family = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				if (family != null) { // 通过家庭组和房间编号查询房间信息
					if(Integer.parseInt(versionNumber)>family.getVersionNumber()){ //如果终端的版本号大于服务端
						 family.setVersionNumber(Integer.parseInt(versionNumber));
						 familyService.updateFamily(family);
					  }
					fu1 = fauserService.selectFamilyUserByGNU(users, family);
					if (fu1 != null) {
						if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
							room = roomDao.getRoomByGroupId(family, roomNumber);
							if (room != null) { // 判断该家庭组是否已经添加了该房间
								success = roomService.deleteRoom(room);
								if (success == false) {
									code = RoomCode.deleteFalse;
									message = "删除房间失败!";
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
			family = familyService.getFamilygroupByNumber(Integer
					.parseInt(groupNumber));
			json.put("versionNumber", family.getVersionNumber()); // 版本号
			if (family != null) {
				room = roomDao.getRoomByGroupId(family, roomNumber);
				if (room != null) { // 判断该家庭组是否已经添加了该房间
					JSONArray array4 = new JSONArray();
					json2.put("roomName", room.getRoomName()); // 房间名称
					json2.put("roomNickName", room.getRoomNickName()); // 房间昵称
					json2.put("floor", room.getFloor()); // 房间楼层(默认 0)
					json2.put("parentId", room.getParentId()); // 父节点标识
					json2.put("roomNumber", room.getRoomNumber()); // 房间编号
					json2.put("robot", room.getRobot()); // 终端绑定的编号

					List<Household> householdList = householdDao
							.selectHouseholdByroomID(room.getId());
					if (!XATools.isNull(householdList)) {
						for (Household household : householdList) {
							JSONObject json4 = new JSONObject();
							json4.put("roomNumber", room.getRoomNumber()); // 房间编号
							json4.put("names", household.getEaName()); // 家电名称
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
	public String changeRoom() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("切换默认房间入参");
		String roomNumber = request.getParameter("roomNumber");
		String xiaoNumber = request.getParameter("xiaoNumber");
		Room room = null;
		JSONObject json = new JSONObject();
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

		if (success) {
			room = roomDao.getRoomByRoomNumber(roomNumber);
			if (room != null) {
				if (XATools.isNull(room.getRobot())) {
					room.setRobot(xiaoNumber);
					roomDao.updateRoom(room);
				}
			} else {
				code = RoomCode.conflictAbortBind;
				success = false;
				message = "该房间已绑定终端 !";
			}
		} else {
			code = RoomCode.noExistBean;
			success = false;
			message = "没有该房间!";
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("切换默认房间的出参:" + json);
		out.print(json.toString());
		return null;
	}

}
