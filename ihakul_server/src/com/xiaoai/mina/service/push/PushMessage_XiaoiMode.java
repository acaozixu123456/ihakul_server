package com.xiaoai.mina.service.push;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.constant.XiaoaiConstant;
import com.xiaoai.mina.service.session.DefaultSessionManager;
import com.xiaoai.mina.service.session.XiaoaiSession;
import com.xiaoai.util.ContextHolder;

/**
 * @author ZERO
 * @Data 2017-6-22 上午10:08:21
 * @Description 消息推送
 */
public class PushMessage_XiaoiMode {
	private static final Logger logger=Logger.getLogger(PushMessage_XiaoiMode.class);
	
	//消息转发
	public static boolean push2Xiao(JSONObject json,@SuppressWarnings("rawtypes") HashMap map){
		SentBody sent=new SentBody();
		DefaultSessionManager sessionManager=(DefaultSessionManager) ContextHolder.getBean("XiaoaiSessionManage");
		XiaoaiSession xaSession=sessionManager.getSession(json.getString(XiaoaiConstant.SESSION_KEY));//通过绑定账号找到session集合
		if(xaSession !=null){ //如果有这个账号 就进行转发
			sent.setKey(json.getString("key"));
			sent.setCode(json.getInteger("code")); 
			sent.setData(map);
			xaSession.write(sent); //转发
			
			logger.info("推送的消息(情景模式管理)是:"+sent.toString());
			return true;
		}
		
		return false;	
	}
	
}
