package com.xiaoai.dao;

import com.xiaoai.entity.Xiaoiurl;



/**
 * @author ZERO
 * @Data 2017-5-25 上午10:39:23
 * @Description 
 */
public interface  IXiaoiurl {
	
	/**
	 * 根据Id查询
	 */

	public Xiaoiurl  selectXiaoiupdateByid(int id);

	
	/**
	 * 根据包名号查询
	 */
	public Xiaoiurl  selectXiaoiupdateBynumber(int number);
}
