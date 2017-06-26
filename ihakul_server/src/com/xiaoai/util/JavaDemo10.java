package com.xiaoai.util;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 随机验证码
 * @author Administrator
 *
 */
public class JavaDemo10 {
	
	@SuppressWarnings("rawtypes")
	public static String getRandomNumber(){
		 String[] beforeShuffle = new String[] {"1",  "2", "3", "4", "5", "6", "7",  
					"8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
					"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
					"W", "X", "Y", "Z" };  
				     List list = Arrays.asList(beforeShuffle);  
				     Collections.shuffle(list);  
				     StringBuilder sb = new StringBuilder();  
				     for (int i = 0; i < list.size(); i++) {  
				         sb.append(list.get(i));  
				     }  
				     String afterShuffle = sb.toString();  
				     String result = afterShuffle.substring(3, 9);
				     Date date=new Date();
				     DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
				     String nowTime=format.format(date);
				     StringBuffer sbu=new StringBuffer(nowTime);
				     String number=sbu.append(result).toString();
				     return number;
	}
	
	
}
