package com.demo.shop.response;

import java.util.Date;

public interface SalesHistoryAllData {

	Integer getOrderId();

	String getUserName();

	Integer getTotalPrice();

	Integer getCountQuantity();

	Date getCreatedDate();

	String getName();

	String getDescription();

	String getAddress();

	String getPhone();
	
	String getStatus();
	
}
