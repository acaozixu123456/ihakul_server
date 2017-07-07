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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * 用户类
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "users", catalog = "xiaoi")
public class Users implements java.io.Serializable {

	// Fields

	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String USER_PASSWORD = "userPassword";
	public static final String ACCESSPER = "accessper";
	public static final String USER_PHONE_NUMBER = "userPhoneNumber";
	public static final String USER_SEX = "userSex";
	public static final String CREATE_TIME = "createTime";
	public static final String LOGIN_LAST_TIMES = "loginLastTimes";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;  //用户id
	private String userName;  //用户名
	private String userPassword;  //用户密码
	private Integer accessper;    //用户登入状态
	private String userPhoneNumber;  //用户手机号
	private String userSex;     //用户性别
	private Timestamp createTime; //创建时间
	private Timestamp loginLastTimes; //最后登入时间
	private Set<FamilyUser> familyUsers = new HashSet<FamilyUser>(0);
	private Set<Familygroup> familygroup=new HashSet<Familygroup>(0);
	// Constructors
	
	
	
	/** default constructor */
	public Users() {
	}

	/** full constructor */
	public Users( String userName, String userPassword,
			Integer accessper, String userPhoneNumber, String userSex,
			Timestamp createTime, Timestamp loginLastTimes,
			Set<FamilyUser> familyUsers,Set<Familygroup> familygroup) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.accessper = accessper;
		this.userPhoneNumber = userPhoneNumber;
		this.userSex = userSex;
		this.createTime = createTime;
		this.loginLastTimes = loginLastTimes;
		this.familyUsers = familyUsers;
		this.familygroup=familygroup;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "userId", unique = true, nullable = false) //userId 是唯一标示 不能为空
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	@Column(name = "userName", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "userPassword", length = 20)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "accessper")
	public Integer getAccessper() {
		return this.accessper;
	}

	public void setAccessper(Integer accessper) {
		this.accessper = accessper;
	}

	@Column(name = "userPhoneNumber", length = 50)
	public String getUserPhoneNumber() {
		return this.userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	@Column(name = "userSex", length = 10)
	public String getUserSex() {
		return this.userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "loginLastTimes", length = 19)
	public Timestamp getLoginLastTimes() {
		return this.loginLastTimes;
	}

	public void setLoginLastTimes(Timestamp loginLastTimes) {
		this.loginLastTimes = loginLastTimes;
	}
	//当前是急加载  查询速度慢  改成懒加载 试试
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	public Set<FamilyUser> getFamilyUsers() {
		return this.familyUsers;
	}

	public void setFamilyUsers(Set<FamilyUser> familyUsers) {
		this.familyUsers = familyUsers;
	}
	
	//当前是急加载  查询速度慢  改成懒加载 试试
	@ManyToMany(cascade =CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="users")
	public Set<Familygroup> getFamilygroup() { 
		return familygroup;
	}
	
	public void setFamilygroup(Set<Familygroup> familygroup) {
		this.familygroup = familygroup;
	}

	
	
}