package com.xiaoai.mina.service.push;

import com.xiaoai.mina.model.Message;



/**
 * 消息发送实现接口
 * @author Administrator
 *
 */
public interface XiaoaiMessagePusher {
	/**
	 * 向用户发送消息
	 * @param message 
	 */
	public void pushMessageToUser(Message message);
}
