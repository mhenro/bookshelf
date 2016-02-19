package ru.khadzhinov.bookshelf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailController {	
	 private final JavaMailSender javaMailSender;
	 private final Logger logger = LoggerFactory.getLogger(SendMailController.class);

	@Autowired
	SendMailController(JavaMailSender javaMailSender) {
	    this.javaMailSender = javaMailSender;
	}
	
	/* sending email to */
	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/sendmail")
    SimpleMailMessage send() {        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("mhenro@gmail.com");
        mailMessage.setFrom("mhbookshelf@gmail.com");
        mailMessage.setSubject("Активация аккаунта");
        mailMessage.setText("Lorem ipsum dolor sit amet");
        javaMailSender.send(mailMessage);
        return mailMessage;
    }
}