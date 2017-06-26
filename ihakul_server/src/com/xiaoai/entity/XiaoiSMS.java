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
 * 用户短信
 */
@Entity
@Table(name = "xiaoisms", catalog = "xiaoi")
public class XiaoiSMS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Integer id;
	private  String operID;
	private  String operPass;
	private  Integer appendID;
	private  String url;
	
	

	public XiaoiSMS(){
		
	}
	
	public XiaoiSMS(Integer id, String operID, String operPass,Integer appendID,String url) {
		this.id = id;
		this.operID = operID;
		this.operPass = operPass;
		this.appendID = appendID;
		this.url = url;
	}
    
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)       //id是主键,不能为空
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperID() {
		return operID;
	}

	public void setOperID(String operID) {
		this.operID = operID;
	}

	public String getOperPass() {
		return operPass;
	}

	public void setOperPass(String operPass) {
		this.operPass = operPass;
	}

	public Integer getAppendID() {
		return appendID;
	}

	public void setAppendID(Integer appendID) {
		this.appendID = appendID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

	

}
