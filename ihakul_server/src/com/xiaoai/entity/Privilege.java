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
 * 权限实体类
 * Privilege entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "privilege", catalog = "xiaoi")
public class Privilege implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private Role role;
	private String priName;
	private String description;

	// Constructors

	/** default constructor */
	public Privilege() {
	}

	/**
	 * 
	 * @param role 角色
	 * @param priName 权限名
	 * @param description 权限描述
	 */
	public Privilege(Role role, String priName, String description) {
		this.role = role;
		this.priName = priName;
		this.description = description;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rid")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "priName", length = 50)
	public String getPriName() {
		return this.priName;
	}

	public void setPriName(String priName) {
		this.priName = priName;
	}

	@Column(name = "description", length = 50)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}