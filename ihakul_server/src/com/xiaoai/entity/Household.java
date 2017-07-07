package com.xiaoai.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 家电
 * Household entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "household", catalog = "xiaoi")
public class Household implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	public static final String HID = "hid";
	public static final String ROOM = "room";
	public static final String FAMILY_GROUP = "familygroup";
	public static final String CLASS_ID = "classId";
	public static final String BRAND = "brand";
	public static final String MODEL = "model";
	public static final String CREAT_TIME = "createTime";
	public static final String EA_NAME = "eaName";
	public static final String EA_NUMBER = "eaNumber";
	public static final String TYPE = "type";
	public static final String PROP = "prop";
	public static final String STUB = "stub";
	public static final String PORT = "port";
	public static final String STATUS = "status";
	
	private Integer hid;             //家电id
	private Room room;               //家电房间 
	private Familygroup familygroup; //家电所属家庭组 
	private Integer classId;     //家电类别(1,智能;2普通) 
	private String brand;            //品牌  
	private String model;            //型号
	private Timestamp createTime;    //创建时间
	private String eaName;           //家电名字
	private String eaNumber;        //家电编号
	private String type;        //家电类型
	private long prop;        //通讯参数
	private Integer stub;        //智能索引
	private Integer port;        //
	private Integer status;        //


	private Set<Xiaoilog> xiaoiLogs = new HashSet<Xiaoilog>(0);

	/** default constructor */
	public Household() {
	}

	/**
	 * @param room 			房间
	 * @param familygroup	家庭组
	 * @param classId		家电类别(1,智能;2普通)
	 * @param brand			品牌
	 * @param model			型号
	 * @param createTime	创建时间
	 * @param hhName		家电名字
	 * @param hidNumber		家电编号
	 * @param props		            通讯参数
	 * @param stub			智能索引
	 * @param type			家电类型
	 */
	public Household(Room room, Familygroup familygroup, Integer classId,
			String brand, String model, Timestamp createTime, String eaName,
			Set<Xiaoilog> xiaoiLogs,String eaNumber,long prop,Integer stub
			,String type,Integer port,Integer status) {
		this.room = room;
		this.familygroup = familygroup;
		this.classId = classId;
		this.brand = brand;
		this.model = model;
		this.createTime = createTime;
		this.eaName = eaName;
		this.xiaoiLogs=xiaoiLogs;
		this.eaNumber=eaNumber;
		this.prop=prop;
		this.stub=stub;
		this.type=type;
		this.port=port;
		this.status=status;
	}
  

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public long getProp() {
		return prop;
	}

	public void setProp(long prop) {
		this.prop = prop;
	}

	public Integer getStub() {
		return stub;
	}

	public void setStub(Integer stub) {
		this.stub = stub;
	}

  
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "hid", unique = true, nullable = false)
	public Integer getHid() {
		return this.hid;
	}

	public void setHid(Integer hid) {
		this.hid = hid;
	}
    
	//改为懒加载
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roomId")
	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
   
	//改为懒加载
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId")
	public Familygroup getFamilygroup() {
		return this.familygroup;
	}

	public void setFamilygroup(Familygroup familygroup) {
		this.familygroup = familygroup;
	}

	@Column(name = "brand", length = 50)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name = "model", length = 50)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getEaName() {
		return eaName;
	}

	public void setEaName(String eaName) {
		this.eaName = eaName;
	}

	public String getEaNumber() {
		return eaNumber;
	}

	public void setEaNumber(String eaNumber) {
		this.eaNumber = eaNumber;
	}

	//改为懒加载
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "household")
	public Set<Xiaoilog> getXiaoiLogs() {
		return xiaoiLogs;
	}

	public void setXiaoiLogs(Set<Xiaoilog> xiaoiLogs) {
		this.xiaoiLogs = xiaoiLogs;
	}
	
	
}