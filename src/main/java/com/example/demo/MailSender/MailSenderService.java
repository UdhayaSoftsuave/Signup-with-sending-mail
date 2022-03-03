package com.example.demo.MailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService implements MailSender {
	
	@Autowired
	private JavaMailSender javaMailSender ;

	@Override
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setFrom("udhayakumar.softsuave@gmail.com");
			helper.setTo(to);
			helper.setSubject("confirming the mail");
			javaMailSender.send(mimeMessage);
		}catch (MessagingException e) {
			System.out.println("Message not send ");
		}
		
		
	}

}
