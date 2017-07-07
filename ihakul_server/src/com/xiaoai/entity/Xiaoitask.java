package com.xiaoai.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Xiaoitask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "xiaoitask", catalog = "xiaoi")
public class Xiaoitask implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String ID = "id";
	public static final String CREATION_TIME = "creationTime";
	public static final String TRIGGER_TIME = "triggerTime";
	public static final String THINGS = "things";
	public static final String RULES = "rules";
	public static final String OBJECT = "object";
	public static final String ORDERS = "orders";
	public static final String GROUP_ID = "groupId";
	
	private long id;
	private long creationTime;
	private long triggerTime;
	private String things;
	private String rules;
	private String object;
	private String orders;
	private Integer groupId;

	public Xiaoitask() {
	}

	public Xiaoitask(long creationTime, long triggerTime, String things,
			String rules, String object,  Integer groupId,String orders) {
		this.creationTime = creationTime;
		this.triggerTime = triggerTime;
		this.things = things;
		this.rules = rules;
		this.object = object;
		this.groupId = groupId;
		this.orders = orders;
	}

	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "creationTime")
	public long getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name = "triggerTime")
	public long getTriggerTime() {
		return this.triggerTime;
	}

	public void setTriggerTime(long triggerTime) {
		this.triggerTime = triggerTime;
	}

	@Column(name = "things", length = 100)
	public String getThings() {
		return this.things;
	}

	public void setThings(String things) {
		this.things = things;
	}

	@Column(name = "orders", length = 20)
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}
	
	@Column(name = "rules", length = 50)
	public String getRules() {
		return this.rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	@Column(name = "object", length = 50)
	public String getObject() {
		return this.object;
	}

	public void setObject(String object) {
		this.object = object;
	}


	@Column(name = "groupId")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}


}