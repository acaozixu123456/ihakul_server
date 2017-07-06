package com.xiaoai.web.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoai.entity.Info;
import com.xiaoai.service.IInfoService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
import com.xiaoai.util.XiaoiResult;

/**
 * @author ZERO
 * @Data 2017-7-3 上午9:59:03
 * @Description 公告
 */
@Controller("appInformationAction")
@Scope("prototype")
public class AppInformationAction extends XiaoaiMessage{
	
	private static Logger logger = Logger.getLogger(AppInformationAction.class);
	
	@Resource
	private IInfoService infoService;
	/**
	 * 获得所有公告
	 * @return
	 * @throws Exception
	 */
	public String getNewInfo() throws Exception{
		//boolean success = true;
		String message = null;
		int code = OK;
		logger.info("查询所有公告");
		
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		PrintWriter out = MyRequest.getResponse();
		try {
			List<Info> allInfo = infoService.getAllInfo();
			JSONObject j2 = null;
			for (Info info : allInfo) {
				j2 = new JSONObject();
				j2.put("id", info.getId());
				j2.put("content", info.getContent());
				j2.put("creatTime", info.getCreatTime());
				j2.put("creator", info.getCreator());
				j2.put("pushTime", info.getPushTime());
				j2.put("pushState", info.getPushState());
				j2.put("otherContent", info.getOtherContent());
				jsonArray.add(j2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		json.put("code", code);
		json.put("message",message);
		json.put("result", jsonArray);
		out.print(json);
		logger.info("查询所有公告出参:" + json);
		return null;
	}
	
	/**
	 * 新增公告
	 * @return
	 * @throws Exception
	 */
	public String InsertInfo() throws Exception{
		
		JSONObject json = new JSONObject();
		JSONObject jsonObject = MyRequest.getParameterNames();
		XiaoiResult xr = new XiaoiResult();
		 JSONObject j2=new JSONObject();
		PrintWriter out = MyRequest.getResponse();
		
		//获取参数
		String content = jsonObject.getString("content");
		String creator = jsonObject.getString("creator");
		String pushTime = jsonObject.getString("pushTime");
		String otherContent = jsonObject.getString("otherContent");
		logger.info("新增公告入参:"+jsonObject);
		
		//封装参数
		Info info = new Info();
		try {
			if(XATools.isNull(content)){
				xr = XiaoiResult.build("公告内容不能为空", InfoCode.emptyContent);
			}
			if(!XATools.isNull(creator)){
				info.setCreator(creator);
			}
			if(!XATools.isNull(pushTime)){
				info.setPushTime(new Date(Long.parseLong(pushTime)));
			}
			if(!XATools.isNull(otherContent)){
				info.setOtherContent(otherContent);
			}
			if(xr.isSuccess()){
				info.setContent(content);
				info.setCreatTime(new Date());
				info.setPushState(0);
				
				infoService.insertInfo(info);
				j2.put("id", info.getId());
			}
		} catch (Exception e) {
			xr = XiaoiResult.build("新增公告失败", InfoCode.insertFail);
			e.printStackTrace();
		}
		
		json.put("code", xr.getCode());
		json.put("message",xr.getMessage());
		json.put("result", j2);
		out.print(json);
		logger.info("新增公告出参:" + json);
		return null;
	}
}
