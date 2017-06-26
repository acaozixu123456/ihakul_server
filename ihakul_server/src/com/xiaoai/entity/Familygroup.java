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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 家庭组
 * Familygroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "familygroup", catalog = "xiaoi")
public class Familygroup implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer groupId; //家庭组id
	private String groupName; //家庭组名字
	private Timestamp creationTime; //创建时间
	private Integer groupNumber;   //家庭组编号
	private String state;         //国家
	private String city;          //城市
	private String district;      //街道
	private String groupPassword;  //验证密码
	private Integer managerId;     //创建家庭组的用户id
	private Integer versionNumber; //版本号
	private Set<Household> households = new HashSet<Household>(0);
	private Set<Room> rooms = new HashSet<Room>(0);
	private Set<FamilyUser> familyUsers = new HashSet<FamilyUser>(0);
	private Set<Xiaoi> xiaois = new HashSet<Xiaoi>(0);
	private Set<Users> users=new HashSet<Users>(0);
	private Set<Channel> channels=new HashSet<Channel>(0);
	// Constructors

	/** default constructor */
	public Familygroup() {
	}

	/** full constructor */
	public Familygroup(String groupName, Timestamp creationTime,
			Integer groupNumber, String state, String city, String district,
			String groupPassword, Set<Household> households, Set<Room> rooms,
			Set<FamilyUser> familyUsers, Set<Xiaoi> xiaois,Set<Users> users,
			Set<Channel> channels,Integer managerId,Integer versionNumber) {
		this.groupName = groupName;
		this.creationTime = creationTime;
		this.groupNumber = groupNumber;
		this.state = state;
		this.city = city;
		this.district = district;
		this.groupPassword = groupPassword;
		this.households = households;
		this.rooms = rooms;
		this.familyUsers = familyUsers;
		this.xiaois = xiaois;
		this.users=users;
		this.channels=channels;
		this.managerId=managerId;
		this.versionNumber=versionNumber;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
   
	

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "groupId", unique = true, nullable = false)
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "groupName", length = 20)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "creationTime", length = 19)
	public Timestamp getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	
	@Column(name = "groupNumber", length = 30)
	public Integer getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}

	@Column(name = "state", length = 20)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "district", length = 50)
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "groupPassword", length = 20)
	public String getGroupPassword() {
		return this.groupPassword;
	}

	public void setGroupPassword(String groupPassword) {
		this.groupPassword = groupPassword;
	}
    
	
	//当前是急加载  EAGER  查询速度慢  改成懒加载  LAZY 试试
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "familygroup")
	public Set<Household> getHouseholds() {
		return this.households;
	}

	public void setHouseholds(Set<Household> households) {
		this.households = households;
	}
	//当前是急加载  EAGER  查询速度慢  改成懒加载  LAZY 试试
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "familygroup")
	public Set<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}
	//当前是急加载  EAGER  查询速度慢  改成懒加载  LAZY 试试
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "familygroup")
	public Set<FamilyUser> getFamilyUsers() {
		return this.familyUsers;
	}

	public void setFamilyUsers(Set<FamilyUser> familyUsers) {
		this.familyUsers = familyUsers;
	}
	//当前是急加载  EAGER  查询速度慢  改成懒加载  LAZY 试试
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "familygroup")
	public Set<Xiaoi> getXiaois() {
		return this.xiaois;
	}

	public void setXiaois(Set<Xiaoi> xiaois) {
		this.xiaois = xiaois;
	}
	
	//当前是急加载  EAGER  查询速度慢  改成懒加载  LAZY 试试
	@JsonIgnore
	@ManyToMany(cascade =CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="family_user",joinColumns={@JoinColumn(name="groupId")},inverseJoinColumns={@JoinColumn(name="userid")})
	public Set<Users> getUsers() {
		return users;
	}
	@JsonIgnore
	public void setUsers(Set<Users> users) {
		this.users = users;
	}
	//当前是急加载  EAGER  查询速度慢  改成懒加载  LAZY 试试
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "familygroup")
	public Set<Channel> getChannels() {
		return channels;
	}

	public void setChannels(Set<Channel> channels) {
		this.channels = channels;
	}
	
}