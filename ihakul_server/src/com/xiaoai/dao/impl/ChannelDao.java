package com.xiaoai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoai.dao.IChannelDao;
import com.xiaoai.entity.Channel;

/**
 * 频道管理实现类
 * @author Administrator
 *
 */
@Repository("channelDao")
public class ChannelDao implements IChannelDao {
	
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> selectChannel() {
		List<Channel> list=hibernateTemplate.find("from Channel");
		return list;
	}

	@Override
	public Channel getChannelByid(int cid) {
		
		return hibernateTemplate.get(Channel.class, cid);
	}

	@Override
	public void insertChannel(Channel channel) {
		hibernateTemplate.save(channel);

	}

	@Override
	public void updateChannel(Channel channel) {
		hibernateTemplate.update(channel);

	}

	@Override
	public void delectChannel(Channel channel) {
		hibernateTemplate.delete(channel);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> selectChannels(String sql, int begin, int showPage,
			Channel channel) {
	//	Session session=hibernateTemplate.getSessionFactory().openSession();
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(sql);
		query.setFirstResult(begin);
		query.setMaxResults(showPage);
		query.setProperties(channel);
		List<Channel> list=query.list();
		return list;
	}

	@Override
	public int getCountChannels(String sql, Channel channel) {
	//	Session session=hibernateTemplate.getSessionFactory().openSession();
		Session session=hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(sql);
		query.setProperties(channel);
		int count=((Long)query.uniqueResult()).intValue();
		return count;
	}

}
