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
 * 管理员实体类
 * Administrate entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "administrate", catalog = "xiaoi")
public class Administrate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer aid; 	//管理员id
	private Role role;      //角色
	private String aname;   //管理员名字
	private String password;//管理员登入密码
	private String createdate;//创建时间
	private String loginLastTime;//最后的登入时间
	private Integer loginNumber; //登入次数
	private String realName;     //真实姓名
	private String phoneNumber;  //手机号
	private String sex;          //性别

	// Constructors

	/** default constructor */
	public Administrate() {
	}

	/** minimal constructor */
	public Administrate(String createdate) {
		this.createdate = createdate;
	}

	/** full constructor */
	public Administrate(Role role, String aname, String password,
			String createdate, String loginLastTime, Integer loginNumber,
			String realName, String phoneNumber, String sex) {
		this.role = role;
		this.aname = aname;
	 
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "aid", unique = true, nullable = false)
	public Integer getAid() {
		return this.aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rid")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "aname", length = 50)
	public String getAname() {
		return this.aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "createdate", nullable = false, length = 19)
	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Column(name = "loginLastTime", length = 19)
	public String getLoginLastTime() {
		return this.loginLastTime;
	}

	public void setLoginLastTime(String loginLastTime) {
		this.loginLastTime = loginLastTime;
	}

	@Column(name = "loginNumber")
	public Integer getLoginNumber() {
		return this.loginNumber;
	}

	public void setLoginNumber(Integer loginNumber) {
		this.loginNumber = loginNumber;
	}

	@Column(name = "realName", length = 20)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "phoneNumber", length = 20)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "sex", length = 10)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}