package com.xiaoai.service;

import java.util.List;

import com.xiaoai.entity.Channel;
/**
 * 频道管理业务实现接口
 * @author Administrator
 *
 */
public interface IChannelService {
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
	 * @return fals=true(添加成功)或者fals=false(添加失败)
	 */
	public boolean insertChannel(Channel channel);
	/**
	 * 修改频道信息
	 * @param channel 频道对象
	 * @return fals=true(修改成功)或者fals=false(修改失败)
	 */
	public boolean updateChannel(Channel channel);
	/**
	 * 删除频道
	 * @param channel 频道对象
	 * @return fals=true(删除成功)或者fals=false(删除失败)
	 */ 
	public boolean delectChannel(Channel channel);
	
	/**
	 * 条件分页查询
	 * @param begin 每页的开始记录数
	 * @param showPage 每页显示最大记录数
	 * @param channel 频道对象
	 * @return
	 */
	public List<Channel> selectChannels(int begin,int showPage,Channel channel);

	/**
	 * 得到总记录数
	 * @param channel 频道对象
	 * @return
	 */
	public int getCountChannels(Channel channel);
}
