package com.xiaoai.entity;



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
 * 终端小艾实体类
 * Xiaoi entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "xiaoi", catalog = "xiaoi")
public class Xiaoi implements java.io.Serializable {

	// Fields

	
	private static final long serialVersionUID = 1L;
	private Integer xid;
	private Familygroup familygroup;
	private String xname;
	private Integer onlineState;
	private String xiaoNumber;
	private Integer xiaoType;
	private String activationTime; //激活时间
	private String xiaoIp; 
	private Integer mode;  //终端情景模式(10-标准模式；20-睡眠模式；30-离家模式)
	private Integer volume;  //扬声器输出音量(取值范围 1-100，当设置为-1 时，终端静音)

	private Set<Xiaoilog> xiaoilogs = new HashSet<Xiaoilog>(0);
	
	//private Set<Room> room=new HashSet<Room>(0);
	// Constructors

	/** default constructor */
	public Xiaoi() {
	}

	/**
	 * 
	 * @param familygroup 
	 * @param xname 小艾名字
	 * @param useType 使用状态
	 * @param xiaoNumber 编号
	 * @param xiaoType 
	 * @param activationTime
	 * @param xiaoiIp
	 * @param xiaoilogs
	 * @param mode  终端情景模式(10-标准模式；20-睡眠模式；30-离家模式)
	 */
	public Xiaoi(Familygroup familygroup, String xname,
			Integer onlineState, String xiaoNumber, Integer xiaoType,
			String activationTime, String xiaoIp, Set<Xiaoilog> xiaoilogs,Integer mode) {
		this.familygroup = familygroup;
		this.xname = xname;
		this.onlineState = onlineState;
		this.xiaoNumber = xiaoNumber;
		this.xiaoType = xiaoType;
		this.activationTime = activationTime;
		this.xiaoIp = xiaoIp;
		this.xiaoilogs = xiaoilogs;
		this.mode=mode;
	}

	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "xid", unique = true, nullable = false)       //xid是主键,不能为空
	public Integer getXid() {
		return this.xid;
	}

	public void setXid(Integer xid) {
		this.xid = xid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	public Familygroup getFamilygroup() {
		return this.familygroup;
	}

	public void setFamilygroup(Familygroup familygroup) {
		this.familygroup = familygroup;
	}
	
	
//	//改为懒加载
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name = "roomId")
//	public Room getRoom() {
//		return room;
//	}
//
//	public void setRoom(Room room) {
//		this.room = room;
//	}

	@Column(name = "xname", length = 20)
	public String getXname() {
		return this.xname;
	}

	public void setXname(String xname) {
		this.xname = xname;
	}

	@Column(name = "onlineState")
	public Integer getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(Integer onlineState) {
		this.onlineState = onlineState;
	}

	@Column(name = "xiaoNumber", length = 20)
	public String getXiaoNumber() {
		return this.xiaoNumber;
	}

	public void setXiaoNumber(String xiaoNumber) {
		this.xiaoNumber = xiaoNumber;
	}

	@Column(name = "xiaoType", length = 20)
	public Integer getXiaoType() {
		return this.xiaoType;
	}

	public void setXiaoType(Integer xiaoType) {
		this.xiaoType = xiaoType;
	}

	
	@Column(name = "activationTime", length = 10)
	public String getActivationTime() {
		return this.activationTime;
	}

	public void setActivationTime(String activationTime) {
		this.activationTime = activationTime;
	}

	@Column(name = "xiaoIp", length = 50)
	public String getXiaoIp() {
		return this.xiaoIp;
	}

	public void setXiaoIp(String xiaoIp) {
		this.xiaoIp = xiaoIp;
	}
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "xiaoi")
	public Set<Xiaoilog> getXiaoilogs() {
		return this.xiaoilogs;
	}

	public void setXiaoilogs(Set<Xiaoilog> xiaoilogs) {
		this.xiaoilogs = xiaoilogs;
	}
	
	@Column(name = "mode", length = 20)
	public Integer getMode() {
		return this.mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	@Column(name = "volume", length = 20)
	public Integer getVolume() {
		return this.volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	
	

}