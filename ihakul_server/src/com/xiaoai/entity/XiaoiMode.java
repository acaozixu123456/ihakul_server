package com.xiaoai.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author ZERO
 * @Data 2017-6-22 下午6:12:20
 * @Description 
 */
@Entity
@Table(name = "xiaoimode", catalog = "xiaoi")
public class XiaoiMode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ID = "id";
	public static final String TRIGGER = "trigger";
	public static final String CLASSID = "classId";
	public static final String ORDERS = "orders";
	public static final String EA_NUMBER = "eaNumber";
	public static final String ARGUMENT = "argument";
	public static final String MODE = "mode";
	public static final String GROUP_NUMBER = "groupNumber";
	
	private long id ;
	private long trigger;
	private int classId;
	private String orders;
	private String eaNumber;
	private String argument;
	private int mode;
	private int groupNumber;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)      
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	@Column(name = "classId")
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	@Column(name = "triggerTime", length = 20)
	public long getTrigger() {
		return trigger;
	}
	public void setTrigger(long trigger) {
		this.trigger = trigger;
	}
	
	
	@Column(name = "orders",length = 10)
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	@Column(name = "eaNumber",length = 100)
	public String getEaNumber() {
		return eaNumber;
	}
	public void setEaNumber(String eaNumber) {
		this.eaNumber = eaNumber;
	}
	
	@Column(name = "argument",length = 32)
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	
	@Column(name = "mode")
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	@Column(name = "groupNumber")
	public int getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}
	@Override
	public String toString() {
		return "XiaoiMode [id=" + id + ", trigger=" + trigger
				+ ", classId=" + classId + ", orders=" + orders + ", eaNumber="
				+ eaNumber + ", argument=" + argument + ", mode=" + mode
				+ ", groupNumber=" + groupNumber + "]";
	}
	
	
}
