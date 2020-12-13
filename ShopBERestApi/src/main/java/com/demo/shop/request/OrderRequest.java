package com.demo.shop.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

	@NotNull
	private Integer userId;

	private List<OrderDetailRequest> orderDetailRequests;

	@NotNull
	private Integer price;

	private String description;

	@NotBlank
	private String cancelUrl;

	@NotBlank
	private String successUrl;

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<OrderDetailRequest> getOrderDetailRequests() {
		return orderDetailRequests;
	}

	public void setOrderDetailRequests(List<OrderDetailRequest> orderDetailRequests) {
		this.orderDetailRequests = orderDetailRequests;
	}
}
