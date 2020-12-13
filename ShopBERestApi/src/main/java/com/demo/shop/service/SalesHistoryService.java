package com.demo.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.shop.entity.Order;
import com.demo.shop.response.SalesHistoryAllResponse;
import com.demo.shop.response.SalesHistoryDetailResponse;
import com.demo.shop.response.SalesHistoryResponse;

@Service
public interface SalesHistoryService {
	
	public List<SalesHistoryResponse> findHistoryByUser(Integer userId, String order);
	
	public List<SalesHistoryDetailResponse> findHistoryDetailByUser(Integer orderId, String orderDetail);
	
	public List<SalesHistoryAllResponse> findHistoryAll(String order);
	
	public Order updateStatus(Integer id);
}
