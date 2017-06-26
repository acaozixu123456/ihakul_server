package com.xiaoai.entity;

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
 * Channel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "channel", catalog = "xiaoi")
public class Channel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cid;
	private String chanName; //频道名
	private String chanNumber;//频道号
	private Familygroup familygroup;

	// Constructors

	/** default constructor */
	public Channel() {
	}

	/** full constructor */
	public Channel(String chanName, String chanNumber, Familygroup familygroup) {
		this.chanName = chanName;
		this.chanNumber = chanNumber;
		this.familygroup=familygroup;
		
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "cid", unique = true, nullable = false)
	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	@Column(name = "chanName", length = 50)
	public String getChanName() {
		return this.chanName;
	}

	public void setChanName(String chanName) {
		this.chanName = chanName;
	}

	@Column(name = "chanNumber", length = 50)
	public String getChanNumber() {
		return this.chanNumber;
	}

	public void setChanNumber(String chanNumber) {
		this.chanNumber = chanNumber;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	public Familygroup getFamilygroup() {
		return familygroup;
	}

	public void setFamilygroup(Familygroup familygroup) {
		this.familygroup = familygroup;
	}

	

}