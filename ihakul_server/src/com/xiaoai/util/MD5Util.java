package com.xiaoai.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *对密码进行md5加密
 * @author Administrator
 *
 */
public class MD5Util {
	/** 
     * md5加密方法 
     * @param password 
     * @return 
     */  
    public static String md5Password(String password) {  
  
        try {  
            // 得到一个信息摘要器  
            MessageDigest digest = MessageDigest.getInstance("md5");  
            byte[] result = digest.digest(password.getBytes());  
            StringBuffer buffer = new StringBuffer();  
            // 把没一个byte 做一个与运算 0xff;  
            for (byte b : result) {  
                // 与运算  
                int number = b & 0xff;// 加盐  
                String str = Integer.toHexString(number);  
                if (str.length() == 1) {  
                    buffer.append("0");  
                }  
                buffer.append(str);  
            }  
  
            // 标准的md5加密后的结果  
            return buffer.toString();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return "";  
        }  
  
    }  
  
     
    
//    public static void main(String[] args) {
//    	MD5Util m=new MD5Util();
//    	String p=m.md5Password("123");
//    	System.out.println(p);
//	}
}
