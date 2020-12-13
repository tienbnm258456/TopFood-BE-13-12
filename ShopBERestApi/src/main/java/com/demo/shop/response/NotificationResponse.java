package com.demo.shop.response;

import java.util.Date;

import com.demo.shop.entity.Product;


public class NotificationResponse {
	
	private Integer id;
	private String description;
	private Integer storageTime;
	private Date createDate;
	private Date updateDate;
	private Product product;
	
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
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
