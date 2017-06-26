package com.xiaoai.mina.service.filter;

import org.apache.log4j.Logger;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.xiaoai.mina.service.constant.XiaoaiConstant;





/**
 * 服务端发送消息前编码，可在此加密消息
 * @author Administrator
 *
 */
public class ServerMessageEncoder extends ProtocolEncoderAdapter{

	protected final Logger logger = Logger.getLogger(ServerMessageEncoder.class);
	@Override
	public void encode(IoSession iosession, Object message, ProtocolEncoderOutput out) throws Exception {
		logger.info("发送前未加密的消息:"+message);
		IoBuffer buff = IoBuffer.allocate(320).setAutoExpand(true);
		buff.put(message.toString().getBytes(XiaoaiConstant.UTF8));
	    buff.put(XiaoaiConstant.MESSAGE_SEPARATE);
	    buff.flip();
		out.write(buff);
		logger.info("发送前加密的消息:"+buff);
		logger.debug(message);
	}
}
