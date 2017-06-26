package com.xiaoai.mina.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import com.xiaoai.util.XiaoaiMessage;




/**
 *  客户端请求结构
 * @author Administrator
 *
 */
public class SentBody implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private static final DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	/**
	 * 请求的key值
	 */
	private String key;
//	/**
//	 * 发送的数据集合
//	 */
//	private HashMap<String,String> data;

	private HashMap<String,Serializable> data;
	
	
	
	private int code;
	
	private long timestamp;
	
	public SentBody(){
//		data=new HashMap<String,String>();
		data=new HashMap<String,Serializable>();
		timestamp=System.currentTimeMillis();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
  
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public HashMap<String, Serializable> getData() {
		return data;
	}
//	public HashMap<String, String> getData() {
//		return data;
//	}
//
//	public void setData(HashMap<String, String> data) {
//		this.data = data;
//	}
	public void setData(HashMap<String, Serializable> data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public void put(String k,String v){
		data.put(k, v);
	}
	public void remove(String k){
		data.remove(k);
	}
	public String get(String k) {
		return (String) data.get(k);
	}
	
	public String toString() {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//		buffer.append("<sent>");
//		buffer.append("<key>").append(key).append("</key>");
//		buffer.append("<timestamp>").append(timestamp).append("</timestamp>");
//		buffer.append("<data>");
//		for (String key : data.keySet()) {
//			buffer.append("<" + key + ">").append(data.get(key)).append(
//					"</" + key + ">");
//		}
//		buffer.append("</data>");
//		buffer.append("</sent>");
//		return buffer.toString();
		
		
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Protocol id='").append(this.timestamp).append("' code='").append(this.code).append("' action='")
				.append(this.key).append("' >");

		for (Map.Entry<String, Serializable> data : this.data.entrySet()) {
			Serializable o = null;
			if ((o=data.getValue()) == null){
				continue;
			}

			sb.append("<").append(data.getKey()).append(" type='").append(o.getClass().getName()).append("'>").append(o)
					.append("</").append(data.getKey()).append(">");
		}
		return sb.append("</Protocol>").toString();
	}

	public String toXmlString() {

		return toString();
	}
	
	public final SentBody as(String protocol) throws Exception {
		if(protocol==null || protocol.isEmpty()){
			throw new NullPointerException("protocol == NULL");
		} else
		if (this.data == null) {
			this.data = new HashMap<String,Serializable>();
		}
		DocumentBuilder b = f.newDocumentBuilder();
		org.w3c.dom.Document d = b.parse(
		new ByteArrayInputStream(protocol.getBytes("UTF-8")));
		org.w3c.dom.Element e = d.getDocumentElement();

		this.key = e.getAttribute("action");
		this.timestamp = Long.valueOf(e.getAttribute("id"));
		this.code = Integer.valueOf(e.getAttribute("code"));

		NodeList list = e.getElementsByTagName("*");
		for (int i = 0; i < list.getLength(); i++) {
			org.w3c.dom.Node n = list.item(i);

			String key = n.getNodeName();
			String value = n.getTextContent();
			String classOfT = n.getAttributes()
					.getNamedItem("type").getNodeValue();
			this.data.put(key, valueGet(classOfT, value));
		} return this;
	}
	
	private Serializable valueGet(String classOfT, String value) {
		try {
			Class<?> object = Class.forName(classOfT);
			Method md=object.getMethod("valueOf",
			"java.lang.String".equals(classOfT) ? Object.class : String.class);
			return (Serializable) md.invoke(null, value);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void setError(Exception e) {
		this.code = XiaoaiMessage.Other;
		this.data = new HashMap<String,Serializable>();
		this.data.put("Exception", e);
	}
}
