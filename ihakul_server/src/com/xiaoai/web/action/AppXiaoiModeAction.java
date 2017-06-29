package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.XiaoiMode;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IXiaoiModeService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
import com.xiaoai.util.XiaoiResult;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:33:00
 * @Description
 */
@Controller("appXiaoiModeAction")
public class AppXiaoiModeAction extends XiaoaiMessage {

	@Resource
	private IXiaoiModeService xiaoiModeService;

	@Resource
	private IFamilygroupService familygroupService;

	boolean success;
	String message;
	int code;
	private static Logger logger = Logger.getLogger(AppXiaoiAction.class);

	/**
	 * 新增情景模式
	 * @return
	 * @throws IOException
	 */
	public String insertMode() throws IOException {
		success = true;
		message = null;
		code = OK;

		PrintWriter out = MyRequest.getResponse();
		//HttpServletRequest request = MyRequest.getRequest();
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		JSONObject jsonObject = MyRequest.getParameterNames();
		XiaoiResult xr = new XiaoiResult();
		jsonObject.put("triggerTime", jsonObject.getString("trigger"));
		String classId = jsonObject.getString("classId");
		String argument = jsonObject.getString("argument");
		String eaNumber = jsonObject.getString("eaNumber");
		String orders = jsonObject.getString("orders");
		String mode = jsonObject.getString("mode");
		String groupNumber = jsonObject.getString("groupNumber");
		//String trigger = jsonObject.getString("trigger");
		
		logger.info("添加情景模式入参：" + jsonObject);
		if (XATools.isNull(classId)) {
			xr=XiaoiResult.build("控制类型不能为空！", XiaoiModeCode.emptyClassId);
		}
		if (XATools.isNull(argument)) {
			xr=XiaoiResult.build("执行参数不能为空！", XiaoiModeCode.emptyArgument);
		}
		if (XATools.isNull(eaNumber)) {
			xr=XiaoiResult.build("电器标识不能为空！", XiaoiModeCode.emptyEaNumber);
		}
		if (XATools.isNull(orders)) {
			xr=XiaoiResult.build("Orders不能为空！", Plan.emptyOrder);
		}
		if (XATools.isNull(mode)) {
			xr=XiaoiResult.build("场景模式不能为空！", XiaoiModeCode.emptyMode);
		}
		if (XATools.isNull(groupNumber)) {
			xr=XiaoiResult.build("家庭组编号不能为空！", FamilyCode.emptyId);
		}

		if (xr.isSuccess()) {
			// 首先检查家庭组id是否存在
			Familygroup family = familygroupService
					.getFamilygroupByNumber(Integer.parseInt(groupNumber));
			if (family != null) {
				XiaoiMode xiaoiMode = new XiaoiMode();
				xiaoiMode=JSON.toJavaObject(jsonObject, XiaoiMode.class);
				try {
					xiaoiModeService.insertMode(xiaoiMode);
					json1.put("modeId", xiaoiMode.getMode());
				} catch (Exception e) {
					xr=XiaoiResult.build("新增情景模式失败！", XiaoiModeCode.insertFail);
					e.printStackTrace();
				}
				/*if (!insertMode) {
					xr=XiaoiResult.build("新增情景模式失败！", XiaoiModeCode.insertFail);
				}else{
					json1.put("modeId", xiaoiMode.getMode());
				}*/
				
			} else {
				xr=XiaoiResult.build("没有该家庭组！", FamilyCode.noExistBean);
			}

		}
		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		json.put("result", json1);
		out.print(json);
		logger.info("新增情景模式出参:" + json);
		return null;
	}
	
