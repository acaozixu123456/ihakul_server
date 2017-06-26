package com.xiaoai.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 版本类
 * Version entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "versions", catalog = "xiaoi")
public class Versions implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Integer versionNumber;	//版本号
	private String upgradeClass;	//更新类容
	private Timestamp upgradeTime;	//更新时间
	private Integer versionType;	//版本类型
	private String versionUrl;		//版本下载url
	private String versionName;     //文件名
	private String versionPackage;     //版本包名
	
	// Constructors


	/** default constructor */
	public Versions() {
	}

	/** full constructor */
	public Versions(String versionName, Integer versionNumber,
			String upgradeClass, Timestamp upgradeTime, Integer versionType,
			String versionUrl) {
		this.versionName = versionName;
		this.versionNumber = versionNumber;
		this.upgradeClass = upgradeClass;
		this.upgradeTime = upgradeTime;
		this.versionType = versionType;
		this.versionUrl = versionUrl;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "versionName", length = 50)
	public String getVersionName() {
		return this.versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}


	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Column(name = "upgradeClass", length = 50)
	public String getUpgradeClass() {
		return this.upgradeClass;
	}

	public void setUpgradeClass(String upgradeClass) {
		this.upgradeClass = upgradeClass;
	}

	@Column(name = "upgradeTime", length = 19)
	public Timestamp getUpgradeTime() {
		return this.upgradeTime;
	}

	public void setUpgradeTime(Timestamp upgradeTime) {
		this.upgradeTime = upgradeTime;
	}

	@Column(name = "versionType")
	public Integer getVersionType() {
		return this.versionType;
	}

	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	@Column(name = "versionUrl", length = 100)
	public String getVersionUrl() {
		return this.versionUrl;
	}

	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl;
	}
  
	
	public String getVersionPackage() {
		return versionPackage;
	}

	public void setVersionPackage(String versionPackage) {
		this.versionPackage = versionPackage;
	}
	
}