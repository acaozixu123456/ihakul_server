package com.xiaoai.mina.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.xiaoai.mina.service.filter.KeepAliveMessageFactoryImpl;
import com.xiaoai.mina.service.filter.ServerMessageCodecFactory;


/**
 * mina服务端
 * I/O 服务
 * @author Administrator
 *
 */
public class SerNioSociketAcceptor {
	IoAcceptor acceptor;
    IoHandler ioHandler;
    int port;
	//记录日志
	public static Logger logger=Logger.getLogger(SerNioSociketAcceptor.class);
	//创建bind()方法接收连接
    public void bind() throws IOException
    {	
    	  //创建 协议编码解码过滤器ProtocolCodecFilter
//  	  ProtocolCodecFilter pf=new ProtocolCodecFilter(new TextLineCodecFactory(Charset
//                .forName("UTF-8"),
//                LineDelimiter.WINDOWS.getValue(),
//                LineDelimiter.WINDOWS.getValue()));
    	//设置 序列化Object
//  	ProtocolCodecFilter pf=new ProtocolCodecFilter(new ObjectSerializationCodecFactory());
  	  
       //getFilterChain() 获取 I/O 过滤器链，可以对 I/O 过滤器进行管理，包括添加和删除 I/O 过滤器。    	
    	acceptor = new NioSocketAcceptor();  
    	//设置缓存大小
        acceptor.getSessionConfig().setReadBufferSize(1024);  
         // 设置过滤器
        //设置多线程
        acceptor.getFilterChain().addLast("executor",new ExecutorFilter()); 
        //添加日志
        acceptor.getFilterChain().addLast("logger",new LoggingFilter());  
        //设置自定义解码器
        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ServerMessageCodecFactory()));
        // 设置过滤器（使用Mina提供的文本换行符编解码器）
      // acceptor.getFilterChain().addLast("codec",pf);
        
        KeepAliveMessageFactory kamf=new KeepAliveMessageFactoryImpl();
        KeepAliveFilter kaf = new KeepAliveFilter(kamf, IdleStatus.BOTH_IDLE);
        kaf.setForwardEvent(true);
        kaf.setRequestInterval(30);  //本服务器为被定型心跳  即需要每30秒接受一个心跳请求  否则该连接进入空闲状态 并且发出idled方法回调
        //kaf.setRequestTimeout(5); //超时时间   如果当前发出一个心跳请求后需要反馈  若反馈超过此事件 默认则关闭连接
        acceptor.getFilterChain().addLast("heart", kaf); 
        //读写通道60秒内无操作进入空闲状态
       acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
        //绑定逻辑处理器
        acceptor.setHandler(ioHandler);  
        //绑定端口
        acceptor.bind(new InetSocketAddress(port));
        logger.info("Mina服务端启动成功...端口号为:"+port); //测试使用
    }
    //创建unbind()方法停止监听
    public void unbind()
    {
    	acceptor.unbind();
    	logger.info("服务端停止成功");
    }
	public void setAcceptor(IoAcceptor acceptor) {
		this.acceptor = acceptor;
	}
	//	设置 I/O 处理器。该 I/O 处理器会负责处理该 I/O 服务所管理的所有 I/O 会话产生的 I/O 事件。
	public void setIoHandler(IoHandler ioHandler) {
		this.ioHandler = ioHandler;
	}
	//设置端口
	public void setPort(int port) {
		this.port = port;
	}
//	获取该 I/O 服务所管理的 I/O 会话。
	public  Map<Long, IoSession> getManagedSessions()
	{
		return acceptor.getManagedSessions();
	}

}
