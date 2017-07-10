package com.xiaoai.mina.service.handler;

import java.net.InetAddress;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.RequestHandler;
import com.xiaoai.mina.service.constant.XiaoaiConstant;
import com.xiaoai.mina.service.session.DefaultSessionManager;
import com.xiaoai.mina.service.session.XiaoaiSession;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.ContextHolder;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
/**
 * 账号绑定实现
 * @author Administrator
 *
 */
public class BindHandler implements RequestHandler{
	private final Logger logger=Logger.getLogger(BindHandler.class);
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	@Resource(name="xiaoiService")
	private IXiaoiService xiaoiService;
	
	@SuppressWarnings("unused")
	private boolean success ;         //成功、失败标记
	@Override 
	public SentBody process(XiaoaiSession newSession, SentBody body) {
		SentBody reply=new SentBody();
		JSONObject json=new JSONObject();
		DefaultSessionManager sessionManager=(DefaultSessionManager) ContextHolder.getBean("XiaoaiSessionManage");
		try {
			String account=(String) body.getData().get(XiaoaiConstant.SESSION_KEY);      //接收信息
			logger.info("body:"+body);
			newSession.setXiaoNumber(account);
			newSession.setGid(UUID.randomUUID().toString());
			newSession.setHost(InetAddress.getLocalHost().getHostAddress());
			newSession.setAttribute(XiaoaiConstant.SESSION_KEY,account);
			json.put("xiaoNumber", account);
			json.put("groupNumber", body.getData().get("groupNumber"));
			json.put("xiaoIp", body.getData().get("xiaoIp"));
			json.put("xiaoName", body.getData().get("xiaoName"));
			json.put("versionNumber", body.getData().get("versionNumber"));
			JSONObject json2=insertUpdateXiaoai(json); //新建连接时修改小艾状态为在线			
			//if(success){
			if(json2.getString("code").equals("0")){
			//第一次设置心跳时间为登录时间
			newSession.setBindTime(System.currentTimeMillis());
			newSession.setHeartbeat(System.currentTimeMillis());
			/**
			 * 由于客户端断线服务端可能会无法获知的情况，客户端重连时，需要关闭旧的连接
			 */
			XiaoaiSession oldSession=sessionManager.getSession(account);		
			   //如果是账号已经在另一台终端登录。则让另一个终端下线
				if(oldSession!=null&&!oldSession.equals(newSession))
				{						 
						oldSession.removeAttribute(XiaoaiConstant.SESSION_KEY);
						reply.setCode(XiaoaiMessage.UnknownCode.Code_999);
						reply.setKey(account);
						if(!oldSession.isLocalhost())
						{							
							/*
							判断当前session是否连接于本台服务器，如不是发往目标服务器处理
							MessageDispatcher.execute(msg, oldSession.getHost());
							*/
						}else
						{
							oldSession.write(reply);
							oldSession.close(true);
							oldSession = null;
						}
						oldSession = null;
				}
				if(oldSession==null)
				{
					sessionManager.addSession(account, newSession);
				}
				reply.setCode(XiaoaiMessage.OK);
				logger.info("终端绑定成功："+account);
				logger.debug("bind :account:" +body.get(XiaoaiConstant.SESSION_KEY)+"-----------------------------" +reply.getCode());
			}else{
				reply.setCode(json2.getInteger("code"));
				reply.setKey(account);
				logger.info("终端绑定失败！："+account+"原因是："+json2.getInteger("code"));
			}
			
			} catch (Exception e) {
				reply.setCode(XiaoaiMessage.Other);
				e.printStackTrace();
			}
		
		return reply;
	}

	public JSONObject insertUpdateXiaoai(JSONObject json){
		logger.info("绑定终端--插入或更新小艾,入参为："+json);
		Xiaoi xiao1=new Xiaoi();
		JSONObject json1=new JSONObject();
		String xiaoNumber=json.getString("xiaoNumber");
		String xiaoName=json.getString("xiaoName");
		String groupNumber=json.getString("groupNumber");
		String xiaoIp=json.getString("xiaoIp");
		String versionNumber=json.getString("versionNumber");
		if(XATools.isNull(xiaoNumber)){
			json1.put("code", XiaoaiMessage.XiaoiCode.emptyId);
			return json1;
		}
		String xiaoType=xiaoNumber.substring(xiaoNumber.length()-1, xiaoNumber.length());
		Familygroup	family=familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
		   if(family!=null){
			   //根据终端编号获得小艾（包括state=2）
				xiao1=xiaoiService.selectXiaoiByNumberAll(xiaoNumber);
			 if(xiao1==null){ //如果查不到，说明已经被删除了;否则，更新小艾
				 	Xiaoi xiao=new Xiaoi();
					xiao.setXname(xiaoName);
					xiao.setXiaoNumber(xiaoNumber);
					xiao.setXiaoType(Integer.parseInt(xiaoType));
					xiao.setState(1);
					xiao.setFamilygroup(family);
					xiao.setXiaoIp(xiaoIp);
					xiao.setActivationTime(XATools.getNowTime());
					xiao.setVolume(50); //设置默认音量
					//更新versionNumber
					family.setVersionNumber(Integer.parseInt(versionNumber));
					//更新家庭组
					familyService.updateFamily(family);
				 success=false;
				 json1.put("code", XiaoaiMessage.OK);
				 xiaoiService.insertXiaoi(xiao);
			  }else{
				  if(xiao1.getState()==2){
					  /*首选方案（已删除小艾不允许绑定）*/
					  //小艾已经被删除
					  //success=false;
					  //json1.put("code", XiaoaiMessage.deletedXiaoi);
					  
					  reloadXiaoi(xiao1, json1, xiaoNumber, xiaoName, xiaoIp,
							xiaoType, family,versionNumber);
				  }else if(xiao1.getState()==3){
					  /*首选方案（已替换小艾不允许绑定）*/
					  //小艾已经被替换
					  //success=false;
					 // json1.put("code", XiaoaiMessage.changedXiaoi);
					  
					  reloadXiaoi(xiao1, json1, xiaoNumber, xiaoName, xiaoIp,
							xiaoType, family,versionNumber);
				  }else{
					  reloadXiaoi(xiao1, json1, xiaoNumber, xiaoName, xiaoIp,
							xiaoType, family,versionNumber);
				  }
				  
			  }
		 }else{
			 json1.put("code", XiaoaiMessage.FamilyCode.noExistBean);
		 }
		return json1;	
	}

	/**重新设置小艾
	 * @param xiao1
	 * @param json1
	 * @param xiaoNumber
	 * @param xiaoName
	 * @param xiaoIp
	 * @param xiaoType
	 * @param family
	 */
	private void reloadXiaoi(Xiaoi xiao1, JSONObject json1, String xiaoNumber,
			String xiaoName, String xiaoIp, String xiaoType, Familygroup family,String versionNumber) {
		xiao1.setXname(xiaoName);
		  xiao1.setXiaoNumber(xiaoNumber);
		  xiao1.setXiaoType(Integer.parseInt(xiaoType));
		  xiao1.setState(1);
		  xiao1.setFamilygroup(family);
		  xiao1.setXiaoIp(xiaoIp);
		  xiao1.setActivationTime(XATools.getNowTime());
		  xiao1.setVolume(50); //设置默认音量
		  xiaoiService.updateXiaoi(xiao1);
		//更新versionNumber
			family.setVersionNumber(Integer.parseInt(versionNumber));
			//更新家庭组
			familyService.updateFamily(family);
		  success=true;
		  json1.put("code", XiaoaiMessage.OK);
	}

}
