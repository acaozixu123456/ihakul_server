package com.xiaoai.mina.service.handler;



import com.xiaoai.mina.model.ReplyBody;

import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.RequestHandler;
import com.xiaoai.mina.service.constant.XiaoaiConstant;
import com.xiaoai.mina.service.session.DefaultSessionManager;
import com.xiaoai.mina.service.session.XiaoaiSession;
import com.xiaoai.util.ContextHolder;
/**
 * 退出连接实现
 * @author Administrator
 *
 */
public class LogoutHandler implements RequestHandler {

	@Override
	public SentBody process(XiaoaiSession session, SentBody message) {

		DefaultSessionManager sessionManager  =  ((DefaultSessionManager) ContextHolder.getBean("XiaoaiSessionManage"));
		String account =session.getAttribute(XiaoaiConstant.SESSION_KEY).toString();
		session.removeAttribute(XiaoaiConstant.SESSION_KEY);
		session.close(true);
		//立马清除map里面的session
		sessionManager.removeSession(account);
		return null;
	}

}
