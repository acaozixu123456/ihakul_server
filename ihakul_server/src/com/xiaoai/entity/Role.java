package com.xiaoai.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 角色实体类
 * Role entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "role", catalog = "xiaoi")
public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer rid;
	private String rolename;
	private Set<Privilege> privileges = new HashSet<Privilege>(0);
	private Set<Administrate> administrates = new HashSet<Administrate>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/**
	 * 
	 * @param rolename 角色名
	 * @param privileges 
	 * @param administrates 
	 */
	public Role(String rolename, Set<Privilege> privileges,
			Set<Administrate> administrates) {
		this.rolename = rolename;
		this.privileges = privileges;
		this.administrates = administrates;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "rid", unique = true, nullable = false)
	public Integer getRid() {
		return this.rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Column(name = "rolename", length = 50)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
	public Set<Privilege> getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
	public Set<Administrate> getAdministrates() {
		return this.administrates;
	}

	public void setAdministrates(Set<Administrate> administrates) {
		this.administrates = administrates;
	}

}