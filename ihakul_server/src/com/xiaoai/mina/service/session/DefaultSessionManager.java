package com.xiaoai.mina.service.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.xiaoai.mina.service.constant.XiaoaiConstant;








/**
 * 自带默认 session管理实现， 各位可以自行实现 AbstractSessionManager接口来实现自己的 session管理
 *	服务器集群时 须要将XiaoaiSession 信息存入数据库或者nosql 等 第三方存储空间中，便于所有服务器都可以访问
 * @author Administrator
 *
 */
public class DefaultSessionManager implements SessionManager{

	 private static HashMap<String,XiaoaiSession> sessions =new  HashMap<String,XiaoaiSession>();
	    
	
	 private static final AtomicInteger connectionsCounter = new AtomicInteger(0);

	 

	
	public void addSession(String account, XiaoaiSession session) {
		if(session !=null){
			
			sessions.put(account, session);
			connectionsCounter.incrementAndGet();
		}
		
	}

	public void updateSession(XiaoaiSession session) {
		
//		sessions.put(session.getAccount(), session);
		sessions.put(session.getXiaoNumber(), session);
		
	}

	
	public XiaoaiSession getSession(String account) {
		
		return sessions.get(account);
	}

	
	public Collection<XiaoaiSession> getSessions() {
		
		return sessions.values();
	}

	
	public void removeSession(XiaoaiSession session) {
		
		sessions.remove(session.getAttribute(XiaoaiConstant.SESSION_KEY));
		
	}

	
	public void setInvalid(String account) {
		
		sessions.get(account).setStatus(1);
		
	}

	
	public boolean containsXiaoaiSession(String account) {
		
		return sessions.containsKey(account);
	}

	
	public void removeSession(String account) {
		
		sessions.remove(account);
		
	}
	//获取消息
	 public String getAccount(XiaoaiSession ios)
	    {
	    	 if(ios.getAttribute(XiaoaiConstant.SESSION_KEY)==null)
	    	 {
	    		for(String key:sessions.keySet())
	    		{
	    			if(sessions.get(key).equals(ios) || sessions.get(key).getGid()==ios.getGid())
	    			{
	    				System.out.println("------key-----"+key);
	    				return key;
	    			}
	    		}
	    	 }else
	    	 {
	    	    return ios.getAttribute(XiaoaiConstant.SESSION_KEY).toString();
	    	 }
	    	 
	    	 return null;
	    }
}