	/**
	 * 删除情景模式
	 * @return
	 * @throws IOException 
	 */
	public String deleteMode() throws IOException{
		PrintWriter out = MyRequest.getResponse();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = MyRequest.getParameterNames();
		XiaoiResult xr = new XiaoiResult();
		logger.info("删除情景模式入参：" + jsonObject);
		/*获取参数*/
		String id = jsonObject.getString("modeId");
		if(XATools.isNull(id)){
			xr=XiaoiResult.build("情景模式id不能为空", XiaoiModeCode.emptyId);
		}
		if(xr.isSuccess()){
			//查询当前id是否存在情景模式
			List<XiaoiMode> list = xiaoiModeService.findById(Long.parseLong(id));
			if(!XATools.isNull(list)){	
				//?是否需要判断有没有删除权限
				for (XiaoiMode xiaoiMode2 : list) {
					boolean result = xiaoiModeService.deleteMode(xiaoiMode2);
				}
			}else{
				//当前id不存在
				//xr=XiaoiResult.build("未找到该任务！", Plan.noExistBean);
			}
		}
		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		out.print(json);
		logger.info("删除情景模式出参:" + json);
		return null;
	}
	
	/**
	 * 通过Modeid获取情景模式
	 * @return
	 * @throws IOException 
	 */
	public String getModeById() throws IOException{
		PrintWriter out = MyRequest.getResponse();
		JSONObject json = new JSONObject();
		JSONObject jsonObject = MyRequest.getParameterNames();
		XiaoiResult xr = new XiaoiResult();
		logger.info("查询情景模式入参：" + jsonObject);
		if(XATools.isNull(jsonObject.getString("modeId"))){
			xr=XiaoiResult.build("情景模式id不能为空！", XiaoiModeCode.emptyId);
		}
		List<XiaoiMode> list =  null;
		if(xr.isSuccess()){
			list = xiaoiModeService.findModeById(jsonObject.getInteger("modeId"));
		}
		
		JSONObject json3=null;
		JSONArray array = new JSONArray();
		for (XiaoiMode xm : list) {
			json3 = (JSONObject) JSONObject.toJSON(xm);
			array.add(json3);
		}
		
		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		json.put("result",array);
		out.print(json);
		return null;
	}
	
	/**
	 * 通过groupNum获得情景模式
	 * @param groupNumber
	 * @return
	 * @throws IOException
	 */
	public String getAllModeByGroupNum() throws IOException{
		success = true;
		message = null;
		code = OK;
		PrintWriter out = MyRequest.getResponse();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject jsonObject = MyRequest.getParameterNames();
		XiaoiResult xiaoiResult = new XiaoiResult();
		List<XiaoiMode> findModeByGroupNum = java.util.Collections.emptyList();
		logger.info("查询情景模式入参：" + jsonObject);
		//获得参数
		String groupNumber = jsonObject.getString("groupNumber");
		//参数判断
		if(XATools.isNull(groupNumber)){
			xiaoiResult = XiaoiResult.build("家庭组编号不能为空", FamilyCode.emptyId);
		}
		if(xiaoiResult.isSuccess()){
			Familygroup  family=familygroupService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
			if(family!=null){
				findModeByGroupNum = xiaoiModeService.findModeByGroupNum(family.getGroupNumber());
				if(!XATools.isNull(findModeByGroupNum)){
					JSONObject json2 = null;
					for (XiaoiMode xm : findModeByGroupNum) {
						json2 = new JSONObject();
						json2.put("id", xm.getId());
						json2.put("triggerTime", xm.getTriggerTime());
						json2.put("classId", xm.getClassId());
						json2.put("orders", xm.getOrders());
						json2.put("eaNumber", xm.getEaNumber());
						json2.put("argument", xm.getArgument());
						json2.put("mode", xm.getMode());
						json2.put("groupNumber", xm.getGroupNumber());
						array.add(json2);
					}
				}else{
					xiaoiResult = XiaoiResult.build("该家庭组下没有任何情景模式", XiaoiModeCode.noExistBean);
				}
			}else{
				xiaoiResult = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
			}
		}
		json.put("code", xiaoiResult.getCode());
		json.put("message", xiaoiResult.getMessage());
		json.put("result", array);
		logger.info("查询情景模式出参：" + jsonObject);
		out.print(json);
		return null;
		
	}
	
	
}
