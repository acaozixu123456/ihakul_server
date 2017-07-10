package com.xiaoai.mina.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import com.xiaoai.util.XiaoaiMessage;
import com.xiaoleilu.hutool.json.JSONException;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.BeanUtil;

/**
 * 客户端请求结构
 * 
 * @author Administrator
 * 
 */
public class SentBody implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final DocumentBuilderFactory f = DocumentBuilderFactory
			.newInstance();
	/**
	 * 请求的key值
	 */
	private String key;
	// /**
	// * 发送的数据集合
	// */
	// private HashMap<String,String> data;

	private HashMap<String, Serializable> data;

	private int code;

	private long timestamp;

	public SentBody() {
		// data=new HashMap<String,String>();
		data = new HashMap<String, Serializable>();
		timestamp = System.currentTimeMillis();
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

	// public HashMap<String, String> getData() {
	// return data;
	// }
	//
	// public void setData(HashMap<String, String> data) {
	// this.data = data;
	// }
	public void setData(HashMap<String, Serializable> data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void put(String k, String v) {
		data.put(k, v);
	}

	public void remove(String k) {
		data.remove(k);
	}

	public String get(String k) {
		return (String) data.get(k);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{'id':").append(this.timestamp).append(",'code':")
				.append(this.code).append(",'action':'").append(this.key)
				.append("'");

		if (this.data != null) {
			JSONObject object = new JSONObject(data);
			sb.append(",'result':").append(object.toString()).append("}");
		}
		return sb.toString();
	}

	public String toXmlString() {

		return toString();
	}

	public final SentBody as(String protocol) throws Exception {
		if (protocol == null || protocol.isEmpty()) {
			throw new NullPointerException("protocol == NULL");
		}
		JSONObject jsonObject = new JSONObject(protocol);
		this.timestamp = jsonObject.getLong("id");
		this.code = jsonObject.getInt("code");
		this.key = (String) jsonObject.get("action");
		//String map = (String) jsonObject.get("result");
		Object result_object = jsonObject.get("result");
		String map= null;
		if(result_object!=null){
			map = result_object.toString();
			this.data = jsonObjectToMap(map);
		}
		return this;
	}

	/**
	 * Converts a JSON-encoded string containing an object to a map.
	 * 
	 * @param json
	 *            a JSON-encoded string containing an object.
	 * @throws JSONException
	 *             if the parse fails or doesn't yield a {@code JSONObject}.
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Serializable> jsonObjectToMap(String json)
			throws JSONException {

		JSONObject jsonObject = new JSONObject(json);
		HashMap<String, Serializable> result = new HashMap<String, Serializable>();
		Set<String> keySet = jsonObject.keySet();
		for (Iterator iterator2 = keySet.iterator(); iterator2.hasNext();) {
			String key = (String) iterator2.next();
			Object val = jsonObject.get(key);
			result.put(key, (val instanceof Serializable) ? (Serializable) val
					: val.toString());
		}
		return result;
	}

	public void setError(Exception e) {
		this.code = XiaoaiMessage.Other;
		this.data = new HashMap<String, Serializable>();
		this.data.put("Exception", e);
	}
}
