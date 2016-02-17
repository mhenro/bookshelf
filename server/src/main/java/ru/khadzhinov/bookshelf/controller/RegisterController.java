package ru.khadzhinov.bookshelf.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.khadzhinov.bookshelf.service.IUserService;

@RestController
public class RegisterController {
	private final Logger logger = LoggerFactory.getLogger(MainController.class); 
	private final IUserService userService;
	
	@Autowired
    public RegisterController(IUserService userService) {
        this.userService = userService;
    }
	
	/*
	@ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }*/
	
	@RequestMapping(value = {"/register"}, method = RequestMethod.GET)
	public String search(
	@RequestParam Map<String,String> allRequestParams, ModelMap model, HttpServletRequest request) {
		boolean isBot = true;
		
		//loop a Map
		String callback = "";
		for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
			if (entry.getKey().equals("callback")) {
				callback = entry.getValue();
			}
			if (entry.getKey().equals("captcha")) {
				logger.info("user captcha = " + entry.getValue());
				logger.info("correct captcha = " + request.getSession().getAttribute("captcha"));
				if (entry.getValue().equals(request.getSession().getAttribute("captcha"))) {
					isBot = false;
				}
			}
		}
		
		/* captcha is correct? */
		if (!isBot) {
			String json;
			try {
				json = new ObjectMapper().writeValueAsString(allRequestParams);			
			} catch (JsonProcessingException e) {
				json = "[]";
				e.printStackTrace();			
			}
			
			/* if jsonp then */
			if (callback.isEmpty()) {
				return json;
			}
			else {
				return callback + "(" + json + ")";
			}
		}
		/* captcha is wrong? */
		else {
			/* if jsonp then */
			if (callback.isEmpty()) {
				return "Error: captcha is wrong!";
			}
			else {
				return callback + "(Error: captcha is wrong!)";
			}
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