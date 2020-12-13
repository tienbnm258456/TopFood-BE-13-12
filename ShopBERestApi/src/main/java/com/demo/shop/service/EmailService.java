package com.demo.shop.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.demo.shop.entity.EmailMessage;

public interface EmailService {
	public void sendMail(EmailMessage emailMessage) throws AddressException, MessagingException;
}
