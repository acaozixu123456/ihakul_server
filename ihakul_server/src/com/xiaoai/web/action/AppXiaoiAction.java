package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

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
import com.xiaoai.dao.impl.XiaoiDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.mina.service.push.PushMessage;
import com.xiaoai.mina.service.push.PushMessage_Xiaoi;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
import com.xiaoai.util.XiaoiResult;

@Controller("appXiaoiAction")
@Scope("prototype")
public class AppXiaoiAction extends XiaoaiMessage {
	@Resource(name = "xiaoiService")
	private IXiaoiService xiaoiService;

	@Resource(name = "fauserService")
	private IFamilyUserService fauserService;
	@Resource(name = "familyService")
	private IFamilygroupService familyService;
	@Resource(name = "usersService")
	private IUsersService usersService;
	@Resource(name = "familyDao")
	private IFamilygroupDao familyDao;
	@Resource(name = "xiaoiDao")
	private XiaoiDao xiaoiDao;
	
	private boolean success; // 成功、失败标记
	private String message; // 信息
	private int code; // 标记
	private static Logger logger = Logger.getLogger(AppXiaoiAction.class);

	/**
	 * 添加终端
	 * 
	 * @param userId
	 *            用户id
	 * @param xiaoName
	 *            终端小艾名字
	 * @param xiaoNumber
	 *            终端小艾编号
	 * @param versionNumber
	 *            家庭组版本号
	 * @return success=false(添加失败)或者success=true(添加成功)
	 * @throws IOException
	 */
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	public String insert() throws IOException {
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		String xiaoType = "";
		MyRequest.printParameterNames("添加终端的入参");
		String xiaoName = request.getParameter("xiaoName");
		String xiaoNumber = request.getParameter("xiaoNumber");
		String groupNumber = request.getParameter("groupNumber");
		String xiaoIp = request.getParameter("xiaoIp");
		JSONObject json = new JSONObject();
		Xiaoi xiao1 = null;
		XiaoiResult xr = new XiaoiResult();
		if (XATools.isNull(xiaoName)) {
			xr = XiaoiResult.build("小艾名称不能为空!", XiaoiCode.emptyName);
		}
		if (XATools.isNull(xiaoIp)) {
			xr = XiaoiResult.build("小艾Ip不能为空!", XiaoiCode.emptyIp);
		}
		if (XATools.isNull(xiaoNumber)) {
			xr = XiaoiResult.build("小艾编号不能为空!", XiaoiCode.emptyId);
		}

		if (XATools.isNull(groupNumber)) {
			xr = XiaoiResult.build("家庭组编号不能为空!", FamilyCode.emptyId);
		} else {
			if (!XATools.isInteger(groupNumber)) {
				xr = XiaoiResult.build("家庭组编号格式不符 ", FamilyCode.formatisInconsistent);	
			}
		}
		if (xr.isSuccess()) {
			try {
				xiaoType = xiaoNumber.substring(xiaoNumber.length() - 1,
						xiaoNumber.length());
				Familygroup family = familyService.getFamilygroupByNumber(Integer
						.parseInt(groupNumber));
				if (family != null) {
					xiao1 = xiaoiService.selectXiaoiByNumber(xiaoNumber);
					Xiaoi xiao = new Xiaoi();
					xiao.setXname(xiaoName);
					xiao.setXiaoNumber(xiaoNumber);
					xiao.setXiaoType(Integer.parseInt(xiaoType));
					xiao.setState(1);
					xiao.setFamilygroup(family);
					xiao.setXiaoIp(xiaoIp);
					xiao.setVolume(50); // 设置默认音量
					if (xiao1 == null) { // 如果查不到，说明该小艾可以添加;否则，更新小艾	
						//不用推
						xr.setSuccess(xiaoiService.insertXiaoi(xiao));
					} else {
						xiao.setXid(xiao1.getXid());
						xr.setSuccess(xiaoiService.updateXiaoi(xiao));
						if (xr.isSuccess() == false) {
							xr = XiaoiResult.build("修改小艾失败!", XiaoiCode.updateFalse);
						}
					}
				} else {
					xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
				}
			} catch (Exception e) {
				xr = XiaoiResult.build("新增小艾失败！", XiaoiCode.insertFalse);
				e.printStackTrace();
			}
		}
		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		logger.info("添加终端出参:" + json);
		out.print(json);
		return null;
	}*/

