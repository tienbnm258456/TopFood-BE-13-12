package com.demo.shop.request;

import java.util.Date;


public class NotificationRequest {
	
	private Integer id;
	private String description;
	private Integer storageTime;
	private Date createDate;
	private Date updateAt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStorageTime() {
		return storageTime;
	}
	public void setStorageTime(Integer storageTime) {
		this.storageTime = storageTime;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
	
}
