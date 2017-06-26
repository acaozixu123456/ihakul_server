package com.xiaoai.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Xiaoiurl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "xiaoiurl", catalog = "xiaoi")
public class Xiaoiurl implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String url;
	private String urlRemark;
	private Integer urlNumber;
	private String other;

	// Constructors

	/** default constructor */
	public Xiaoiurl() {
	}

	/** minimal constructor */
	public Xiaoiurl(String url) {
		this.url = url;
	}

	/** full constructor */
	public Xiaoiurl(String url, String urlRemark, Integer urlNumber,
			String other) {
		this.url = url;
		this.urlRemark = urlRemark;
		this.urlNumber = urlNumber;
		this.other = other;
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

	@Column(name = "url", nullable = false, length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "urlRemark", length = 200)
	public String getUrlRemark() {
		return this.urlRemark;
	}

	public void setUrlRemark(String urlRemark) {
		this.urlRemark = urlRemark;
	}

	@Column(name = "urlNumber")
	public Integer getUrlNumber() {
		return this.urlNumber;
	}

	public void setUrlNumber(Integer urlNumber) {
		this.urlNumber = urlNumber;
	}

	@Column(name = "other", length = 50)
	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}