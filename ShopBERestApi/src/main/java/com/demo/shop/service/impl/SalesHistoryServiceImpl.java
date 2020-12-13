package com.demo.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.shop.constant.StatusConstant;
import com.demo.shop.entity.Order;
import com.demo.shop.repository.OrderDetailRepository;
import com.demo.shop.repository.OrderRepository;
import com.demo.shop.repository.SalesHistoryRepository;
import com.demo.shop.repository.UserRepository;
import com.demo.shop.response.SalesHistoryAllData;
import com.demo.shop.response.SalesHistoryAllResponse;
import com.demo.shop.response.SalesHistoryData;
import com.demo.shop.response.SalesHistoryDetailData;
import com.demo.shop.response.SalesHistoryDetailResponse;
import com.demo.shop.response.SalesHistoryResponse;
import com.demo.shop.service.SalesHistoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesHistoryServiceImpl implements SalesHistoryService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SalesHistoryRepository salesHistoryRepository;
	
	@Autowired
	OrderDetailRepository oderDetailRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public List<SalesHistoryResponse> findHistoryByUser(Integer userId, String order) {
		 userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
		 List<SalesHistoryData> salesHistorys = salesHistoryRepository.findHistoryByUser(userId, order);
		 if (salesHistorys.isEmpty()) {
				log.error("Không có dữ liệu");
			}
		// Convert sang dto
			List<SalesHistoryResponse> salesDTOs = new ArrayList<>();
			salesDTOs = salesHistorys.stream().map(a -> {
				SalesHistoryResponse saleDTO = new SalesHistoryResponse();
				saleDTO.setOrderId(a.getOrderId());
				saleDTO.setUserName(a.getUserName());
				saleDTO.setTotalPrice(a.getTotalPrice());
				saleDTO.setCountQuantity(a.getCountQuantity());
				saleDTO.setCreatedDate(a.getCreatedDate());
				saleDTO.setName(a.getName());
				saleDTO.setDescription(a.getDescription());
				saleDTO.setAddress(a.getAddress());
				saleDTO.setPhone(a.getPhone());
				return saleDTO;

			}).filter(ap -> ap != null).collect(Collectors.toList());

		return salesDTOs;
	}

	@Override
	public List<SalesHistoryDetailResponse> findHistoryDetailByUser(Integer orderId, String orderDetail) {
//		oderDetailRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("order id not found"));
		 List<SalesHistoryDetailData> salesHistorys = salesHistoryRepository.findHistoryDetailByUser(orderId, orderDetail);
		 if (salesHistorys.isEmpty()) {
				log.error("Không có dữ liệu");
			}
		// Convert sang dto
			List<SalesHistoryDetailResponse> salesDTOs = new ArrayList<>();
			salesDTOs = salesHistorys.stream().map(a -> {
				SalesHistoryDetailResponse saleDTO = new SalesHistoryDetailResponse();
				saleDTO.setOrderDetailId(a.getOrderDetailId());
				saleDTO.setOrderId(a.getOrderId());
				saleDTO.setUserName(a.getUserName());
				saleDTO.setProductName(a.getProductName());
				saleDTO.setTotalPrice(a.getTotalPrice());
				saleDTO.setCountQuantity(a.getCountQuantity());
				saleDTO.setCreatedDate(a.getCreatedDate());
				saleDTO.setName(a.getName());
				saleDTO.setDescription(a.getDescription());
				saleDTO.setAddress(a.getAddress());
				saleDTO.setPhone(a.getPhone());
				return saleDTO;

			}).filter(ap -> ap != null).collect(Collectors.toList());

		return salesDTOs;
	}
	
	@Override
	public List<SalesHistoryAllResponse> findHistoryAll(String order) {
		 List<SalesHistoryAllData> salesHistorys = salesHistoryRepository.findHistoryAll(order);
		 if (salesHistorys.isEmpty()) {
				log.error("Không có dữ liệu");
			}
		// Convert sang dto
			List<SalesHistoryAllResponse> salesDTOs = new ArrayList<>();
			salesDTOs = salesHistorys.stream().map(a -> {
				SalesHistoryAllResponse saleDTO = new SalesHistoryAllResponse();
				saleDTO.setOrderId(a.getOrderId());
				saleDTO.setUserName(a.getUserName());
				saleDTO.setCreatedDate(a.getCreatedDate());
				saleDTO.setTotalPrice(a.getTotalPrice());
				saleDTO.setCountQuantity(a.getCountQuantity());
				saleDTO.setName(a.getName());
				saleDTO.setDescription(a.getDescription());
				saleDTO.setAddress(a.getAddress());
				saleDTO.setPhone(a.getPhone());
				saleDTO.setStatus(a.getStatus());
				return saleDTO;

			}).filter(ap -> ap != null).collect(Collectors.toList());

		return salesDTOs;
	}

	@Override
	public Order updateStatus(Integer id) {
		Order order = orderRepository.findById(id)
		.orElseThrow(() -> new EntityNotFoundException("order with not found id  = " + id));
		order.setStatus(StatusConstant.STATUS_ACTIVE);
		orderRepository.save(order);
		return order;
	}

}
