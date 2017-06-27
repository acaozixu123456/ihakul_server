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

	private long id ;
	private long triggerTime;
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
	
	@Column(name = "triggerTime", length = 20)
	public long getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(long triggerTime) {
		this.triggerTime = triggerTime;
	}
	@Column(name = "classId")
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
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
		return "XiaoiMode [id=" + id + ", triggerTime=" + triggerTime
				+ ", classId=" + classId + ", orders=" + orders + ", eaNumber="
				+ eaNumber + ", argument=" + argument + ", mode=" + mode
				+ ", groupNumber=" + groupNumber + "]";
	}
	
	
}
