package com.xiaoai.dao;

import java.util.List;

import com.xiaoai.entity.Channel;

/**
 * 频道管理接口
 * @author Administrator
 *
 */
public interface IChannelDao {
	/**
	 * 查询所有频道
	 * @return 频道对象集合
	 */
	public List<Channel> selectChannel();
	
	/**
	 * 根据id查询频道
	 * @param cid 频道id
	 * @return 频道对象
	 */
	public Channel getChannelByid(int cid);
	/**
	 * 添加频道
	 * @param channel 频道对象
	 */
	public void insertChannel(Channel channel);
	/**
	 * 修改频道信息
	 * @param channel 频道对象
	 */
	public void updateChannel(Channel channel);
	/**
	 * 删除频道
	 * @param channel 频道对象
	 */ 
	public void delectChannel(Channel channel);
	/**
	 * 条件分页查询
	 * @param sql 查询的sql语句
	 * @param begin 每页的开始记录数
	 * @param showPage 每页显示最大记录数
	 * @param channel 频道对象
	 * @return
	 */
	public List<Channel> selectChannels(String sql,int begin,int showPage,Channel channel);

	/**
	 * 得到总记录数
	 * @param sql 执行的sql语句
	 * @param channel 频道对象
	 * @return
	 */
	public int getCountChannels(String sql,Channel channel);
}
