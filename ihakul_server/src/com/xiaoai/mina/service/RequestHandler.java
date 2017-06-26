package com.xiaoai.mina.service;




import com.xiaoai.mina.model.ReplyBody;
import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.session.XiaoaiSession;
/**
 * 请求处理接口,所有的请求必须实现此接口
 * @author Administrator
 *
 */
public interface RequestHandler {
	
//	public abstract ReplyBody process(XiaoaiSession session,SentBody message);
	public abstract SentBody process(XiaoaiSession session,SentBody message);
	
}
