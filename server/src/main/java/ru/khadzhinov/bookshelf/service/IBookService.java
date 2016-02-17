package ru.khadzhinov.bookshelf.service;

/* my imports */
import ru.khadzhinov.bookshelf.entity.Book;

import java.util.List;

public interface IBookService {
	List<Book> findAll();
	Book findById(Long id);
	Book save(Book book);
}