package ru.khadzhinov.bookshelf.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.khadzhinov.bookshelf.entity.Book;
import ru.khadzhinov.bookshelf.entity.Shelf;
import ru.khadzhinov.bookshelf.service.IBookService;
import ru.khadzhinov.bookshelf.service.IShelfService;

@RestController
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final IShelfService shelfService;
	private final IBookService bookService;
		
	@Autowired
	public MainController(IShelfService shelfService, IBookService bookService) {
	    this.shelfService = shelfService;
	    this.bookService = bookService;
	}
	 
	/* get list of shelves */
	@RequestMapping(value = "/getshelves", headers="Accept=application/json")
	public List<Shelf> getShelves() {
        /*List<Shelf> l1 = new ArrayList<Shelf>();
        l1.add(new );
        l1.add(new Song(atr1,atr2....));
        l1.add(new Song(atr1,atr2....)); 
        return l1;*/
		
		List<Shelf> shelfArr = shelfService.getAllShelves();
		return shelfArr;
	}
	
	/* get list of books */
	@RequestMapping(value = "/getbooks", headers="Accept=application/json")
	public List<Book> getBooks() {		
		List<Book> bookArr = bookService.getAllBooks();
		return bookArr;
	} 
}