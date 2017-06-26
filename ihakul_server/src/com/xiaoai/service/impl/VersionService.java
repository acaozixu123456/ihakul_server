package com.xiaoai.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaoai.dao.IVersionDao;
import com.xiaoai.entity.Versions;
import com.xiaoai.service.IVersionService;
import com.xiaoai.util.XATools;
/**
 * 版本业务实现类
 * @author Administrator
 *
 */

@Service("versionService")
public class VersionService implements IVersionService {
	
	@Resource(name="versionDao")
	private IVersionDao versionDao;
	//根据版本号和包名进行条件分页查询
	public List<Versions> findAllVersion(Versions version, int showPage,
			int beginNumber) {
		StringBuffer sb=new StringBuffer("from Versions as v where 1=1 ");
		if(version.getVersionNumber() !=null){
			sb.append(" and v.versionNumber=:versionNumber");
		}
		if(version.getUpgradeTime() !=null){
			sb.append(" and v.upgradeTime=:upgradeTime");
		}
		if(version.getVersionPackage()!=null){
			sb.append(" and v.versionPackage=:versionPackage");
		}
//		if(version.getUpgradeClass() !=null){
//			sb.append(" and v.upgradeClass=:upgradeClass");
//		}
		String hql=sb.toString();
		List<Versions> list=versionDao.findAllVersion(hql, showPage, beginNumber, version);
		return list;
	}

//	//根据版本号和更新内容得到总记录数
//	public int getCountVersion(Versions version) {
//		StringBuffer sb=new StringBuffer("select count(*) from Versions as v where 1=1");
//		if(version.getVersionNumber() !=null){
//			sb.append(" and v.versionNumber=:versionNumber");
//		}
//		if(version.getUpgradeTime() !=null){
//			sb.append(" and v.upgradeTime=:upgradeTime");
//		}
//		if(version.getUpgradeClass() !=null){
//			sb.append(" and v.upgradeClass=:upgradeClass");
//		}
//		String hql=sb.toString();
//		int count=versionDao.getCountVersion(hql, version);
//		return count;
//	}
    
	
	//根据版本号和包名得到总记录数
	public int getCountVersion(Versions version) {
		StringBuffer sb=new StringBuffer("select count(*) from Versions as v where 1=1");
		if(version.getVersionNumber() !=null){
			sb.append(" and v.versionNumber=:versionNumber");
		}
		if(version.getUpgradeTime() !=null){
			sb.append(" and v.upgradeTime=:upgradeTime");
		}
		if(version.getVersionPackage()!=null){
			sb.append(" and v.versionPackage=:versionPackage");
		}
		String hql=sb.toString();
		int count=versionDao.getCountVersion(hql, version);
		return count;
	}
	
	//添加
	public boolean insertVersion(Versions version) {
		boolean fals=true;
		version.setUpgradeTime(XATools.getTNowTime());
		try {
			versionDao.insertVersion(version);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		
		return fals;
	}

	//根据id查询
	public Versions selectVersionByid(int id) {
		Versions version=versionDao.selectVersionByid(id);
		return version;
	}

	//修改
	public boolean updateVersion(Versions version) {
		boolean fals=true;
		try {
			versionDao.updateVersion(version);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
			
		}
		
		return fals;
	}

	//删除
	public boolean deleteVrtsion(Versions version) {
		boolean fals=true;
		try {
			versionDao.deleteVrtsion(version);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}

	
	public List<Versions> selectVersionByverNumber(Versions versions) {
		
		return versionDao.selectVersionByverNumber(versions);
	}
   
	@Override
	public List<Versions> selectVersionByvr(Versions versions) {
		
		return versionDao.selectVersionByvr(versions);
	}
	
	@Override
	public List<Versions> selectVersion() {
		
		return versionDao.selectVersion();
	}

	

}
