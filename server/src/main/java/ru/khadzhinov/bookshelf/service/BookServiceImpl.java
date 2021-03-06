package ru.khadzhinov.bookshelf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;

/* my imports */
import ru.khadzhinov.bookshelf.entity.Book;

@Repository
@Transactional
@Service("bookService")
public class BookServiceImpl implements IBookService {
	private IBookRepository bookRepository;
	
	@Autowired
	public void setBookRepository(IBookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> getAllBooks() {
		return Lists.newArrayList(bookRepository.findAll());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Book> getByName(String name) {
		return Lists.newArrayList(bookRepository.findByName(name));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> getByAuthor(String author) {
		return Lists.newArrayList(bookRepository.findByAuthor(author));
	}

	@Override
	@Transactional(readOnly = true)
	public Book findById(Long id) {
		return bookRepository.findOne(id);
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}
}
