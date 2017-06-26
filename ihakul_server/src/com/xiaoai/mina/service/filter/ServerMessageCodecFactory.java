package com.xiaoai.mina.service.filter;


import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.xiaoai.mina.model.SentBody;

/**
 * 服务端消息 编码解码器， 可以在
 * 关于消息加密与加密， 可在 encoder时进行消息加密，在ClientMessageCodecFactory的 decoder时对消息解密
 *
 * @author Administrator
 *
 */
	
//	 private final ServerMessageEncoder encoder;
//
//	    private final ServerMessageDecoder decoder;
//	 
//	    public ServerMessageCodecFactory() {
//	        encoder = new ServerMessageEncoder();
//	        decoder = new ServerMessageDecoder();
//	    }
//	 
//	    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
//	        return encoder;
//	    }
//	 
//	    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
//	        return decoder;
//	    }
	

public class ServerMessageCodecFactory implements ProtocolCodecFactory {
	private final Logger LOG=Logger.getLogger(ServerMessageCodecFactory.class);
		private MessageEncoder encoder;
		private MessageDecoder decoder;
	//	public static final String HB_REQUEST = "hb_request";
		public static final String HB_RESPONSE = "hb_response";
		/**
		 * 客户端消息 编码解码器工厂类，说明：
		 * <p>
		 * 在encoder时对发送的消息进行加密，在 decoder时对接收的消息进行解密
		 * <p>
		 * 注意，服务器端应与本客户端的加解密的算法相匹配
		 * 
		 * @author allon
		 * @version 1.0.1
		 * @date 2015年6月3日
		 */
		public ServerMessageCodecFactory() {
			this.encoder = new MessageEncoder();
			this.decoder = new MessageDecoder();
		}

		@Override
		public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
			return this.decoder;
		}

		@Override
		public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
			return this.encoder;
		}

		/**
		 * 内部消息编码器，即在此进行消息内容加密
		 * @author allon
		 * @version 1.0.1
		 * @date 2016年3月5日
		 */
		class MessageEncoder extends ProtocolEncoderAdapter {

			// 消息的结束符
			public static final int MARK_END = '\n';

			@Override
			public void encode(IoSession session, Object message,
					ProtocolEncoderOutput out) throws Exception {
		//		LOG.warn("加密前的信息:"+message);
				String value = (message == null) ? "" : message.toString();
				IoBuffer buf = IoBuffer.allocate(value.length())
						.setAutoExpand(true);
				buf.put(value.getBytes(HTTP.UTF_8));
				buf.put((byte) MARK_END);
				buf.flip();
				out.write(buf);
			}
		}

		/**
		 * 内部消息解码器，即在此对已加密的消息进行解密
		 * 
		 * @author allon
		 * @version 1.0.1
		 * @date 2016年3月5日
		 */
		class MessageDecoder extends CumulativeProtocolDecoder  {
			@Override
			protected boolean doDecode(IoSession session, IoBuffer in,
					ProtocolDecoderOutput out) throws Exception {
				boolean complete = false;
				IoBuffer buf = IoBuffer.allocate(128);
				buf.setAutoExpand(true);
				while (in.hasRemaining()) {
					byte oneByte = in.get();
					if (oneByte == MessageEncoder.MARK_END) {
						complete = true;
						break;
					} else {
						buf.put(oneByte);
					}
				}
				if (complete) {
					buf.flip();
					byte[] bytes = new byte[buf.limit()];
					buf.get(bytes);
					String message = new String(bytes, HTTP.UTF_8);
					out.write(parser(message));
				}
				return complete;
			}

			/**
			 * 解释单条消息
			 * 
			 * @param message
			 *            内容结构体
			 * @return 消息
			 */
			private Object parser(String message) {
		//		LOG.warn("解密前的信息:"+message);
				if (HB_RESPONSE.equals(message)) {
					return message;
				}
				SentBody box = new SentBody();
		        try {
					return box.as(message);
				} catch (Exception e) {
					box.setError(e);
					return box;
				}
			}
		}
}	
