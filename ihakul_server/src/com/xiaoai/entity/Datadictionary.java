package com.xiaoai.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 数据字典
 * Datadictionary entity.
 */

@Entity
@Table(name = "datadictionary", catalog = "xiaoi")
public class Datadictionary implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;     //数据字典id
	private String ddName;  //名字
	private Timestamp createTime; //创建时间
    private Integer checkedType;
 	// Constructors

	/** default constructor */
	public Datadictionary() {
	}

	/** full constructor */
	public Datadictionary(String ddName, Timestamp createTime,Integer checkedType) {
		this.ddName = ddName;
		this.createTime = createTime;
		this.checkedType=checkedType;
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

	@Column(name = "ddName", length = 50)
	public String getDdName() {
		return this.ddName;
	}

	public void setDdName(String ddName) {
		this.ddName = ddName;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Column(name = "checkedType", length = 2)
	public Integer getCheckedType() {
		return checkedType;
	}

	public void setCheckedType(Integer checkedType) {
		this.checkedType = checkedType;
	}
	
}