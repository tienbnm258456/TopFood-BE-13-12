package com.demo.shop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.demo.shop.constant.StatusConstant;
import com.demo.shop.entity.Notification;
import com.demo.shop.entity.Product;
import com.demo.shop.repository.NotificationRepository;
import com.demo.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class ShopBeRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopBeRestApiApplication.class, args);
	}
	@Autowired
	ProductRepository productRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Value("${date.time}")
	private int dateTimeOut;
	@Scheduled(cron = "0 38 01 * * ?")
	void someJob() throws Exception {
		List<Product> list = productRepository.findByStatus(StatusConstant.STATUS_ACTIVE);

		for (Product produt : list) {
			Notification notification = new Notification();
			Date dateNow = new Date();
			LocalDateTime createdDate = produt.getCreatedDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = createdDate.format(formatter);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(formattedDateTime);
			long millis = date.getTime();
			long getDiff = dateNow.getTime() - millis;
			long getDaysDiff = TimeUnit.MILLISECONDS.toHours(getDiff);
			long timeOut = dateTimeOut * 24 - getDaysDiff;
			if (timeOut <= 48) {
				notification.setDescriptions("Sản phẩm " + produt.getProductName() + " còn " + timeOut + " giờ là hết hạn");
				notification.setStorageTime((int) getDaysDiff);
				notification.setCreateDate(dateNow);
				notification.setProductId(produt.getId());
				notificationRepository.save(notification);
			}
			if(produt.getQuantity() <= 10) {
				notification.setDescriptions("Sản phẩm " + produt.getProductName() + " còn sắp hết");
//				notification.setStorageTime((int) getDaysDiff);
				notification.setCreateDate(dateNow);
				notification.setProductId(produt.getId());
				notificationRepository.save(notification);
			}
		}

	}
}
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {

}

