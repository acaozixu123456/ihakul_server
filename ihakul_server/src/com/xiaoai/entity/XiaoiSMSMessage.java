package com.xiaoai.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author ZERO
 * @version 2017-4-18 下午3:25:46
 * 用户短信消息
 */
@Entity
@Table(name = "xiaoismsmessage", catalog = "xiaoi")
public class XiaoiSMSMessage implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Integer id;
	private  String headMessage;
	private  String yzMessage;
	private  String messagetwo;
	private  String messageone;
	private  Integer minuteTime;
	private  String messageNumber;
	
	public XiaoiSMSMessage(){
		
	}
	
	public XiaoiSMSMessage(Integer id, String headMessage, String yzMessage,String messagetwo,
			String messageone,Integer minuteTime,String messageNumber) {
		this.id = id;
		this.headMessage = headMessage;
		this.yzMessage = yzMessage;
		this.messagetwo = messagetwo;
		this.messageone = messageone;
		this.minuteTime = minuteTime;
		this.messageNumber=messageNumber;
	}
    
	public String getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHeadMessage() {
		return headMessage;
	}

	public void setHeadMessage(String headMessage) {
		this.headMessage = headMessage;
	}

	public String getYzMessage() {
		return yzMessage;
	}

	public void setYzMessage(String yzMessage) {
		this.yzMessage = yzMessage;
	}

	public String getMessagetwo() {
		return messagetwo;
	}

	public void setMessagetwo(String messagetwo) {
		this.messagetwo = messagetwo;
	}

	public String getMessageone() {
		return messageone;
	}

	public void setMessageone(String messageone) {
		this.messageone = messageone;
	}

	public Integer getMinuteTime() {
		return minuteTime;
	}

	public void setMinuteTime(Integer minuteTime) {
		this.minuteTime = minuteTime;
	}
	
	

	

}