	/**
	 * 修改终端属性
	 * 
	 * @param userId
	 *            用户id
	 * @param xiaoName
	 *            终端小艾名字
	 * @param xiaoType
	 *            终端类型
	 * @param groupId
	 *            家庭组id
	 * @param xid
	 *            终端id
	 * @return fals=false(修改失败)或者fals=true(修改成功)
	 * @throws IOException
	 */
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public String update() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		XiaoiResult xr = new XiaoiResult();
		PrintWriter out = response.getWriter();
		String xiaoNumber = request.getParameter("xiaoNumber");
		String xiaoName = request.getParameter("xiaoName");
		String groupNumber = request.getParameter("groupNumber");
		String userId = request.getParameter("userId");
		boolean fals = false;
		logger.info("修改小艾名称入参:{" +"xiaoNumber:" +xiaoNumber+",xiaoName:"+xiaoName+",groupNumber:"+groupNumber+",userId:"+userId+"");
		//参数判断
		if(XATools.isNull(xiaoNumber)){
			xr = XiaoiResult.build("小艾编号不能为空", XiaoiCode.emptyId);
		}
		if(XATools.isNull(xiaoName)){
			xr = XiaoiResult.build("小艾名称不能为空", XiaoiCode.emptyName);
		}
		if(XATools.isNull(groupNumber)){
			xr = XiaoiResult.build("家庭组编号不能为空", FamilyCode.emptyId);
		}
		if(XATools.isNull(userId)){
			xr = XiaoiResult.build("用户id不能为空", UserCode.emptyId);
		}
		//权限判断,应该优化到过滤器去进行权限判断
		try {
			if(xr.isSuccess()){
				Xiaoi xiaos = xiaoiService.selectXiaoiByNumber(xiaoNumber);
				if(xiaos==null){
					//没有此小艾
					xr = XiaoiResult.build("没有该小艾", XiaoiCode.noExistBean);
				}else{
					//设置名称
					xiaos.setXname(xiaoName);
					Familygroup familygroup = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
					if(familygroup!=null){
						Users user = usersService.selectUsersByid(Integer.parseInt(userId));
						if(user!=null){
							FamilyUser familyUser = fauserService.selectFamilyUserByGNU(user, familygroup);
							if(familyUser!=null){
								//是否是家庭组管理员
								if(familyUser.getDna().equals(userId)){
									//推送
									//获取当前在线的小艾
									List<Xiaoi> allOnlineXiaois = xiaoiDao.selectXiaoiByFa(familygroup);
									com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
									boolean pushState = false;
									boolean flag = false;
									HashMap hashMap = null;
									for (Xiaoi xiaoi : allOnlineXiaois) {
										//判断当前小艾是否在线
										if(xiaoi.getState()==1){
											//在线，推送
											hashMap = new HashMap();
											hashMap.put("xiaoi", xiaos);
											json2.put("key", "appupdateXiaoiName");
											json2.put("code", 0);
											json2.put("xiaoNumber", xiaoi.getXiaoNumber());
											pushState = PushMessage_Xiaoi.push2Xiao(json2, hashMap);
											if(pushState){
												flag = true;
											}
										}
									}
									if(flag){
										//当前家庭组有在线小艾推送成功,执行修改小艾名称
										xr.setSuccess(xiaoiService.updateXiaoi(xiaos));
										if (xr.isSuccess() == false) {
											xr = XiaoiResult.build("修改小艾失败", XiaoiCode.updateFalse);
										}
									}else{
										//当前家庭组没有任何一台小艾在线，推送失败，拒绝添加房间！
										/*code = XiaoiCode.noExistOnlinBean;
										message = "没有在线小艾!";*/
										xr = XiaoiResult.build("没有在线小艾!", XiaoiCode.noExistOnlinBean);
									}
								}else{
									xr = XiaoiResult.build("您不是群主，没有权限操作!", FamilyCode.privilegeMaster);
								}
							}else{
								xr = XiaoiResult.build("该家庭组中不存在该用户", FamilyCode.noExistUser);
							}
						}else{
							xr = XiaoiResult.build("没有该用户", UserCode.noExistBean);
						}
					}else{
						xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
					}
				}
			}
		} catch (Exception e) {
			xr = XiaoiResult.build("修改小艾失败", XiaoiCode.updateFalse);
			e.printStackTrace();
		}
		

