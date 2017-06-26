package com.xiaoai.mina.service.handler;


import org.apache.log4j.Logger;

import com.xiaoai.mina.model.ReplyBody;
import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.RequestHandler;
import com.xiaoai.mina.service.constant.XiaoaiConstant;
import com.xiaoai.mina.service.session.DefaultSessionManager;
import com.xiaoai.mina.service.session.XiaoaiSession;
import com.xiaoai.util.ContextHolder;
import com.xiaoai.util.XiaoaiMessage;
/**
 * 主动推送消息
 * @author Administrator
 *
 */

public class PushOnlinMessageHandler implements RequestHandler {
	private final Logger logger=Logger.getLogger(PushOnlinMessageHandler.class);

	

	public SentBody process(XiaoaiSession session, SentBody sent) {
		SentBody reply=new SentBody();
		String account=(String) sent.getData().get(XiaoaiConstant.SESSION_KEY);//得到绑定账号
		DefaultSessionManager sessionManager=(DefaultSessionManager) ContextHolder.getBean("XiaoaiSessionManage");
		XiaoaiSession xaSession=sessionManager.getSession(account);//通过绑定账号找到session集合
		if(xaSession !=null){ //如果有这个账号 就进行转发
			sent.remove(XiaoaiConstant.SESSION_KEY);
			reply.setKey(sent.getKey());
			reply.setData(sent.getData());
			reply.setCode(XiaoaiMessage.OK); 
			xaSession.write(reply); //转发
			logger.info("推送的消息是:"+reply.toString());
		}
		return reply;
	}
}
