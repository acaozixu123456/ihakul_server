package com.xiaoai.mina.service.session;

import java.util.Collection;

/**
 * 客户端的session管理接口
 * 可自行实现此接口管理session
 * @author Administrator
 *
 */
public interface SessionManager {
	/**
	 * 添加新的session
	 */
	public void addSession(String account,XiaoaiSession session);
	
	/**
	 * 修改session
	 */
	public void updateSession(XiaoaiSession session);
	
	/**
	 * 
	 * @param account 客户端session的 key 一般可用 用户账号来对应session
	 * @return
	 */
	XiaoaiSession getSession(String account);
	
	/**
	 * 获取所有session
	 * @return
	 */
	public Collection<XiaoaiSession> getSessions();
	
    
    /**
	 * 删除session
	 * @param session
	 */
    public void  removeSession(XiaoaiSession session);
    /**
  	 * 删除session
  	 * @param account
  	 */
    public void  removeSession(String account);
    
    /**
     * 
     * @param account 客户端session的 key 一般可用 用户账号来对应session
	 * 
     */
    public void  setInvalid(String account);
    
    /**
	 * session是否存在
	 * @param session
	 */
    public boolean containsXiaoaiSession(String account);
}
