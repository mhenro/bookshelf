package ru.khadzhinov.bookshelf.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ru.khadzhinov.bookshelf.entity.MyUser;
import ru.khadzhinov.bookshelf.entity.Role;
import ru.khadzhinov.bookshelf.service.IUserService;

@CrossOrigin
@RestController
public class RegisterController {
	private final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final IUserService userService;
	private final JavaMailSender javaMailSender;
	
	@Autowired
    public RegisterController(IUserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }
	
	/* check verification token function */
	@RequestMapping(value = {"/email_confirm"}, method = RequestMethod.GET)
	public String checkToken(
	@RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {
		/* loop a Map */
		String token = "";
		for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
			if (entry.getKey().equals("token")) {
				token = entry.getValue();
				break;
			}
		}
		MyUser myUser = userService.getUserByToken(token);
		
		/* if user exist, then */
		if (myUser != null) {
			Date expireDate = myUser.getExpireDate();
			
			/* if token isn't expired, then */
			if (expireDate.after(new Date())) {
				myUser.setEnabled(true);
				
				/* save user in database */
				userService.save(myUser);
				
				return "<html><head><meta charset=\"utf-8\"></head><body>Активация аккаунта успешно произведена!<br>Теперь можете зайти на сайт <a href=\"http://82.209.91.137:8080/bookshelf/\">http://bookshelf/</a> и пользоваться полным доступом!</body></html>";
			}
			else {
				userService.remove(myUser.getEmail());
				return "<html><head><meta charset=\"utf-8\"></head><body>Срок действия активации истек! Пожалуйста, повторите регистрацию</body></html>";
			}
		}
		else {
			return "<html><head><meta charset=\"utf-8\"></head><body>Ошибка активации аккаунта! Повторите регистрацию еще раз, пожалуйста!</body></html>";
		}
	}
	
	/* register function*/
	@RequestMapping(value = {"/register"}, method = RequestMethod.POST)
	public RegisterErrors register(
	@RequestParam Map<String,String> allRequestParams, ModelMap model, HttpServletRequest request) {
		boolean isBot = true;
		
		/* loop a Map */
		String login = "";
		String password = "";
		for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
			if (entry == null) continue;
			if (entry.getKey() == null) continue;
			
			if (entry.getKey().equals("captcha")) {
				String userCaptcha = entry.getValue();
				if (request.getSession().getAttribute("captcha") == null) logger.error("session captcha = null!");
				String sessionCaptcha = request.getSession().getAttribute("captcha").toString();
				logger.info("user captcha = " + userCaptcha);
				logger.info("correct captcha = " + sessionCaptcha);
				if ((userCaptcha != null) && userCaptcha.equals(sessionCaptcha)) {
					isBot = false;
				}
			}
			if (entry.getKey().equals("login")) {
				login = entry.getValue();
			}
			if (entry.getKey().equals("password")) {
				password = entry.getValue();
			}
		}
		
		/* captcha is correct? */
		if (!isBot) {
			/* user exist? */
			MyUser myUser = userService.getUserByEmail(login);
			if (myUser != null) {
				RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.ERROR, "User already exist!");
				return registerErrors;
			}
			
			/* create user */
			myUser = new MyUser(login, password, Role.USER);
			
			/* send verification email */
			String subject = "Активация аккаунта";
			String text = "<html><head><meta charset=\"utf-8\"></head><body>Для активации вашего аккаунта, пожалуйста, перейдите по этой " +
					"<a href=\"http://82.209.91.137:8082/email_confirm?token=" + myUser.getToken() + 
					"\">ссылке</a>" +
					"\nЕсли вы не регистрировались на сайте mhenro.tk/bookshelf просто проигнорируйте это сообщение!</body></head>";
			try {
				sendEmail(myUser.getEmail(), subject, text);
			} catch (MessagingException|MailSendException|MailParseException e) {
				/* return json */
				RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.ERROR, "Mail is incorrect!");				
				return registerErrors;
			} 
			
			/* save user in database */
			userService.save(myUser);
			
			/* authentication */
			Collection<SimpleGrantedAuthority> authArr = new ArrayList<SimpleGrantedAuthority>();
			SimpleGrantedAuthority auth = new SimpleGrantedAuthority(myUser.getRole().toString());
			authArr.add(auth);
			
			//username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities
			UserDetails user = new User(myUser.getEmail(), password, myUser.getEnabled(), 
					true, true, true, authArr);
				
			Authentication authentication =  new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			/* return json */
			RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.SUCCESS, "User created!");			
			return registerErrors;
		}
		/* captcha is wrong? */
		else {
			RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.ERROR, "Captcha is wrong!");
			return registerErrors;
		}
	}
	
	/* procedure to send email */
	void sendEmail(String destAddr, String subject, String text) throws MessagingException {
		//JavaMailSenderImpl sender = new JavaMailSenderImpl();
		MimeMessage message = javaMailSender.createMimeMessage();

		/* use the true flag to indicate you need a multipart message */
		MimeMessageHelper helper = new MimeMessageHelper(message, false);
		helper.setTo(destAddr);
		helper.setSubject(subject);
		
		message.setContent(text, "text/html; charset=UTF-8");

		/* use the true flag to indicate the text included is HTML */
		//helper.setText(text, true);

		/* let's include the infamous windows Sample file (this time copied to c:/) */
		//FileSystemResource res = new FileSystemResource(new File("c:/Sample.jpg"));
		//helper.addInline("identifier1234", res);

		javaMailSender.send(message);
	}
	
	/* error codes */
	private static class RegisterErrorCodes {
		public static final int SUCCESS = 0; 
		public static final int ERROR = 1;
	}
	
	/* class for json answer */
	@SuppressWarnings("unused")
	private class RegisterErrors {
		private int code;
		private String msg;
		
		RegisterErrors(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public int getCode() {
			return code;
		}
		
		public void setCode(int code) {
			this.code = code;
		}
		
		public String getMsg() {
			return msg;
		}
		
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	/*
	 @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
    	logger.info("Listing books");
    	List<Book> books = bookService.findAll();
    	model.addAttribute("books", books);
    	logger.info("Books count=" + books.size());
    	
    	return "books/list";
    }
	 * */
}