		JSONObject json = new JSONObject();
		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		out.print(json.toString());
		logger.info("修改小艾名称出参:" + json);
		return null;
	}

	/**
	 * 删除终端信息
	 * 
	 * @param xiaoNumber
	 *            终端编号
	 * @param userId
	 *            用户id
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String delete() throws IOException {
		JSONObject json = new JSONObject();
		XiaoiResult xr = new XiaoiResult();
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("删除终端的入参");
		String userId = request.getParameter("userId");
		String xiaoNumber = request.getParameter("xiaoNumber");
		String groupNumber = request.getParameter("groupNumber");
		Users users = null;
		Xiaoi xiaoi = null;
		FamilyUser fu1 = null;
		Familygroup family = null;
		if (XATools.isNull(xiaoNumber)) {
			xr = XiaoiResult.build("小艾编号不能为空!", XiaoiCode.emptyId);
		}
		if (XATools.isNull(userId)) {
			xr = XiaoiResult.build("用户id不能为空", UserCode.emptyId);
		}
		if (XATools.isNull(groupNumber)) {
			xr = XiaoiResult.build("家庭组编号不能为空!", FamilyCode.emptyId);
		}
		if (xr.isSuccess()) {
			try {
				users = usersService.selectUsersByid(Integer.parseInt(userId));
				if (users != null) {
					xiaoi = xiaoiService.selectXiaoiByNumber(xiaoNumber);
					if (xiaoi != null) {
						family = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
						if (family != null) {
							fu1 = fauserService.selectFamilyUserByGNU(users, family);
							if (fu1 != null) {
								if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
										//推送
										//获取当前在线的小艾
										//List<Xiaoi> allOnlineXiaois = xiaoiDao.selectXiaoiByFa(family);
										com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
										boolean pushState = false;
										boolean flag = false;
										HashMap hashMap = null;
										//for (Xiaoi xiaoi_ : allOnlineXiaois) {
											//判断当前小艾是否在线
											//if(xiaoi.getState()==1){
												//在线，推送
												hashMap = new HashMap();
												hashMap.put("xiaoNumber", xiaoi.getXiaoNumber());
												json2.put("key", "appdeleteXiaoi");
												json2.put("code", 0);
												json2.put("xiaoNumber", xiaoi.getXiaoNumber());
												pushState = PushMessage_Xiaoi.push2Xiao(json2, hashMap);
												//不管推送成功与否直接删除
												flag = true;
											//}else{
												//不在线
												
											//}
										//}
										if(flag){
											//当前家庭组有在线小艾推送成功,执行删除小艾（假删除只删除房间电器）
											xr.setSuccess(xiaoiService.delete(xiaoi));
											if (xr.isSuccess() == false) {
												xr = XiaoiResult.build("删除小艾失败", XiaoiCode.deleteFalse);
											}else{
												xiaoi.setState(2);
												xiaoiService.updateXiaoi(xiaoi);
											}
										}else{
											//当前家庭组没有任何一台小艾在线，推送失败，拒绝添加房间！
											/*code = XiaoiCode.noExistOnlinBean;
											message = "没有在线小艾!";*/
											xr = XiaoiResult.build("没有在线小艾!", XiaoiCode.noExistOnlinBean);
										}
										
								} else {
									xr = XiaoiResult.build("您不是群主，没有权限操作!", FamilyCode.privilegeMaster);
								}
							} else {
								xr = XiaoiResult.build("该家庭组中不存在该用户", FamilyCode.noExistUser);
							}
						} else {
							xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
						}
					} else {
						xr = XiaoiResult.build("没有该小艾!", XiaoiCode.noExistBean);
					}
				} else {
					xr = XiaoiResult.build("没有该用户!", UserCode.noExistBean);
				}
			} catch (Exception e) {
				xr = XiaoiResult.build("修改小艾失败", XiaoiCode.deleteFalse);
				e.printStackTrace();
			}
		}
		
		JSONObject familyGroup = familyService.getFamilyGroup(groupNumber);
		if(xr.getCode()==0){
			out.print(familyGroup);
			logger.info("删除终端的出参:" + familyGroup);
		}else{
			json.put("code", xr.getCode());
			json.put("message", xr.getMessage());
			
			json.put("result", familyGroup);
			out.print(json);
			logger.info("删除终端的出参:" + json);
		}
		return null;
	}

	
	/**
	 * 更换终端信息
	 * 
	 * @param xiaoNumber
	 *            终端编号
	 * @param userId
	 *            用户id
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String changeXiaoi() throws IOException {
		JSONObject json = new JSONObject();
		XiaoiResult xr = new XiaoiResult();
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("更换终端的入参");
		String userId = request.getParameter("userId");
		String xiaoNumber = request.getParameter("xiaoNumber");
		String groupNumber = request.getParameter("groupNumber");
		String newxiaoNumber = request.getParameter("newxiaoNumber");
		Users users = null;
		Xiaoi xiaoi = null;
		FamilyUser fu1 = null;
		Familygroup family = null;
		if (XATools.isNull(xiaoNumber)) {
			xr = XiaoiResult.build("旧的小艾编号不能为空!", XiaoiCode.emptyId);
		}
		if (XATools.isNull(userId)) {
			xr = XiaoiResult.build("用户id不能为空", UserCode.emptyId);
		}
		if (XATools.isNull(groupNumber)) {
			xr = XiaoiResult.build("家庭组编号不能为空!", FamilyCode.emptyId);
		}
		if (XATools.isNull(newxiaoNumber)) {
			xr = XiaoiResult.build("新的小艾编号不能为空!", XiaoiCode.emptyId);
		}
		if (xr.isSuccess()) {
			try {
				//终端相同,不替换
				if(xiaoNumber.equals(newxiaoNumber)){
					xr = XiaoiResult.build("更换的小艾相同!", XiaoiCode.conflictBean);
				}
				if(xr.isSuccess()){
					try {
						users = usersService.selectUsersByid(Integer.parseInt(userId));
						if (users != null) {
							xiaoi = xiaoiService.selectXiaoiByNumber(xiaoNumber);
							Xiaoi xiaoi_new = xiaoiService.selectXiaoiByNumber(newxiaoNumber);
							if (xiaoi != null) {
								if(xiaoi_new!=null){
									family = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
									if (family != null) {
										/*if (Integer.parseInt(versionNumber) > family.getVersionNumber()) { // 如果终端的版本号大于服务端
											family.setVersionNumber(Integer.parseInt(versionNumber));
											familyService.updateFamily(family);
										}*/
										fu1 = fauserService.selectFamilyUserByGNU(users, family);
										if (fu1 != null) {
											if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
												//推送
												//获取当前在线的小艾
												List<Xiaoi> allOnlineXiaois = xiaoiDao.selectXiaoiByFa(family);
												com.alibaba.fastjson.JSONObject json2 = new com.alibaba.fastjson.JSONObject();
												boolean pushState = false;
												boolean flag = false;
												HashMap hashMap = null;
												for (Xiaoi xiaoi_ : allOnlineXiaois) {
													//判断当前小艾是否在线
													if(xiaoi_.getState()==1){
														//在线，推送
														hashMap = new HashMap();
														hashMap.put("xiaoi", xiaoi);
														json2.put("key", "appchangeXiaoi");
														json2.put("code", 0);
														json2.put("xiaoNumber", xiaoi.getXiaoNumber());
														pushState = PushMessage_Xiaoi.push2Xiao(json2, hashMap);
														if(pushState){
															flag = true;
														}
													}
												}
												if(flag){
													xr.setSuccess(xiaoiService.change(xiaoNumber, newxiaoNumber));
													//被替换的小艾家庭组设置为空(状态state设置为3)
													xiaoiService.cleanInfo(xiaoi);
													//当前家庭组有在线小艾推送成功,执行删除小艾
													if (xr.isSuccess() == false) {
														xr = XiaoiResult.build("更换终端失败", XiaoiCode.changeFalse);
													}
												}else{
													//当前家庭组没有任何一台小艾在线，推送失败，拒绝添加房间！
													xr = XiaoiResult.build("没有在线小艾!", XiaoiCode.noExistOnlinBean);
												}
											} else {
												xr = XiaoiResult.build("您不是群主，没有权限操作!", FamilyCode.privilegeMaster);
											}
										} else {
											xr = XiaoiResult.build("该家庭组中不存在该用户", FamilyCode.noExistUser);
										}
									} else {
										xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
									}
								}else{
									xr = XiaoiResult.build("不存在新小艾!", XiaoiCode.noExistBean);
								}
							} else {
								xr = XiaoiResult.build("没有该小艾!", XiaoiCode.noExistBean);
							}
						} else {
							xr = XiaoiResult.build("没有该用户!", UserCode.noExistBean);
						}
					} catch (Exception e) {
						xr = XiaoiResult.build("更换终端失败", XiaoiCode.changeFalse);
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				xr = XiaoiResult.build("更换终端失败", XiaoiCode.changeFalse);
				e.printStackTrace();
			}
		}
		
		if(xr.getCode()==0){
			JSONObject familyGroup = familyService.getFamilyGroup(groupNumber);
			out.print(familyGroup);
			
			logger.info("更换终端成功的出参:" + familyGroup);
		}else{
			json.put("code", xr.getCode());
			json.put("message", xr.getMessage());
			//失败不返回信息
			//json.put("result", familyGroup);
			out.print(json);
			logger.info("更换终端失败的出参:" + json);
		}
		return null;
	}
	
	
	/**
	 * 根据编号得到小艾
	 * 
	 * @param xiaoNumber
	 *            终端小艾编号
	 * @throws IOException
	 */
	public String getXiaoiByid() throws IOException {
		JSONObject json = new JSONObject();
		JSONObject json5 = new JSONObject();
		JSONArray array = new JSONArray();
		XiaoiResult xr = new XiaoiResult();
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("查询终端的入参");
		String xiaoNumber = request.getParameter("xiaoNumber");
		Xiaoi xiaoi = null;
		if (XATools.isNull(xiaoNumber)) {
			xr = XiaoiResult.build("小艾编号不能为空!", XiaoiCode.emptyId);
		}

		if (xr.isSuccess()) {
			try {
				xiaoi = xiaoiService.selectXiaoiByNumber(xiaoNumber);
				if (xiaoi != null) {
					json5.put("xname", xiaoi.getXname());
					json5.put("onlineState", xiaoi.getState()); // 在线状态(0,不在线;1,在线;2删除)
					json5.put("xiaoNumber", xiaoi.getXiaoNumber());// 终端编号
					json5.put("xiaoType", xiaoi.getXiaoType());// 终端类型(1,普通;2时尚)
					json5.put("xiaoiIp", xiaoi.getXiaoIp());// 终端IP
					json5.put("activationTime", xiaoi.getActivationTime());// 激活时间
					json5.put("mode", xiaoi.getMode());// 情景模式
					json5.put("volume", xiaoi.getVolume());// 声音
					array.add(json5);
				} else {
					xr = XiaoiResult.build("没有该小艾!", XiaoiCode.noExistBean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		json.put("result", array);
		logger.info("查询终端出参:" + json);
		out.print(json.toString());
		return null;
	}

}
