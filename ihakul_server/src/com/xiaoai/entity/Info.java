package com.xiaoai.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author ZERO
 * @Data 2017-7-3 上午10:24:35
 * @Description 公告实体类
 */

@Entity
@Table(name = "info", catalog = "xiaoi")
public class Info implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ID="content";
	public static final String CONTENT="content";
	public static final String CREAT_TIME="creatTime";
	public static final String CREATOR="creator";
	public static final String PUSH_TIME="pushTime";
	public static final String PUSH_STATE="pushState";
	public static final String OTHER_CONTENT="otherContent";
	
	private Integer id;
	/**
	 * 公告推送的文本内容
	 */
	private String content;
	/**
	 * 公告创建时间
	 */
	private Date creatTime;
	/**
	 * 公告创建者
	 */
	private String creator;
	/**
	 * 公告定时推送时间
	 */
	private Date pushTime;
	/**
	 * 公告推送状态
	 */
	private Integer pushState;
	/**
	 * 公告其他内容（图片地址等）
	 */
	private String otherContent;
	
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "creatTime")
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
	@Column(name = "creator", length = 20)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "pushTime")
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	
	@Column(name = "pushState")
	public Integer getPushState() {
		return pushState;
	}
	public void setPushState(Integer pushState) {
		this.pushState = pushState;
	}
	
	@Column(name = "otherContent",length=255)
	public String getOtherContent() {
		return otherContent;
	}
	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}
	@Override
	public String toString() {
		return "Info [id=" + id + ", content=" + content + ", creatTime="
				+ creatTime + ", creator=" + creator + ", pushTime=" + pushTime
				+ ", pushState=" + pushState + ", otherContent=" + otherContent
				+ "]";
	}
	
	
}
