package com.xiaoai.mina.service.filter;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.xiaoai.mina.service.constant.XiaoaiConstant;
/**
 * 心跳协议的实现类
 * @author Administrator
 *
 */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{
	private final Logger LOG=Logger.getLogger(KeepAliveMessageFactoryImpl.class);
	/**
	 * 客户端心跳响应命令
	 */
	private static  String HEARRESPONSE=XiaoaiConstant.CMD_HEARTBEAT_RESPONSE; 
	/**
	 * 服务端心跳请求命令
	 */
	private static  String HEARREQUEST=XiaoaiConstant.CMD_HEARTBEAT_REQUEST;
	
	/**
	 *  被动型心跳机制无请求  因此直接返回null
	 */
	@Override
	public Object getRequest(IoSession session) {
//		LOG.warn("请求预设信息:"+HEARREQUEST);
		return HEARREQUEST;
	}

	/**
	 *  根据心跳请求request 反回一个心跳反馈消息 non-nul 
	 */
	@Override
	public Object getResponse(IoSession session, Object message) {
//		 LOG.warn("响应预设信息: " + HEARRESPONSE);  
	        /** 返回预设语句 */  
	      return HEARRESPONSE;  
	}

	/**
	 * 判断是否心跳请求包  是的话返回true 
	 */
	@Override
	public boolean isRequest(IoSession session, Object message) {
//		LOG.warn("message:"+ message);
//		LOG.warn("请求心跳包信息: " + HEARREQUEST+",访问的IP地址:"+((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress());   
        return message.equals(HEARREQUEST); 
	}

	/**
	 * 由于被动型心跳机制，没有请求当然也就不关注反馈 因此直接返回false
	 */
	@Override
	public boolean isResponse(IoSession session, Object message) {
//		LOG.warn("message:"+ message);
//		LOG.warn("响应心跳包信息: " + HEARRESPONSE+",访问的IP地址:"+((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress());   
	     return message.equals(HEARRESPONSE);
	   }

	
	
}
