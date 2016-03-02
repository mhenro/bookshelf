package ru.khadzhinov.bookshelf.service;

/*my imports */
import java.util.List;

import ru.khadzhinov.bookshelf.entity.Book;

import org.springframework.data.repository.CrudRepository;

public interface IBookRepository extends CrudRepository<Book, Long> {
	List<Book> findByName(String name);
	List<Book> findByAuthor(String name);
}