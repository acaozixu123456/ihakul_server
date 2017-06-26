package com.xiaoai.mina.service.push;

import com.xiaoai.mina.model.Message;






public class SystemMessagePusher extends DefaultMessagePusher{
	/**
	 * Constructor.
	 */
	public SystemMessagePusher() {
		super();
	}
	
	
	public void pushMessageToUser(Message MessageMO){
		
		MessageMO.setSender("system");
		super.pushMessageToUser(MessageMO);
		
	}
}
