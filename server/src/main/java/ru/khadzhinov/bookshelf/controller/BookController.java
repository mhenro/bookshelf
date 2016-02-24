/*package ru.khadzhinov.bookshelf.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.khadzhinov.bookshelf.service.*;
import ru.khadzhinov.bookshelf.entity.Book;

@CrossOrigin
@RequestMapping("/books")
@Controller
public class BookController {
	private final Logger logger = LoggerFactory.getLogger(BookController.class);
	private IBookService bookService;

	@Autowired
	public void setIBookService(IBookService bookService) {
		this.bookService = bookService;
	}
	
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
    	logger.info("Listing books");
    	List<Book> books = bookService.findAll();
    	model.addAttribute("books", books);
    	logger.info("Books count=" + books.size());
    	
    	return "books/list";
    }
}*/