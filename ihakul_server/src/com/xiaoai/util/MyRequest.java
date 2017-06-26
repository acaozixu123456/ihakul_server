package com.xiaoai.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;


/**
 * @author ZERO
 * @version 2017-4-13 下午4:03:28
 * 设置request和response
 */
public class MyRequest implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MyRequest.class);
	/**
	 * 获取request
	 * @return request
	 * @throws IOException
	 */
   public static HttpServletRequest  getRequest() throws IOException{
	   HttpServletRequest request=ServletActionContext.getRequest();
	   request.setCharacterEncoding("utf-8");
	   return request;
   }
   
   
   /**
    * 设置response
    * @return PrintWriter
    * @throws IOException
    */
   public static PrintWriter  getResponse() throws IOException{
	   HttpServletResponse response =ServletActionContext.getResponse();
	   response.setCharacterEncoding("utf-8");
	   return response.getWriter();
   }
   
   
  
  
   
   /** 
    * 从request获取字符串类型的参数 
    * @param request 
    * @param paramStr 
    * @param defaultValue 
    * @return 找不到返回null 
    */  
   public static String getString(HttpServletRequest request, String paramStr){  
       String value = request.getParameter(paramStr);  
       return value;  
   }  
   
   
   /** 
    * 获取当前访问的url 
    * @param 
    * @return 
    */  
   public static String getQueryUrl(){  
	   HttpServletRequest request=ServletActionContext.getRequest();
       return request.getContextPath() + request.getServletPath() + "?" + request.getQueryString();  
   }
   
   
   /** 
    * 获取当前访问的url 
    * @param 
    * @return 
    */  
   public static String getQueryUrl(HttpServletRequest request){  
       return request.getContextPath() + request.getServletPath() + "?" + request.getQueryString();  
   }
   
   
   /**
    * 将request获取得到的参数打印出来
    * @throws IOException
    */
   @SuppressWarnings("rawtypes")
   public static void  printParameterNames(String str) throws IOException{
	   HttpServletRequest request=ServletActionContext.getRequest();
	   Map<String,Object> map=new HashMap<String,Object>();
	   Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
		String paraName=(String)enu.nextElement();  
		map.put(paraName, request.getParameter(paraName));
		}
		System.out.println("\r\n");
		logger.warn(XATools.getClientIp());
     	logger.info(str+":"+map);
   }
   
   /**
    * 获取请求集合
    * @throws IOException
    */
   @SuppressWarnings("rawtypes")
   public static JSONObject  getParameterNames() throws IOException{
	   HttpServletRequest request=ServletActionContext.getRequest();
	   JSONObject json=new JSONObject();
	    Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){
	/*		if(enu.nextElement()){
				
			}*/
		String paraName=(String)enu.nextElement();  
		
		json.put(paraName, request.getParameter(paraName));
		}
		return json;
   }

   
   
   
   /**
	 * 获取本机Ip
	 * @return String
	 * @throws UnknownHostException 
	 */
	public static String getLocalHostIp() throws   UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
   

   
	
}
