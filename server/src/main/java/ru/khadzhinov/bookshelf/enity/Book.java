package ru.khadzhinov.bookshelf.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Table(name = "books")
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", nullable = false, updatable = false)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SHELF_ID")
	private Shelf shelf;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private MyUser myUser;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "AUTHOR", nullable = false)
	private String author;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "ISBN", unique = true)
	private String isbn;
	
	/* конструктор класса */
	protected Book() {}
	
	public Long getId() {
		return this.id;
	}
	
	public Shelf getShelf() {
		return this.shelf;
	}
	
	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}
	
	public MyUser getMyUser() {
		return this.myUser;
	}
	
	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getIsbn() {
		return this.isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	/*
	@Override
	public String toString() {
		return name + "," + author + "," + isbn;
	}
	*/
}