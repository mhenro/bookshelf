package ru.khadzhinov.bookshelf.service;

/*my imports */
import ru.khadzhinov.bookshelf.entity.Book;

import org.springframework.data.repository.CrudRepository;

public interface IBookRepository extends CrudRepository<Book, Long> {
}
