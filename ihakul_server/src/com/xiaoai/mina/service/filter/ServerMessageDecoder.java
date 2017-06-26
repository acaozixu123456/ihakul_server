package com.xiaoai.mina.service.filter;

import java.io.ByteArrayInputStream;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xiaoai.mina.model.SentBody;
import com.xiaoai.mina.service.constant.XiaoaiConstant;



/**
 * 服务端接收消息解码，可在此解密消息
 * @author Administrator
 *
 */
public class ServerMessageDecoder extends CumulativeProtocolDecoder{
	protected final Logger logger = Logger.getLogger(ServerMessageDecoder.class);
    private IoBuffer buff = IoBuffer.allocate(320).setAutoExpand(true);
	@Override
	public boolean doDecode(IoSession iosession, IoBuffer iobuffer, ProtocolDecoderOutput out) throws Exception {
		boolean complete = false;
		while (iobuffer.hasRemaining()) {
            byte b = iobuffer.get();
            /**
			 * XiaoaiConstant.MESSAGE_SEPARATE 为消息界限
			 * 当一次收到多个消息时，以此分隔解析多个消息
			 */
            if (b == XiaoaiConstant.MESSAGE_SEPARATE ) {
            	
            	complete = true;
                break;
            }else if(b == '\0')//flex客户端 安全策略验证时会收到<policy-file- request/>\0的消息，忽略此消息内容
            {
            	complete = true;
                break;
            }
            	else {
                buff.put(b);
            }
        }
		if (complete) {
			buff.flip();
	        byte[] bytes = new byte[buff.limit()];
	        buff.get(bytes);
	        
	        String message = new String(bytes, XiaoaiConstant.UTF8);
	        
	        logger.debug(message);
	        logger.info("------------"+message);
	        buff.clear();
	        try{
	        	 
				Object body = getSentBody(message);
		        out.write(body);
	        }catch(Exception e){
	        	out.write(message);//解析xml失败 是返回原始的xml数据到上层处理,比如心跳响应、flex sokcet的 安全验证请求xml
	        }
		}
	    return complete;
	}
	
	public Object getSentBody(String message) throws Exception
	{
		SentBody body = new SentBody();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document doc = builder.parse(new ByteArrayInputStream(message.getBytes(XiaoaiConstant.UTF8)));
        body.setKey(doc.getElementsByTagName("key").item(0).getTextContent());
        
        NodeList datas = doc.getElementsByTagName("data");
        if(datas!=null&&datas.getLength()>0)
        {
	        NodeList items = datas.item(0).getChildNodes();  
	        for (int i = 0; i < items.getLength(); i++) {  
	            Node node = items.item(i);  
	            body.getData().put(node.getNodeName(), node.getTextContent());
	        }
        }
        return body;
	}

}	
