package com.xiaoai.web.action.other;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import com.xiaoai.dao.IUsersDao;
import com.xiaoai.dao.IXiaoiSMSDao;
import com.xiaoai.dao.IXiaoiSMSMessageDao;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.XiaoiSMS;
import com.xiaoai.entity.XiaoiSMSMessage;
import com.xiaoai.util.MyHttpRequest;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;

/**
 * @author ZERO
 * @version 2017-4-18 下午2:54:45
 * 短信平台交互
 */
@Controller("httpSms")
public class HttpSms extends XiaoaiMessage{
	
	//记录日志
  public static Logger log=Logger.getLogger(XiaoaiMessage.class);
	
	private boolean success ;         //成功、失败标记
	private  String message ;       //信息
	private int code ;				//标记
	@Resource(name="xiaoiSMSDao")//注入类  
	private IXiaoiSMSDao xiaoiSMSDao;
	@Resource(name="xiaoSMSMessageDao")//注入类  
	private IXiaoiSMSMessageDao xiaoiSMSMessageDao;
	
	@Resource(name="usersDao")
	private IUsersDao usersDao;
	
	//单条短信发送
	public String oneTOSMS() throws IOException{
		String ms,content,nowTime="",startTime="",endTime="",url,param;
		int minuteTime=0;
		message =null;      
		success =true;
		code=OK;
		JSONObject json=new JSONObject();
		JSONObject json2=new JSONObject();
		JSONArray array=new JSONArray();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();
		MyRequest.printParameterNames("接收入参");
		String userPhoneNumber=request.getParameter("userPhoneNumber");  //用户电话
		String random=XATools.random(6);
		request.setAttribute("random", random);
		if(XATools.isNull(userPhoneNumber)){
			code=UserCode.emptyPhoneNum;
			message="手机号不能为空";
			success=false;
		}
		if(success){
		XiaoiSMS xs= xiaoiSMSDao.selectXiaoiSMSByid(1);		
		XiaoiSMSMessage xsm= xiaoiSMSMessageDao.selectXiaoiSMSMessageByid(1);		
		url=xs.getUrl();
		minuteTime=xsm.getMinuteTime();
	//	startTime=XATools.getNowTime("YYYYMMDDHHMMSS");
	//	endTime=XATools.getTimeAddmi(xsm.getMinuteTime(), startTime, "YYYYMMDDHHMMSS");
		ms=xsm.getHeadMessage()+xsm.getYzMessage()+random+xsm.getMessageone()+minuteTime+xsm.getMessagetwo();
		content=URLEncoder.encode(ms,"GBK");
//		http://hsms.guodulink.net:9007/QxtSms/QxtFirewall?OperID=ceshfj1&OperPass=n9JUBHgw&SendTime=&ValidTime=&DesMobile=15827141590&Content=%D6%D0%CE%C4%B6%CC%D0%C5abc
		param="OperID="+xs.getOperID()+"&OperPass="+xs.getOperPass()+"&SendTime="
			+startTime+"&ValidTime="+endTime+"&DesMobile="+userPhoneNumber+"&Content="+content+"&AppendID="+xs.getAppendID();
		log.info("url:"+url);
		String result = MyHttpRequest.sendGet(url, param,"utf-8");
		nowTime=XATools.getNowTime();
		JSONObject result2=XATools.xml2JSON(result);
		if(!"03".equals(result2.get("code").toString())){
			code=OtherCode.otherFalse;
			message="调用短信平台接口失败!";
		}
		 json2.put("random",random);
		 json2.put("minuteTime",minuteTime);
		 json2.put("nowTime", nowTime);
		 array.add(json2);
		}
	    json.put("code", code);
	    json.put("message", message);
	    json.put("result", array);
	    log.info("返回的数据:"+json);
		out.print(json.toString());
		return null;
	}   
	
	
	//多条短信发送
	public String moreTOSMS() throws IOException{
		String content,url,param;
		message =null;      
		success =true;
		code=OK;
		StringBuffer ms=new StringBuffer();
		JSONObject json=new JSONObject();
		JSONArray array=new JSONArray();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();
		MyRequest.printParameterNames("多条短信发送的入参");
		String messageId=request.getParameter("messageId");  //群发消息id
		if(XATools.isNull(messageId)){
			code=OtherCode.emptyId;
			message="OtherCode";
			success=false;
		}
		if(success){
		XiaoiSMS xs= xiaoiSMSDao.selectXiaoiSMSByid(1);		
		XiaoiSMSMessage xsm= xiaoiSMSMessageDao.selectXiaoiSMSMessageByid(Integer.parseInt(messageId));		
		List<Users> uslist= usersDao.selectUsersAll();
		StringBuffer sb=new StringBuffer();
		String userPhoneNumber="";
		if(!XATools.isNull(uslist)){
			for(Users us:uslist ){
				if(sb.length()>0){
					sb.append(","+us.getUserPhoneNumber());
				}else{
					sb.append(us.getUserPhoneNumber());
				}
			}
		}
		userPhoneNumber=sb.toString();
		url=xs.getUrl();
		if(!XATools.isNull(xsm.getHeadMessage())){
			ms.append(xsm.getHeadMessage());
		}
        if(!XATools.isNull(xsm.getMessageone())){
        	ms.append(xsm.getMessageone());
		}
        if(!XATools.isNull(xsm.getMessagetwo())){
        	ms.append(xsm.getMessagetwo());
        }
		content=URLEncoder.encode(ms.toString(),"GBK");
		param="OperID="+xs.getOperID()+"&OperPass="+xs.getOperPass()+
			"&DesMobile="+userPhoneNumber+"&Content="+content+"&AppendID="+xs.getAppendID();
		log.info("url:"+url);
		String result = MyHttpRequest.sendGet(url, param,"utf-8");
		JSONObject result2=XATools.xml2JSON(result);
		log.info("短信平台返回参数:"+result2);
		if(!"01".equals(result2.get("code").toString())){
			code=OtherCode.otherFalse;
			message="调用短信平台接口失败!";
		}
		}
	    json.put("code", code);
	    json.put("message", message);
	    json.put("result", array);
	    out.print(json.toString());
	    log.info("多条短信发送的出参:"+json);
		return null;
	}   

}
