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
 * 家庭组用户关系对照类
 * FamilyUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "family_user", catalog = "xiaoi")
public class FamilyUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Familygroup familygroup;
	private Users users;
	private String dna;     //创建标识


	

	/** default constructor */
	public FamilyUser() {
	}

	/** full constructor */
	public FamilyUser(Familygroup familygroup, Users users,String dna) {
		this.familygroup = familygroup;
		this.users = users;
		this.dna=dna;
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
	@JoinColumn(name = "groupId")
	public Familygroup getFamilygroup() {
		return this.familygroup;
	}

	public void setFamilygroup(Familygroup familygroup) {
		this.familygroup = familygroup;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
  

	public String getDna() {
		return dna;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}
	
}