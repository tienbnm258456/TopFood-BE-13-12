package com.demo.shop.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.entity.EmailMessage;
import com.demo.shop.service.EmailService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class EmailController {
	
	@Autowired
	EmailService emailService;
	
	@PostMapping("/send-email")
	public String sendEmail(@RequestBody EmailMessage emailMessage) {
		try {
			emailService.sendMail(emailMessage);
			return "OK";
		} catch (MessagingException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
