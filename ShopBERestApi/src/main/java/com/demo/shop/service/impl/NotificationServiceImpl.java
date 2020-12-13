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

	@Scheduled(cron = "0 03 14 * * ?")
	void someJob() throws Exception {
		List<Product> list = productRepository.findByStatus(StatusConstant.STATUS_ACTIVE);

		for (Product produt : list) {
			Notification notification = new Notification();
			Date dateNow = new Date();
			LocalDateTime createdDate = produt.getCreatedDate();
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			String formattedDateTime = createdDate.format(formatter);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(formattedDateTime);
			long millis = date.getTime();
			long getDiff = dateNow.getTime() - millis;
			long getDaysDiff = TimeUnit.MILLISECONDS.toHours(getDiff);
			if (getDaysDiff > 0 && getDaysDiff < 48) {
				notification.setDescriptions("Sản phẩm " + produt.getProductName() + " còn " + getDaysDiff + " giờ là hết hạn");
				notification.setStorageTime((int) getDaysDiff);
				notification.setCreateDate(dateNow);
				notification.setProductId(produt.getId());
				notificationRepository.save(notification);

			}
		}

	}
}
