package com.xiaoai.mina.service.session;

import java.io.Serializable;



import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;


/**
 * IoSession包装类  
 * I/O 会话
 * @author Administrator
 *
 */
public class XiaoaiSession implements Serializable {

	
	private static final long serialVersionUID = 1L;
	

	
	
    public  transient static  final int STATUS_DISENABLE=1;
	private transient IoSession session;
	
	
	private String gid;				//session全局ID
	private Long nid;				//session在本台服务器上的ID
	private String deviceId;    	//客户端ID  (设备号码+应用包名),ios为devicetoken
	private String host;			//session绑定的服务器IP
//	private String account;			//session绑定的账号
	
	private String xiaoNumber;		//session绑定的小艾编号
	
	private String channel;			//终端设备类型
	private String deviceModel;		//终端设备型号
	private String clientVersion;	//终端应用版本
	private String systemVersion;	//终端系统版本
	private Long bindTime;			//登录时间
	private Long heartbeat;			//心跳时间
	private Double longitude;		//经度
	private Double latitude;		//维度
	private String location;		//位置
	private int apnsAble;			//apns推送状态
	private int status;				// 状态
	
	public XiaoaiSession(IoSession session) {
		this.session = session;
		this.nid = session.getId();
	}
 
	public XiaoaiSession()
	{
		
	}
	
	
 
//
//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}

	public String getXiaoNumber() {
		return xiaoNumber;
	}

	public void setXiaoNumber(String xiaoNumber) {
		this.xiaoNumber = xiaoNumber;
	}
	 
	
	 


	public Double getLongitude() {
		return longitude;
	}

	

	public void setLongitude(Double longitude) {
		
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		
		this.location = location;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public String getDeviceId() {
		return deviceId;
	}


	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
		
		
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		
		
	}


   

	public String getHost() {
		return host;
	}



	public Long getBindTime() {
		return bindTime;
	}

	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
	   
	}


	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
		
	}

	

	
	
	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
		
	}

	public Long getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(Long heartbeat) {
		this.heartbeat = heartbeat;
		
	}

	public void setHost(String host) {
		this.host = host;
		 
		
	}


	public int getApnsAble() {
		return apnsAble;
	}

	public void setApnsAble(int apnsAble) {
		this.apnsAble = apnsAble;
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		
	}

//  将键为 key，值为 value的用户自定义的属性存储到 I/O 会话中。
	public void setAttribute(String key, Object value) {
		if(session!=null){
			session.setAttribute(key, value);
		}
		
	}


	public boolean containsAttribute(String key) {
		if(session!=null){
			
			return session.containsAttribute(key);
		}
		return false;
	}
	//	从 I/O 会话中获取键为 key的用户自定义的属性。
	public Object getAttribute(String key) {
		if(session!=null){
			
			return session.getAttribute(key);
		}
		
		return null;
	}
	//从 I/O 会话中删除键为 key的用户自定义的属性。
	public void removeAttribute(String key) {
		if(session!=null){
			session.removeAttribute(key);
		}
	}
		

	public SocketAddress getRemoteAddress() {
		if(session!=null){
			return session.getRemoteAddress();
		}		
		return null;
	}

/*	 将消息对象 message发送到当前连接的对等体。该方法是异步的，当消息被真正发送到对等体的时候，
	IoHandler.messageSent(IoSession,Object)会被调用。如果需要的话，
	也可以等消息真正发送出去之后再继续执行后续操作。*/
	public boolean write(Object msg) {
		if(session!=null)
		{
			WriteFuture write = session.write(msg);
			boolean awaitUninterruptibly = write.awaitUninterruptibly(5000);
			//boolean written = session.write(msg).isWritten();
			if(write.isWritten()){
				return true;
			}
		
		}
		return false;
	}
	
	

	public boolean isConnected() {
		if(session!=null){
			return session.isConnected();
		}
		return false;
	}

	public boolean  isLocalhost()
	{
		
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip.equals(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
		 
	}
	
/* 关闭当前连接。如果参数 immediately为 true的话，
 * 连接会等到队列中所有的数据发送请求都完成之后才关闭；否则的话就立即关闭。 
 */	
	public void close(boolean immediately) {
		if(session!=null){
			session.close(immediately);
		}
	}

	 
	public boolean equals(Object message) {
        
		if (message instanceof XiaoaiSession) {
			
			XiaoaiSession t = (XiaoaiSession) message;
			if(t.deviceId!=null && deviceId!=null &&  t.nid!=null && nid!=null)
			{
				return t.deviceId.equals(deviceId) && t.nid.longValue()==nid.longValue() && t.host.equals(host);
			} 
		}  
		return false;
	}

	public void setIoSession(IoSession session) {
		this.session = session;
	}

	public IoSession getIoSession() {
		return session;
	}
	
	
	
	public String  toString()
	{
		StringBuffer buffer = new   StringBuffer();
		buffer.append("{");
		
		buffer.append("\"").append("gid").append("\":").append("\"").append(gid).append("\"").append(",");
		buffer.append("\"").append("nid").append("\":").append(nid).append(",");
		buffer.append("\"").append("deviceId").append("\":").append("\"").append(deviceId).append("\"").append(",");
		buffer.append("\"").append("host").append("\":").append("\"").append(host).append("\"").append(",");
//		buffer.append("\"").append("account").append("\":").append("\"").append(account).append("\"").append(",");
		buffer.append("\"").append("xiaoNumber").append("\":").append("\"").append(xiaoNumber).append("\"").append(",");
		buffer.append("\"").append("channel").append("\":").append("\"").append(channel).append("\"").append(",");
		buffer.append("\"").append("deviceModel").append("\":").append("\"").append(deviceModel).append("\"").append(",");
		buffer.append("\"").append("status").append("\":").append(status).append(",");
		buffer.append("\"").append("apnsAble").append("\":").append(apnsAble).append(",");
		buffer.append("\"").append("bindTime").append("\":").append(bindTime).append(",");
		buffer.append("\"").append("heartbeat").append("\":").append(heartbeat);
		buffer.append("}");
		return buffer.toString();
		
	}
	
}
