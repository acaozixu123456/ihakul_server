package com.xiaoai.mina.service.handler;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import com.xiaoai.mina.model.ReplyBody;
import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.RequestHandler;
import com.xiaoai.mina.service.constant.XiaoaiConstant;
import com.xiaoai.mina.service.push.SessionMap;
import com.xiaoai.mina.service.session.XiaoaiSession;
import com.xiaoai.util.XiaoaiMessage;




/**
 *  I/O 处理器
 * 客户端请求的入口，所有请求都首先经过它分发处理
 * 业务逻辑实现
 */
@Component("sercixeMainHandler")
public class ServiceMainHandler extends IoHandlerAdapter{
	protected final Logger logger = Logger.getLogger(ServiceMainHandler.class);
	
    //本地handler请求
	private HashMap<String, RequestHandler> handlers = new HashMap<String, RequestHandler>();
			
	//出错时
	@Override
	public void exceptionCaught(IoSession session, Throwable cause){
		logger.error("exceptionCaught()... from "+session.getRemoteAddress());
		logger.error(cause);
		cause.printStackTrace();
	}
	//接收到消息时
	@Override
	public void messageReceived(IoSession session,Object message){		
//		/**
//		 * flex 客户端安全策略请求，需要返回特定报文
//		 */
//		if(XiaoaiConstant.FLEX_POLICY_REQUEST.equals(message.toString()))
//		{
//			session.write(XiaoaiConstant.FLEX_POLICY_RESPONSE);
//			return ;
//		}
		
//		logger.info("服务端接收到的消息..."+message);
//		logger.info("服务端接收到的消息的IP地址:"+((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress());
		if(message instanceof SentBody){
			XiaoaiSession xsession=new XiaoaiSession(session);
//			ReplyBody rb=new ReplyBody();
			SentBody rb=new SentBody();
			SentBody sent=(SentBody) message;
//			if(! sent.get("pass").equals("pass")){
//				session.close(true);
//			}
//			logger.info("连接的会话ID:"+xsession.getNid()+","+sent.getData().get(XiaoaiConstant.SESSION_KEY));
			String key=sent.getKey();
			//根据key的不同调用不同的handler
			//key 在spring中配置  为 client_descriptor 为绑定   为client_logout 断开
			RequestHandler rhandler=handlers.get(key);
			if(rhandler==null){//如果没有这个handler
				rb.setCode(XiaoaiMessage.Other);
		//		rb.setCode(XiaoaiConstant.ReturnCode.CODE_405);
		//		rb.setCode("KEY ["+key+"] 服务端未定义");
			}else{//有的话
				rb=rhandler.process(xsession, sent);
			}
			if(rb !=null){
				rb.setKey(key);
				xsession.write(rb);
			//	logger.info("-----------------------process done. reply: " + rb.toString());
			}
			//保存客户端的会话session
//			  SessionMap sessionMap = SessionMap.newInstance();
//			  sessionMap.addSession(sent.getData().get("account").toString(), session);
//			  logger.info("key:"+sent.getData().get("account")+",session:"+session);
		}
	}
	
	//发送消息
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
//	 	logger.info("服务端成功发送数据 :"+message);
//	 	session.close(); //发送成功后主动断开与客户端的连接 实现短连接
      //  logger.info("服务端发送信息成功...");
	 	//logger.debug("服务端成功发送数据 :"+message);
	 	logger.info("服务端成功发送数据 :"+message);
	 	//IoHandler handler = session.getHandler();
	 	
	    }
	
	//建立连接时
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		 	InetSocketAddress sa=(InetSocketAddress)session.getRemoteAddress();
			String address=sa.getAddress().getHostAddress(); //访问的ip
			session.setAttribute("address", address);
			//将连接的客户端ip保存到map集合中
			SentBody body=new SentBody();
			body.getData().put("address", address);
			logger.info("访问的ip:"+address);
	    }
	//关闭连接时   
	@Override
	public void sessionClosed(IoSession session) throws Exception {
			XiaoaiSession xiaoSession=new XiaoaiSession(session);
			
			logger.debug("sessionClosed()... from "+xiaoSession.getRemoteAddress());
			try {
				RequestHandler hand=handlers.get("client_closs");
//				logger.info("xiaoSession:"+xiaoSession);
//				logger.info("连接的会话ID:"+xiaoSession.getNid());
//				logger.info("xiaoSession.containsAttribute():"+xiaoSession.containsAttribute("account"));
//				logger.info("hand:"+hand);
//				logger.info("xiaoSession.getAttribute:"+xiaoSession.getAttribute("account"));
				if(hand !=null && xiaoSession.containsAttribute(XiaoaiConstant.SESSION_KEY)){
					hand.process(xiaoSession, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.close(true);  
			logger.info("连接关闭");
	    }
	//空闲时
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//		XiaoaiSession xsession=new XiaoaiSession(session);
//		logger.info("服务器空闲时..."+ new Date());
//		logger.info("服务器空闲时的session:"+ xsession);
		logger.debug("sessionIdle()... from "+session.getRemoteAddress());
	    }
	 //打开连接时   
	@Override
	 public void sessionOpened(IoSession session) throws Exception {
		 	logger.info("开启连接...");
	    }
	public HashMap<String, RequestHandler> getHandlers() {
		return handlers;
	}
	public void setHandlers(HashMap<String, RequestHandler> handlers) {
		this.handlers = handlers;
	}
	
	 
	 
	 
}
