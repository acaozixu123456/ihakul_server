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
	
	private boolean success ;         //成功、失败标记
	@Override 
	public SentBody process(XiaoaiSession newSession, SentBody body) {
		SentBody reply=new SentBody();
		JSONObject json=new JSONObject();
		DefaultSessionManager sessionManager=(DefaultSessionManager) ContextHolder.getBean("XiaoaiSessionManage");
		try {
			String account=(String) body.getData().get(XiaoaiConstant.SESSION_KEY);      //接收信息
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
			if(success){
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
			}else{
				reply.setCode(json2.getInteger("code"));
				reply.setKey(account);
			}
//				reply.setCode(XiaoaiConstant.ReturnCode.CODE_200);
				reply.setCode(XiaoaiMessage.OK);
			//	reply.setMessage(account+"，账号绑定成功");
			} catch (Exception e) {
			//	reply.setCode(XiaoaiConstant.ReturnCode.CODE_500);
				reply.setCode(XiaoaiMessage.Other);
				e.printStackTrace();
			}
		
		logger.debug("bind :account:" +body.get(XiaoaiConstant.SESSION_KEY)+"-----------------------------" +reply.getCode());
		return reply;
	}

	public JSONObject insertUpdateXiaoai(JSONObject json){
		Xiaoi xiao1=new Xiaoi();
		JSONObject json1=new JSONObject();
		String xiaoNumber=json.getString("xiaoNumber");
		String xiaoName=json.getString("xiaoName");
		String groupNumber=json.getString("groupNumber");
		String xiaoIp=json.getString("xiaoIp");
		int versionNumber=json.getInteger("versionNumber");
	//	xiao1=JSON.toJavaObject(json, Xiaoi.class);
		String xiaoType=xiaoNumber.substring(xiaoNumber.length()-1, xiaoNumber.length());
		Familygroup	family=familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
		   if(family!=null){
			   if(versionNumber>family.getVersionNumber()){ //如果终端的版本号大于服务端
				   family.setVersionNumber(versionNumber);  
				   familyService.updateFamily(family);
			   }
				xiao1=xiaoiService.selectXiaoiByNumber(xiaoNumber);
				Xiaoi xiao=new Xiaoi();
				xiao.setXname(xiaoName);
				xiao.setXiaoNumber(xiaoNumber);
				xiao.setXiaoType(Integer.parseInt(xiaoType));
				xiao.setOnlineState(1);
				xiao.setFamilygroup(family);
				xiao.setXiaoIp(xiaoIp);
				xiao.setActivationTime(XATools.getNowTime());
				xiao.setVolume(50); //设置默认音量
			 if(xiao1==null){ //如果查不到，说明已经被删除了;否则，更新小艾	
				 success=false;
				 json1.put("code",XiaoaiMessage.XiaoiCode.noExistBean);
			//	 xiaoiService.insertXiaoi(xiao);
			  }else{
				  xiao.setXid(xiao1.getXid());
				  xiaoiService.updateXiaoi(xiao);
				  success=true;
				  json1.put("code", XiaoaiMessage.OK);
			  }
		 }else{
			 json1.put("code", XiaoaiMessage.FamilyCode.noExistBean);
		 }
		return json1;	
	}

}