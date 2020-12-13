package com.demo.shop.service;

import com.demo.shop.entity.Order;
import com.demo.shop.request.CartRequest;

public interface OrderDetailService {
	public Order initOrder(CartRequest cart); 
}
