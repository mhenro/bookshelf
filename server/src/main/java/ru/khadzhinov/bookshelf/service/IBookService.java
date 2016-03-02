package ru.khadzhinov.bookshelf.service;

/* my imports */
import ru.khadzhinov.bookshelf.entity.Book;

import java.util.List;

public interface IBookService {
	List<Book> getAllBooks();
	List<Book> getByName(String name);
	List<Book> getByAuthor(String author);
	Book findById(Long id);
	Book save(Book book);
}