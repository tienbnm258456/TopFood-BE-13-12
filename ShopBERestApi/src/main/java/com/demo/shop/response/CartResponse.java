package com.demo.shop.response;

import lombok.Data;

@Data
public class CartResponse {
	private Integer id;

	private Integer productId;

	private Integer price;

	private String productName;

	private Integer userId;

	private Integer quantity;

	private String image;


}
