package ru.khadzhinov.bookshelf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.khadzhinov.bookshelf.entity.MyUser;
import ru.khadzhinov.bookshelf.service.IUserService;

@RestController
public class SendMailController {	
	 private final JavaMailSender javaMailSender;
	 private final Logger logger = LoggerFactory.getLogger(SendMailController.class);
	 private final IUserService userService;

	@Autowired
	SendMailController(JavaMailSender javaMailSender, IUserService userService) {
	    this.javaMailSender = javaMailSender;
	    this.userService = userService;
	}
	
	/* sending email to */
	@RequestMapping("/sendmail")
    SimpleMailMessage send() {
		/* if user isn't activating then error */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUser myUser = userService.getUserByEmail(auth.getName());		
		if (!myUser.getEnabled()) {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo("error");
	        mailMessage.setFrom("error");
	        mailMessage.setSubject("");
	        mailMessage.setText("Account isn't enabled yet!");
	        return mailMessage;
		}
		
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("mhenro@gmail.com");
        mailMessage.setFrom("mhbookshelf@gmail.com");
        mailMessage.setSubject("Активация аккаунта");
        mailMessage.setText("Lorem ipsum dolor sit amet");
        javaMailSender.send(mailMessage);
        return mailMessage;
    }
}