package com.xiaoai.util;

import java.io.Serializable;

public class XiaoiResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消息
	 */
	private String message;
	/**
	 * 返回标识
	 */
	private boolean success;
	/**
	 * 错误码
	 */
	private int code;
	
	
	public static XiaoiResult build(String message, boolean success, int code) {
		return new XiaoiResult(message,success,code);
	}
	
	public static XiaoiResult build(String message, int code) {
		return new XiaoiResult(message,false,code);
	}
	
	public static XiaoiResult ok(String message) {
		return new XiaoiResult(message,true,0);
	}
	
	public XiaoiResult() {
		this.success = true;
	}


	public XiaoiResult(String message, boolean success, int code) {
		this.message = message;
		this.success = success;
		this.code = code;
	}


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
