package com.demo.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.response.NotificationResponse;
import com.demo.shop.service.NotificationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/notifications")
	public ResponseEntity<?> getAll() {
		List<NotificationResponse> list = notificationService.getAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
