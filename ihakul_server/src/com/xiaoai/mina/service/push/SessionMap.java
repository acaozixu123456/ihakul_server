package com.xiaoai.mina.service.push;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;


/**
 *  单例工具类，保存所有mina客户端连接
 */
public class SessionMap {
	
	private final static Log log = LogFactory.getLog(SessionMap.class);
	
	private static SessionMap sessionMap = null;
			
	private Map<String, IoSession>map = new HashMap<String, IoSession>();
	
	
	//构造私有化 单例
	private SessionMap(){}
	
	
	/**
	 * 获取唯一实例
	 */
	public static SessionMap newInstance(){
		log.debug("SessionMap单例获取---");
		if(sessionMap == null){
			sessionMap = new SessionMap();
		}
		return sessionMap;
	}
	
	
	/**
	 *  保存session会话
	 
	 */
	public void addSession(String key, IoSession session){
		log.debug("保存会话到SessionMap单例---key=" + key);
		this.map.put(key, session);
	}
	
	/**
	 *  根据key查找缓存的session
	 */
	public IoSession getSession(String key){
		log.debug("获取会话从SessionMap单例---key=" + key);
		return this.map.get(key);
	}
	
	/**
	 *  发送消息到客户端
	 */
	public void sendMessage(String[] keys, Object message){
		for(String key : keys){
			IoSession session = getSession(key);			
			log.debug("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
			if(session == null){
				return;
			}
			session.write(message);
			
		}
	}
	
	
	

}