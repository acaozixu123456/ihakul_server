package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoai.dao.IChannelDao;
import com.xiaoai.entity.Channel;
import com.xiaoai.service.IChannelService;
@Service("channelService")
public class ChannelService implements IChannelService {
	@Resource(name="channelDao")
	private IChannelDao channelDao;
	@Override
	public List<Channel> selectChannel() {
		
		return channelDao.selectChannel();
	}

	@Override
	public Channel getChannelByid(int cid) {
		
		return channelDao.getChannelByid(cid);
	}

	@Override
	public boolean insertChannel(Channel channel) {
		boolean fals=true;
		try {
			channelDao.insertChannel(channel);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	@Override
	public boolean updateChannel(Channel channel) {
		boolean fals=true;
		try {
			channelDao.updateChannel(channel);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	@Override
	public boolean delectChannel(Channel channel) {
		boolean fals=true;
		try {
			channelDao.delectChannel(channel);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	@Override
	public List<Channel> selectChannels(int begin, int showPage, Channel channel) {
		StringBuffer sql=new StringBuffer("from Channel as c where 1=1");
		if(channel.getFamilygroup() !=null){
			sql.append(" and c.familgroup=:familygroup");
		}
		
		return channelDao.selectChannels(sql.toString(), begin, showPage, channel);
	}

	@Override
	public int getCountChannels(Channel channel) {
		StringBuffer sql=new StringBuffer("select count(*) from Channel as c where 1=1");
		if(channel.getFamilygroup() !=null){
			sql.append(" and c.familygroup=:familygroup");
		}
		
		return channelDao.getCountChannels(sql.toString(),channel );
	}

}
