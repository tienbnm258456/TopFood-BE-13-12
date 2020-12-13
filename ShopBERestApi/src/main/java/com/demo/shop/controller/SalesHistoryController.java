package com.demo.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.entity.Order;
import com.demo.shop.response.SalesHistoryAllResponse;
import com.demo.shop.response.SalesHistoryDetailResponse;
import com.demo.shop.response.SalesHistoryResponse;
import com.demo.shop.service.SalesHistoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/history")
public class SalesHistoryController {

	@Autowired
	private SalesHistoryService salesHistoryService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<SalesHistoryAllResponse>> getHistoryAdmin(
			@RequestParam(value = "orderId", required = false, defaultValue = "") String order) {
		return ResponseEntity.ok().body(salesHistoryService.findHistoryAll(order));
	}

	@GetMapping
	public ResponseEntity<List<SalesHistoryResponse>> getAll(@RequestParam("userId") Integer userId,
			@RequestParam(value = "orderId", required = false, defaultValue = "") String order) {
		return ResponseEntity.ok().body(salesHistoryService.findHistoryByUser(userId, order));
	}

	@GetMapping(value = "/detail/{orderId}")
	public ResponseEntity<List<SalesHistoryDetailResponse>> getDetailHistory(
			@PathVariable(value = "orderId") Integer orderId,
			@RequestParam(value = "orderDetailId", required = false, defaultValue = "") String orderDetail) {
		return ResponseEntity.ok().body(salesHistoryService.findHistoryDetailByUser(orderId, orderDetail));
	}
	
	@DeleteMapping("/update/{id}")
    public ResponseEntity<Order> update(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(salesHistoryService.updateStatus(id));
    }
}
