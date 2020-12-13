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
import com.demo.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}

