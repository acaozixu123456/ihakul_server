package com.xiaoai.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.struts2.ServletActionContext;


/**
 * 简单方法集合
 * @author ZERO
 */
public class XATools implements java.io.Serializable{
     
	private static final long serialVersionUID = 1L;


	/**
	 * 判断String类型的数据是否为空
	 * @return boolean
	 */
	public static boolean isNull(String data){
		if(null==data||"".equals(data)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断list类型的数据是否为空
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(List list){
		if(null==list||list.size()==0){
			return true;
		}
		return false;
	}

	/**
	 * 获取当前String类型的的时间
	 * @return String
	 */
	public static String getNowTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 获取当前long类型的的时间
	 * @return long
	 */
	public static long getNowTime1(){
	   return System.currentTimeMillis();
	}
	
	/**
	 *  long类型的时间转换成自定义时间格式 
	 * @param lo
	 * @return String
	 */
	 public static String LongTime2StringTime(long lo,String format){
		 if(isNull(format)){
			 format="yyyy-MM-dd HH:mm:ss";
		 }
		 return new SimpleDateFormat(format).format(lo);
	 }
	
	/**
	 *  String类型的时间转换成 long
	 * @param lo
	 * @return String
	 * @throws ParseException 
	 */
	 public static long StringTime2LongTime(String time,String format) throws ParseException{
		 if(null==format||"".equals(format)){
			 format="yyyy-MM-dd HH:mm:ss";
		 }
		 if(null==time||"".equals(time)){
			 time=new SimpleDateFormat(format).format(new Date());
		 }
		 SimpleDateFormat sd= new SimpleDateFormat(format);
		 Date date=sd.parse(time);
		 return date.getTime();
		}
 
	/**
	 * 获取当前String类型的的时间(自定义格式)
	 * @return String
	 */
	public static String getNowTime(String format){
		if(isNull(format)){//如果没有设置格式使用默认格式
			format="yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(format).format(new Date());
	}
	
	/**
	 * 获取当前Timestamp类型的的时间
	 * @return Timestamp
	 */
	public static Timestamp getTNowTime(){
		return Timestamp.valueOf(getNowTime());
	}
	
	/**
	 * 获取的String类型时间加上增加的分钟
	 * @return String
	 */
	public static String getTimeAddmi(int mi,String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=null;
		try {
			d = format.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
			return time;
		}    
		Calendar ca = Calendar.getInstance();  //定义一个Calendar 对象
		ca.setTime(d);//设置时间
		ca.add(Calendar.MINUTE, mi);//增加分钟
		String backTime = format.format(ca.getTime());  //转化为String 的格式
		return backTime;
	}
	
	/**
	 * 获取的String类型时间加上增加的分钟自定义格式
	 * @return String
	 */
	public static String getTimeAddmi(int mi,String time,String format) {
		if(null==format||"".equals(format)){//如果没有设置格式使用默认格式
			format="yyyy-MM-dd HH:mm:ss";
		}
		if(null==time||"".equals(time)){ //如果没有设置时间取当前时间
			time=new SimpleDateFormat(format).format(new Date());
		}
		SimpleDateFormat format1 = new SimpleDateFormat(format);
		Date d=null;
		try {
			d = format1.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}    
		Calendar ca = Calendar.getInstance();  //定义一个Calendar 对象
		ca.setTime(d);//设置时间
		ca.add(Calendar.MINUTE, mi);//增加分钟
		String backTime = format1.format(ca.getTime());  //转化为String 的格式
		return backTime;
	}
	
	
	/**
	 * 获取的String类型时间加上增加的小时
	 * @return String
	 */
	public static String getTimeAddho(int ho,String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=null;
		try {
			d = format.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}    
		Calendar ca = Calendar.getInstance();  //定义一个Calendar 对象
		ca.setTime(d);//设置时间
		ca.add(Calendar.HOUR, ho);//增加小时
		String backTime = format.format(ca.getTime());  //转化为String 的格式
		return backTime;
	}
	
	/**
	 * 两个日期带时间比较
	 * @param String 
	 * @return boolean
	 * @throws ParseException 
	 */
    public static boolean compareDay(String time1,String time2) throws ParseException  {
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date t1=s1.parse(time1);
		Date t2=s1.parse(time2);
		return t2.after(t1);//true，当 t2 大于 t1 时，为 true，否则为 false
    }
	
	/**
	 * 两个日期带时间比较
	 * @param String 
	 * @return boolean
	 * @throws ParseException 
	 */
    public static boolean compareDay(String time1,String time2,String format) throws Exception {
		  if(null==format||"".equals(format)){//如果没有设置格式使用默认格式
				format="yyyy-MM-dd HH:mm:ss";
			}
		    SimpleDateFormat s1 = new SimpleDateFormat(format);
			Date t1=s1.parse(time1);
			Date t2=s1.parse(time2);
			return t2.after(t1);//当 t2 大于 t1 时，为 true，否则为 false
	    }
    
    
	/**
	 * 自定义位数产生随机数字
	 * @param int 
	 * @return String
	 */
	public static String random(int count) {
		char start = '0';
		char end = '9';
		Random rnd = new Random();
		char[] result = new char[count];
		int len = end - start + 1;
		while (count-- > 0) {
		    result[count] = (char) (rnd.nextInt(len) + start);
		}
		return new String(result);
	}
		
	
	/**
	 * 获取本机ip
	 * @return String
	 * @throws UnknownHostException 
	 */
	public static String getLocalHostIp() throws  UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	
	/**
	 * 获取本机port
	 * @return int
	 * @throws UnsupportedEncodingException 
	 */
	public static int getLocalPort() throws  UnsupportedEncodingException {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		return request.getLocalPort();
	}
	
	/**
	 * 获取项目名称
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public static String getProjectName() {
		HttpServletRequest request=ServletActionContext.getRequest();
		return request.getContextPath();
	}
	

	
	/**
	 * 获取客户端ip
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public static String getClientIp() throws  UnsupportedEncodingException {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		return request.getRemoteAddr();
	}
	
	/**
	 * 获取客户端port
	 * @return int
	 * @throws UnsupportedEncodingException 
	 */
	public static int getClientPort() throws  UnsupportedEncodingException {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		return request.getRemotePort();
	}
	
	/**
	 * 获取客户端主机ip
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public static String getClientHostIp() throws  UnsupportedEncodingException {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		return request.getRemoteHost();
	}
	

	
	
	
	
  
    /** 
     * xml转json
     *  
     * @param String 
     *          
     * @return json 
     */ 
    public static JSONObject xml2JSON(String sr){
    	  XMLSerializer xmlSerializer = new XMLSerializer(); 
    	  JSON json01 = xmlSerializer.read(sr);
		  return JSONObject.fromObject(json01); 
    }
   
    /**
     * 判断是否为数字
     * @param String           
     * @return boolean 
     */   
    public static boolean isNumber(String str) {
		String regEx="^[0-9]+$";
	    Pattern p=Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
   
    
    /**
     * 判断是否为整型
     * @param String           
     * @return boolean 
     */   
    public static boolean isInteger(String str) {
		String regEx="^\\d+$";
	    Pattern p=Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
    
    /**
     * 判断是否为手机号
     * @param String           
     * @return boolean 
     */ 
    public static boolean isMobileNO(String mobiles){  
    	String regEx="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    	Pattern p = Pattern.compile(regEx);  
    	Matcher m = p.matcher(mobiles);  	  
    	return m.find();   
    	} 
    
  
    /**
     * 改变协议进制
     * @param int           
     * @return byte 
     */ 
    public static byte getType(int prop){
		return (byte) (prop >>56 &0xff); 
	}
    
    /**
     * MD5加密
     * @param message
     * @return
     */
    public static String encode(String message) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
   
    /**
     * 检查任务意图是否正确
     * @param orders
     * @return
     */
	public static  boolean checkOrders(String orders){
		if(!isNull(orders)){
			if(orders.equals("ALARM_SET")||orders.equals("REMINDER_SET")
				||orders.equals("DEVICE_ON")||orders.equals("DEVICE_OFF")||orders.equals("SONG_SET")){
				return true;
			}
		}
		return false;
	}

}
