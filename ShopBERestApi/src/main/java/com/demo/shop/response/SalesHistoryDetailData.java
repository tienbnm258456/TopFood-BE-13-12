package com.demo.shop.response;

import java.util.Date;

public interface SalesHistoryDetailData {

	Integer getOrderDetailId();
	
	Integer getOrderId();
	
	String getUserName();

	String getProductName();

	Integer getTotalPrice();

	Integer getCountQuantity();

	Date getCreatedDate();

	String getName();

	String getDescription();

	String getAddress();

	String getPhone();
	
}
