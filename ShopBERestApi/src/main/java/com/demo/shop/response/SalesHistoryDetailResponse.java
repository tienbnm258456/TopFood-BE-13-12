package com.demo.shop.response;

import java.util.Date;

import lombok.Data;

@Data
public class SalesHistoryDetailResponse {

	private Integer orderDetailId;
	
	private Integer orderId;
	
	private String userName;

	private String productName;

	private Integer totalPrice;

	private Integer countQuantity;

	private Date createdDate;

	private String name;

	private String description;

	private String address;

	private String phone;

}
