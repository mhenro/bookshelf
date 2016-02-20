package ru.khadzhinov.bookshelf.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ru.khadzhinov.bookshelf.entity.MyUser;
import ru.khadzhinov.bookshelf.service.IUserService;

@RestController
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(MainController.class); 
	private final IUserService userService;
	
	@Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public RegisterErrors search(
	@RequestParam Map<String,String> allRequestParams, ModelMap model, HttpServletRequest request) {
		boolean isBot = true;
		
		/* loop a Map */
		String login = "";
		String password = "";
		for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
			if (entry.getKey().equals("captcha")) {
				logger.info("user captcha = " + entry.getValue());
				logger.info("correct captcha = " + request.getSession().getAttribute("captcha"));
				if (entry.getValue().equals(request.getSession().getAttribute("captcha"))) {
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
			MyUser myUser = userService.getUserByEmail(login);
			if (myUser == null) {
				RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.ERROR, "User not found!");
				return registerErrors;
			}
			if (!password.equals(myUser.getPasswordHash())) {
				RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.ERROR, "Password is incorrect!");
				return registerErrors;
			}
			
			Collection<SimpleGrantedAuthority> authArr = new ArrayList<SimpleGrantedAuthority>();
			SimpleGrantedAuthority auth = new SimpleGrantedAuthority(myUser.getRole().toString());
			authArr.add(auth);
			
			//username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities
			UserDetails user = new User(myUser.getEmail(), password, myUser.getEnabled(), 
					true, true, true, authArr);
				
			Authentication authentication =  new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			logger.debug("Logging in with {}", authentication.getPrincipal());
			SecurityContextHolder.getContext().setAuthentication(authentication);			
			RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.SUCCESS, "Log in successful!");
			
			return registerErrors;
		}
		/* captcha is wrong? */
		else {
			RegisterErrors registerErrors = new RegisterErrors(RegisterErrorCodes.ERROR, "Captcha is wrong!");
			return registerErrors;
		}
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
}