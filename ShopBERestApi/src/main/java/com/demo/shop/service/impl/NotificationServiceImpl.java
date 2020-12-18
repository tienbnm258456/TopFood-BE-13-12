package com.demo.shop.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.demo.shop.constant.StatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.shop.entity.Notification;
import com.demo.shop.entity.Product;
import com.demo.shop.repository.NotificationRepository;
import com.demo.shop.repository.ProductRepository;
import com.demo.shop.response.NotificationResponse;
import com.demo.shop.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private ProductRepository productRepository;


	@Override
	public List<NotificationResponse> getAll() {
		List<Notification> list = notificationRepository.findNotificationByDate();
		List<NotificationResponse> notificationResponses = new ArrayList<>();
		for (Notification notification : list) {
			NotificationResponse response = new NotificationResponse();
			response.setId(notification.getId());
			response.setProduct(notification.getProduct());
			response.setStorageTime(notification.getStorageTime());
			response.setDescription(notification.getDescriptions());
			response.setUpdateDate(notification.getUpdateDate());
			response.setCreateDate(notification.getCreateDate());
			notificationResponses.add(response);
		}
		return notificationResponses;
	}

	
}
