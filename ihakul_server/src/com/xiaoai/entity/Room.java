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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.junit.Ignore;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 房间实体类
 * Room entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "room", catalog = "xiaoi")
public class Room implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String ID = "id";
	public static final String FAMILY_GROUP = "familygroup";
	public static final String ROOM_NAME = "roomName";
	public static final String CREAT_TIME = "createTime";
	public static final String ROOM_NICK_NAME = "roomNickName";
	public static final String FLOOR = "floor";
	public static final String PARENT_ID = "parentId";
	public static final String ROOM_NUMBER = "roomNumber";
	public static final String ROBOT = "robot";
	public static final String CREATOR = "creator";
	
	private Integer id;               //房间id
	@JSONField(serialize=false)
	private Familygroup familygroup;  //家庭组对象
	private String roomName;		  //房间名字
	private Timestamp createTime;     //创建时间
	private String roomNickName;      //别名昵称
	private Integer floor;      //房间楼层(默认 0)
	private String parentId;      //父节点标识
	private String roomNumber;      //房间编号
	private String robot;      //终端绑定
	private String creator;      //终端创建
	
	@JSONField(serialize=false)
	private Set<Household> households = new HashSet<Household>(0);
	public Room() {
	 }

	/** 
	 * 
	 * @param familygroup 家庭组对象
	 * @param roomName    房间名字
	 * @param createTime  创建时间
	 * @param households  
	 * @param xiaois     
	 * @param roomNickName  别名昵称
	 * @param floor  房间楼层(默认 0)
	 * @param parentId  父节点标识
	 * @param roomNumber  房间编号
	 * @param robot  终端编号
	 * 
	 */
	public Room(Familygroup familygroup, String roomName, Timestamp createTime,
			Set<Household> households,Set<Xiaoi> xiaois,String roomNickName,
			Integer floor,String parentId,String roomNumber,String robot,String creator) {
		this.familygroup = familygroup;
		this.roomName = roomName;
		this.createTime = createTime;
		this.households = households;
		this.roomNickName=roomNickName;
		this.floor=floor;
		this.parentId=parentId;
		this.roomNumber=roomNumber;
		this.robot=robot;
		this.creator=creator;
	}
  
	@Column(name = "creator", length = 20)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "robot", length = 20)
	public String getRobot() {
		return robot;
	}

	public void setRobot(String robot) {
		this.robot = robot;
	}

	@Column(name = "roomNumber", length = 20)
	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Column(name = "floor", length = 10)
	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	@Column(name = "parentId", length = 20)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

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
  
	//改为懒加载
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId")
	public Familygroup getFamilygroup() {
		return this.familygroup;
	}

	public void setFamilygroup(Familygroup familygroup) {
		this.familygroup = familygroup;
	}
     

	@Column(name = "roomName",  length = 20)
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "roomNickName", length = 50)
	public String getRoomNickName() {
		return roomNickName;
	}

	public void setRoomNickName(String roomNickName) {
		this.roomNickName = roomNickName;
	}
	   //改为懒加载
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
	public Set<Household> getHouseholds() {
		return this.households;
	}

	public void setHouseholds(Set<Household> households) {
		this.households = households;
	}
	
}