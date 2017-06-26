package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoai.dao.IFamilygroupDao;
import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.mina.service.push.PushMessage;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;

@Controller("appXiaoiAction")
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
	 * @param xiaoType
	 *            终端小艾类型(1,普通;2时尚)
	 * @return success=false(添加失败)或者success=true(添加成功)
	 * @throws IOException
	 */
	public String insert() throws IOException {
		success = true;
		message = null;
		code = OK;
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		String xiaoType = "";
		MyRequest.printParameterNames("添加终端的入参");
		String xiaoName = request.getParameter("xiaoName");
		String xiaoNumber = request.getParameter("xiaoNumber");
		String groupNumber = request.getParameter("groupNumber");
		String xiaoIp = request.getParameter("xiaoIp");
		String versionNumber = request.getParameter("versionNumber"); // 档案版本号
		JSONObject json = new JSONObject();
		Xiaoi xiao1 = new Xiaoi();
		if (XATools.isNull(xiaoName)) {
			code = XiaoiCode.emptyName;
			success = false;
			message = "小艾名称不能为空!";
		}
		if (XATools.isNull(xiaoIp)) {
			code = XiaoiCode.emptyIp;
			success = false;
			message = "小艾Ip不能为空!";
		}
		if (XATools.isNull(xiaoNumber)) {
			code = XiaoiCode.emptyId;
			success = false;
			message = "小艾编号不能为空!";
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

		if (XATools.isNull(versionNumber)) {
			code = FamilyCode.emptyVersion;
			success = false;
			message = "家庭组版本号不能为空!";
		}

		if (success) {
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
				xiao.setOnlineState(1);
				xiao.setFamilygroup(family);
				xiao.setXiaoIp(xiaoIp);
				xiao.setVolume(50); // 设置默认音量
				if (xiao1 == null) { // 如果查不到，说明该小艾可以添加;否则，更新小艾
					success = xiaoiService.insertXiaoi(xiao);
					family.setVersionNumber(Integer.parseInt(versionNumber));
					familyDao.updateFamily(family);
					if (success == false) {
						code = XiaoiCode.insertFalse;
						success = false;
						message = "添加小艾是失败!";
					}
				} else {
					xiao.setXid(xiao1.getXid());
					success = xiaoiService.updateXiaoi(xiao);
					if (success == false) {
						code = XiaoiCode.updateFalse;
						success = false;
						message = "修改小艾失败!";
					}
				}
			} else {
				code = FamilyCode.noExistBean;
				success = false;
				message = "没有该家庭组";
			}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("添加终端出参:" + json);
		out.print(json);
		return null;
	}

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
	public String update() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String xiaoName = request.getParameter("xiaoName");
		String xiaoType = request.getParameter("xiaoType");
		String id = request.getParameter("groupId");
		String xid = request.getParameter("xid");
		String userid = request.getParameter("userId");
		Users users = null;
		boolean fals = false;
		if (userid != null && !userid.equals("")) {
			users = usersService.selectUsersByid(Integer.parseInt(userid));
		}
		if (users != null) {
			Xiaoi xiaos = null;
			if (xid != null && !xid.equals("")) {
				int xiaoId = Integer.parseInt(xid);
				xiaos = xiaoiService.getXiaoiByid(xiaoId);
			}
			Familygroup family = null;
			if (id != null && !id.equals("")) {
				int groupId = Integer.parseInt(id);
				family = familyService.getFamilygroupByid(groupId);
				xiaos.setFamilygroup(family);
			}

			if (xiaoName != null && !xiaoName.equals("")) {
				xiaos.setXname(xiaoName);
			}
			if (xiaoType != null && !xiaoType.equals("")) {
				int xiaoT = Integer.parseInt(xiaoType);
				xiaos.setXiaoType(xiaoT);
			}
			fals = xiaoiService.updateXiaoi(xiaos);
		}

		JSONObject json = new JSONObject();
		json.put("fals", fals);
		out.print(json.toString());
		return null;
	}

	/**
	 * 删除或更换终端信息
	 * 
	 * @param xiaoNumber
	 *            终端编号
	 * @param userId
	 *            用户id
	 * @throws IOException
	 */
	public String delete() throws IOException {
		success = true;
		message = null;
		code = OK;
		JSONObject json = new JSONObject();
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("删除终端的入参");
		String userId = request.getParameter("userId");
		String xiaoNumber = request.getParameter("xiaoNumber");
		String groupNumber = request.getParameter("groupNumber");
		String versionNumber = request.getParameter("versionNumber");
		String newxiaoNumber = request.getParameter("xiaoNumber");
		Users users = null;
		Xiaoi xiaoi = null;
		FamilyUser fu1 = null;
		if (XATools.isNull(xiaoNumber)) {
			code = XiaoiCode.emptyId;
			success = false;
			message = "小艾编号不能为空!";
		}
		if (XATools.isNull(userId)) {
			code = UserCode.emptyId;
			message = "用户id不能为空";
			success = false;
		}
		if (XATools.isNull(groupNumber)) {
			code = FamilyCode.emptyId;
			success = false;
			message = "家庭组编号不能为空!";
		}
		if (success) {
			users = usersService.selectUsersByid(Integer.parseInt(userId));
			if (users != null) {
				xiaoi = xiaoiService.selectXiaoiByNumber(xiaoNumber);
				if (xiaoi != null) {
					Familygroup family = familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
					if (family != null) {
						if (Integer.parseInt(versionNumber) > family.getVersionNumber()) { // 如果终端的版本号大于服务端
							family.setVersionNumber(Integer.parseInt(versionNumber));
							familyService.updateFamily(family);
						}
						fu1 = fauserService.selectFamilyUserByGNU(users, family);
						if (fu1 != null) {
							if (userId.equals(fu1.getDna())) { // 判断该家庭组是不是该用户创建
								if(XATools.isNull(newxiaoNumber)){ //如果为空，则为删除终端; 否则更换终端 
									success = xiaoiService.delete(xiaoi);
								}else{
									success = xiaoiService.change(xiaoNumber, newxiaoNumber);
								}
								Xiaoi xi=xiaoiService.selectXiaoiByFa(family);
								if(xi!=null){
									JSONObject json2=new JSONObject();
									json2.put("key", "appdeleteXiaoi");
									json2.put("code", 0);
									json2.put("xiaoNumber", xiaoNumber);
									PushMessage.push2Xiao(json2);
								}
								if (success == false) {
									code = XiaoiCode.deleteFalse;
									success = false;
									message = "删除小艾失败!";
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
					code = XiaoiCode.noExistBean;
					success = false;
					message = "没有该小艾!";
				}
			} else {
				code = UserCode.noExistBean;
				success = false;
				message = "没有该用户!";
			}
		}
		json.put("code", code);
		json.put("message", message);
		out.print(json.toString());
		logger.info("删除终端的出参:" + json);
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
		success = true;
		message = null;
		code = OK;
		JSONObject json = new JSONObject();
		JSONObject json5 = new JSONObject();
		JSONArray array = new JSONArray();
		HttpServletRequest request = MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		MyRequest.printParameterNames("查询终端的入参");
		String xiaoNumber = request.getParameter("xiaoNumber");
		Xiaoi xiaoi = null;
		if (XATools.isNull(xiaoNumber)) {
			code = XiaoiCode.emptyId;
			success = false;
			message = "小艾编号不能为空!";
		}

		if (success) {
			xiaoi = xiaoiService.selectXiaoiByNumber(xiaoNumber);
			if (xiaoi != null) {
				json5.put("xname", xiaoi.getXname());
				json5.put("onlineState", xiaoi.getOnlineState()); // 在线状态(0,不在线;1,在线)
				json5.put("xiaoNumber", xiaoi.getXiaoNumber());// 终端编号
				json5.put("xiaoType", xiaoi.getXiaoType());// 终端类型(1,普通;2时尚)
				json5.put("xiaoiIp", xiaoi.getXiaoIp());// 终端IP
				json5.put("activationTime", xiaoi.getActivationTime());// 激活时间
				json5.put("mode", xiaoi.getMode());// 情景模式
				json5.put("volume", xiaoi.getVolume());// 声音
				array.add(json5);
			} else {
				code = XiaoiCode.noExistBean;
				success = false;
				message = "没有该小艾!";
			}
		}
		json.put("code", code);
		json.put("message", message);
		json.put("result", array);
		logger.info("查询终端出参:" + json);
		out.print(json.toString());
		return null;
	}

}