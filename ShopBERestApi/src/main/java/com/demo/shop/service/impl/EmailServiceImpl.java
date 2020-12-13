package com.demo.shop.service.impl;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.shop.entity.EmailMessage;
import com.demo.shop.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Value("${gmail.username}")
	private String username;
	@Value("${gmail.password}")
	private String password;
	
	@Override
	public void sendMail(EmailMessage emailmessage) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username, false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailmessage.getEmail()));
		msg.setSubject(emailmessage.getSubject());
		msg.setContent(emailmessage.getBody(), "text/html");
		msg.setSentDate(new Date());
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(emailmessage.getBody(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
//		MimeBodyPart attachPart = new MimeBodyPart();

//		attachPart.attachFile("C:\\talk2amareswaran-downloads\\mysql2.png");

//		multipart.addBodyPart(attachPart);
		msg.setContent(multipart);
		// sends the e-mail
		Transport.send(msg);
		
	}

	
}
