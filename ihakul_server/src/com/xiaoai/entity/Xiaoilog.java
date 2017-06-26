package com.xiaoai.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 小艾工作日志
 * Xiaoilog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "xiaoilog", catalog = "xiaoi")
public class Xiaoilog implements java.io.Serializable {

	// Fields

	
	private static final long serialVersionUID = 1L;
	private Integer lid; 
	private Xiaoi xiaoi; 
	private Household household; 
	private String usingDetails; //日志描述
	private Timestamp userTime;  //使用时间

	// Constructors

	/** default constructor */
	public Xiaoilog() {
	}

	/** full constructor */
	public Xiaoilog(Xiaoi xiaoi, Household household, String usingDetails,
			Timestamp userTime) {
		this.xiaoi = xiaoi;
		this.household = household;
		this.usingDetails = usingDetails;
		this.userTime = userTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "lid", unique = true, nullable = false)
	public Integer getLid() {
		return this.lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xid")
	public Xiaoi getXiaoi() {
		return this.xiaoi;
	}

	public void setXiaoi(Xiaoi xiaoi) {
		this.xiaoi = xiaoi;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hid")
	public Household getHousehold() {
		return this.household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	@Column(name = "usingDetails", length = 50)
	public String getUsingDetails() {
		return this.usingDetails;
	}

	public void setUsingDetails(String usingDetails) {
		this.usingDetails = usingDetails;
	}

	@Column(name = "userTime", length = 19)
	public Timestamp getUserTime() {
		return this.userTime;
	}

	public void setUserTime(Timestamp userTime) {
		this.userTime = userTime;
	}

}