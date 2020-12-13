package com.demo.shop.response;

import lombok.Data;

@Data
public class SalesReportResponse {

	private Integer id;
	
	private String productName;
	
	private Integer totalPrice;
	
	private Integer countQuantity;
	
	private Integer totalSales;
}
