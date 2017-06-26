package com.xiaoai.mina.service.constant;
/**
 * 常量
 * @author Administrator
 *
 */
public interface XiaoaiConstant {
public static class ReturnCode{
		
		public static String CODE_404 ="404";
		
		public static String CODE_403 ="403";
		
		public static String CODE_405 ="405";
		
		/**成功 */
		public static String CODE_200 ="200";
		
		public static String CODE_206 ="206";
		
		/**异常 */
		public static String CODE_500 ="500";
		
		
	}
	
	public static String UTF8="UTF-8";
	
	public static byte  MESSAGE_SEPARATE='\b';
	
	
	public static int  CIM_DEFAULT_MESSAGE_ORDER=1;
	
	/** 接收的消息 */
//	public static final String SESSION_KEY ="account";
	public static final String SESSION_KEY ="xiaoNumber";
	
	public static final String HEARTBEAT_KEY ="heartbeat";
	
	
	/**
	 * FLEX 客户端socket请求发的安全策略请求，需要特殊处理，返回安全验证报文
	 */
	public static final String FLEX_POLICY_REQUEST ="<policy-file-request/>";
	
	public static final String FLEX_POLICY_RESPONSE ="<?xml version=\"1.0\"?><cross-domain-policy><site-control permitted-cross-domain-policies=\"all\"/><allow-access-from domain=\"*\" to-ports=\"*\"/></cross-domain-policy>\0"; 
	
	/**
	 * 服务端心跳请求命令  
	 */
	public static final String CMD_HEARTBEAT_REQUEST="hb_request";
	/**
	 * 客户端心跳响应命令  
	 */
	public static final String CMD_HEARTBEAT_RESPONSE ="hb_response"; 
/*	*//**
	 * 服务端心跳请求命令  
	 *//*
	public static final String CMD_HEARTBEAT_REQUEST="cmd_server_hb_request";
	*//**
	 * 客户端心跳响应命令  
	 *//*
	public static final String CMD_HEARTBEAT_RESPONSE ="cmd_client_hb_response"; 
*/
	

	
   
   public static class SessionStatus{
		
		public static int STATUS_OK =0;
		
		public static int STATUS_CLOSED =1;
		
	}
   
   public static class MessageType{
		
	    //用户会 踢出下线消息类型
		public static String TYPE_999 ="999";
		
   }


}
