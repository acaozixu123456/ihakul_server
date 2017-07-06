package com.xiaoai.mina.service.handler;

import javax.annotation.Resource;


import org.apache.log4j.Logger;


import com.xiaoai.entity.Xiaoi;
import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.RequestHandler;
import com.xiaoai.mina.service.constant.XiaoaiConstant;
import com.xiaoai.mina.service.session.DefaultSessionManager;
import com.xiaoai.mina.service.session.XiaoaiSession;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.ContextHolder;
import com.xiaoai.util.XATools;
/**
 *  断开连接，清除session
 * @author Administrator
 *
 */
public class SessionClossHanler implements RequestHandler{
	private final Logger logger=Logger.getLogger(SessionClossHanler.class);
	@Resource(name="xiaoiService")
	private IXiaoiService xiaoiService;
	
	@Override
	public SentBody process(XiaoaiSession session, SentBody message) {
		DefaultSessionManager sessionManager  =  ((DefaultSessionManager) ContextHolder.getBean("XiaoaiSessionManage"));
	//	String account=message.getData().get("account");
		if(session.getAttribute(XiaoaiConstant.SESSION_KEY)==null)
		{
			return null;
		}
		
	    String account2 = session.getAttribute(XiaoaiConstant.SESSION_KEY).toString();
	    logger.info("account2:"+account2);
	    if(!XATools.isNull(account2)){
			updateXiaoai(account2); //连接断开时修改小艾状态为离线
		}
	  
	    sessionManager.removeSession(account2);
	    logger.info("已断开连接");
	    return null;
	}
   
	public void updateXiaoai(String xiaoNumber){
		Xiaoi xiao=xiaoiService.selectXiaoiByNumber(xiaoNumber);
		if(xiao!=null){
			xiao.setState(0);
			xiaoiService.updateXiaoi(xiao);
		}
		
	}
	
}
