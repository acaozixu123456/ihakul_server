package com.xiaoai.mina.service.push;

import java.util.Collection;

import com.xiaoai.mina.model.Message;
import com.xiaoai.mina.service.session.DefaultSessionManager;
import com.xiaoai.mina.service.session.XiaoaiSession;


/**
 * 消息发送实现类
 * @author Administrator
 *
 */
public class DefaultMessagePusher implements XiaoaiMessagePusher{
	
	
	
	private DefaultSessionManager sessionManager;
	
	 
    public void setSessionManager(DefaultSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
    /**
     * 向用户发送消息
     */
	public void pushMessageToUser(Message message) {
		
		Collection<XiaoaiSession> sessions=sessionManager.getSessions();
		if(sessions == null) return;
		for (XiaoaiSession xiSession : sessions) {
			if(xiSession !=null && xiSession.isConnected()){
				System.out.println("发送的消息:"+message);
				//推送消息
				xiSession.write(message);
	 			 
			}
		}
	}
	
}
