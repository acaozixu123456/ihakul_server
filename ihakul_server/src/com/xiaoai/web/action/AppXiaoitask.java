package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoai.dao.IFamilygroupDao;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.entity.Xiaoitask;
import com.xiaoai.mina.service.push.PushMessage;
import com.xiaoai.service.IXiaoitaskService;
import com.xiaoai.service.impl.XiaoiService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;

/**
 * @author ZERO
 * @Description  定时任务
 */
@Controller("appXiaoitask")
public class AppXiaoitask extends XiaoaiMessage{

	private boolean success ;         //成功、失败标记
	private  String message ;        //信息
	private int code ;				 //标记
	private static Logger logger = Logger.getLogger(AppXiaoitask.class);
	
	@Resource(name = "xiaoitaskService")
	private IXiaoitaskService xiaoitaskService;
	@Resource(name="familyDao")
	private IFamilygroupDao familyDao;
	
	@Resource(name="xiaoiService")
	private XiaoiService xiaoiService;
	
	/**
	 * 添加计划任务
	 * @param groupNumber 家庭组编号
	 * @param create  创建时间
	 * @param trigger 执行时间
	 * @param things  任务详情
	 * @param rules   重复规则
	 * @param object  任务类型
	 * @param orders  任务意图
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String insert() throws IOException{
		success =true;
		message =null;
		code=OK;
		JSONObject json=new JSONObject();
		JSONObject json1=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("添加定时任务的入参");
		String groupNumber=request.getParameter("groupNumber");
		long create=Long.parseLong(request.getParameter("create"));
		String trigger=request.getParameter("trigger");
		String things=request.getParameter("things");
		String rules=request.getParameter("rules");
		String object=request.getParameter("object");
		String orders=request.getParameter("orders");
		String xiaoNumber=request.getParameter("xiaoNumber");
		if(XATools.isNull(xiaoNumber)){
			code=XiaoiCode.emptyId;
			success=false;
			message="小艾编号不能为空!";
		}
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
		}
		 
		if(XATools.isNull(trigger)){
			code=Plan.emptyTrigger;
			success=false;
			message="任务触发时间不能为空!";
		}
		
		if(!XATools.checkOrders(orders)){
			code=Plan.emptyOrder;
			success=false;
			message="任务意图不能为空!";
		}else{
			if(!orders.equals("ALARM_SET")){
				if(XATools.isNull(object)){
					code=Plan.emptyObject;
					success=false;
					message="任务对象不能为空!";
				}
				if(XATools.isNull(things)){
					code=Plan.emptyThings;
					success=false;
					message="任务事项不能为空!";
				}
			}
		}
		
		
	if(success){
		Familygroup  family=familyDao.getFamilygroupByNumber(Integer.parseInt(groupNumber));
		if(family!=null){
		//Xiaoi xiaoi=xiaoiService.selectXiaoiByFa(family);
		Xiaoi xiaoi = xiaoiService.selectXiaoiByNumber(xiaoNumber);
		if(xiaoi!=null){
		Xiaoitask xiaoitask=new Xiaoitask();
		xiaoitask.setCreationTime(create);
		xiaoitask.setTriggerTime(Long.parseLong(trigger));
		xiaoitask.setGroupId(family.getGroupId());
		xiaoitask.setThings(things);
		xiaoitask.setObject(object);
		xiaoitask.setRules(rules);
		xiaoitask.setOrders(orders);
		success=xiaoitaskService.insertXiaoitask(xiaoitask);
		if(success){
			JSONObject json2=new JSONObject();
			json2.put("key", "appgetXiaoitask");
			json2.put("code", xiaoitask.getId());
			json2.put("xiaoNumber", xiaoi.getXiaoNumber());
			boolean flag = PushMessage.push2Xiao(json2);
			if(!flag){
				//当前终端推送失败，推送当前家庭组下的其他在线终端
				//Familygroup familygroupByid = familyDao.getFamilygroupByid(family.getGroupId());
				List<Xiaoi> xiaois = xiaoiService.selectXiaoiByid(family.getGroupId());
				for (Xiaoi xiaoi2 : xiaois) {
					//判断当前小艾是否在线
					if(xiaoi2.getOnlineState()==1){
						//在线，推送
						json2=new JSONObject();
						json2.put("key", "appgetXiaoitask");
						json2.put("code", xiaoitask.getId());
						json2.put("xiaoNumber", xiaoi2.getXiaoNumber());
						flag = PushMessage.push2Xiao(json2);
						if(flag){
							break;
						}
					}else{
						//所有终端都推送失败。。
					}
				}
			}
			json1.put("taskId", xiaoitask.getId());
		}else{
			code=Plan.insertFalse;
			message="新增计划任务失败! ";
		}
		}else{
			code=XiaoiCode.noExistOnlinBean;
			message="没有在线小艾 !";
	 	}
		}else{
			code=FamilyCode.noExistBean;
			message="没有该家庭组";
		}
	 }
	  	json.put("code", code);
		json.put("message", message);
		json.put("result", json1);
		logger.info("添加计划任务的出参:"+json);
		out.print(json);
		return null;
	}
	
	
	/**
	 * 修改计划任务
	 * @param groupId 家庭组Id
	 * @param create  创建时间
	 * @param trigger 执行时间
	 * @param things  任务详情
	 * @param rules   重复规则
	 * @param object  任务类型
	 * @param classId  家电类型
	 * @param mode  情景模式
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String update() throws IOException{
		success =true;
		message =null;
		code=OK;
		JSONObject json=new JSONObject();
		JSONObject json1=new JSONObject();
//		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("添加定时任务的入参");
		json1=MyRequest.getParameterNames();
		Xiaoitask xiaoitask=new Xiaoitask();
		xiaoitask=JSON.toJavaObject(json1, Xiaoitask.class);
		success=xiaoitaskService.updateXiaoitask(xiaoitask);
		if(success==false){
			code=4444;
			message="ssss";
		}
	  	json.put("code", code);
		json.put("message", message);
		logger.info("添加定时任务的出参:"+json);
		out.print(json);
		return null;
	}
	
	
	/**
	 * 删除定时任务
	 * @param taskId  定时任务ID
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		success =true;
		message =null;
		code=OK;
		JSONObject json=new JSONObject();
		JSONObject json1=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("删除定时任务的入参");
		String taskId=request.getParameter("taskId");
		String groupNumber=request.getParameter("groupNumber");
		Xiaoitask xiaoitask=new Xiaoitask();
		if(XATools.isNull(taskId)){
			code=Plan.emptyId;
			success=false;
			message="任务ID不能为空!";
		}
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
		}
		if(success){
			xiaoitask=xiaoitaskService.selectXiaoitaskById(Long.parseLong(taskId));
			if(xiaoitask!=null){
				Familygroup  family=familyDao.getFamilygroupByNumber(Integer.parseInt(groupNumber));
				if(family!=null){
				Xiaoi xiaoi=xiaoiService.selectXiaoiByFa(family);
				success=xiaoitaskService.deleteXiaoitask(xiaoitask);
				if(success){
					JSONObject json2=new JSONObject();
					json2.put("key", "appdelXiaoitask");
					json2.put("code", xiaoitask.getId());
					json2.put("xiaoNumber", xiaoi.getXiaoNumber());
					PushMessage.push2Xiao(json2);
					json1.put("taskId", xiaoitask.getId());
				}else{
					code=Plan.deleteFalse;
					message="删除计划任务失败!";
				}
			}else{
				code=FamilyCode.noExistBean;
				message="没有该家庭组";
			}
			}else{
				code=Plan.noExistBean;
				message="没有该任务数据!";
			}
		}
		
	  	json.put("code", code);
		json.put("message", message);
		logger.info("删除定时任务的出参:"+json);
		out.print(json);
		return null;
	}
	
	/**
	 * 查询定时任务
	 * @param taskId  定时任务ID
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String getXiaoitaskBytaskId() throws IOException{
		success =true;
		message =null;
		code=OK;
		JSONObject json=new JSONObject();
		JSONObject json1=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("查询定时任务的入参");
	    String taskId=request.getParameter("taskId");
		Xiaoitask xiaoitask=new Xiaoitask();
	    
		if(XATools.isNull(taskId)){
			code=Plan.emptyId;
			success=false;
			message="任务ID不能为空!";
		}		
		if(success){
			xiaoitask=xiaoitaskService.selectXiaoitaskById(Long.parseLong(taskId));
			if(xiaoitask!=null){
				json1.put("taskId", xiaoitask.getId());
				json1.put("trigger", xiaoitask.getTriggerTime());
				json1.put("create", xiaoitask.getCreationTime());
				json1.put("things", xiaoitask.getThings());
				json1.put("object", xiaoitask.getObject());
				json1.put("rules", xiaoitask.getRules());
				json1.put("orders", xiaoitask.getOrders());
			}else{
				code=Plan.noExistBean;
				success=false;
				message="没有该任务数据！";
			}
		//	json1=(JSONObject) JSON.toJSON(xiaoitask);
		}
	
	  	json.put("code", code);
		json.put("message", message);
		json.put("result", json1);
		logger.info("查询定时任务的出参:"+json);
		out.print(json);
		return null;
	}
	
	
	
	/**
	 * 查询定时任务
	 * @param groupNumber  家庭组编号
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String getXiaoitaskByGroupId() throws IOException{
		success =true;
		message =null;
		code=OK;
		JSONObject json=new JSONObject();
		JSONObject json1=new JSONObject();
		JSONArray array=new JSONArray();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("查询定时任务的入参");
	    String groupNumber=request.getParameter("groupNumber");
		List<Xiaoitask> xiaoitasklist=new ArrayList<Xiaoitask>();
	    
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
		}		
		if(success){
			Familygroup  family=familyDao.getFamilygroupByNumber(Integer.parseInt(groupNumber));
			if(family!=null){
				xiaoitasklist=xiaoitaskService.selectXiaoitaskByGroupId(family.getGroupId());
			 if(!XATools.isNull(xiaoitasklist)){
			  for(Xiaoitask xiaoitask:xiaoitasklist){
				json1.put("trigger", xiaoitask.getTriggerTime());
				json1.put("create", xiaoitask.getCreationTime());
				json1.put("things", xiaoitask.getThings());
				json1.put("object", xiaoitask.getObject());
				json1.put("rules", xiaoitask.getRules());
				json1.put("orders", xiaoitask.getOrders());
				json1.put("taskId", xiaoitask.getId());
				array.add(json1);
			 //	json1=(JSONObject) JSON.toJSON(xiaoitask);
			  }
			}
			
		   }
		}
	
	  	json.put("code", code);
		json.put("message", message);
		json.put("result", array);
		logger.info("查询定时任务的出参:"+json);
		out.print(json);
		return null;
	}
	
	
	
	public String test(){
		for(int i=1;i<101;i++){
			System.out.println("i:"+i);
			JSONObject json2=new JSONObject();
			json2.put("key", 10*i);
			json2.put("code", i);
			json2.put("xiaoNumber", "0030000100009702");
			PushMessage.push2Xiao(json2);
			System.out.println("json2:"+json2);
		}
		return null;
	}
	
	

	
}
