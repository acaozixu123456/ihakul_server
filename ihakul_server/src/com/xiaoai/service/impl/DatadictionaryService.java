package com.xiaoai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoai.dao.IDatadictionaryDao;
import com.xiaoai.entity.Datadictionary;
import com.xiaoai.service.IDatadictionaryService;
/**
 * 数据字典业务层的实现类
 * @author Administrator
 *
 */
@Service("datadicService")
public class DatadictionaryService implements IDatadictionaryService {
	@Resource(name="datadicDao")
	private IDatadictionaryDao datadicDao;
	//查询所有数据字典数据
	public List<Datadictionary> findAll() {
		
		return datadicDao.findAll();
	}

	//修改数据字典数据
	public boolean update(Datadictionary datadictionary) {
		boolean fals=true;
		try {
			datadicDao.update(datadictionary);
			
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	//添加数据字典数据
	public boolean insert(Datadictionary datadictionary) {
		boolean fals=true;
		try {
			datadicDao.insert(datadictionary);
			
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	//删除数据字典数据
	public boolean delete(Datadictionary datadictionary) {
		boolean fals=true;
		try {
			datadicDao.delete(datadictionary);
			
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	//分页查询
	public List<Datadictionary> paginFind(int begin, int showPage,
			Datadictionary datadictionary) {
		StringBuilder sql=new StringBuilder("from Datadictionary as da where 1=1");
		if(datadictionary.getDdName()  !=null){
			sql.append(" and ddName=:ddName ");
		}
		String hql=sql.toString();
		List<Datadictionary> list=datadicDao.paginFind(begin, showPage, datadictionary, hql);
		return list;
	}

	//得到总记录数
	public int getCountDatadictionary(Datadictionary datadictionary) {
		StringBuilder sql=new StringBuilder("select count(*) from  Datadictionary as da where 1=1");
		if(datadictionary.getDdName()  !=null){
			sql.append(" and ddName=:ddName ");
		}
		String hql=sql.toString();
		return datadicDao.getCountDatadictionary(hql, datadictionary);
	}

	//根据id查询
	public Datadictionary selectDatadictionaryByid(int id) {
		
		return datadicDao.selectDatadictionaryByid(id);
	}
	
	
	
	

}
