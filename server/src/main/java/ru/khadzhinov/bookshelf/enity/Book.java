package ru.khadzhinov.bookshelf.entity;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
//import org.hibernate.annotations.Type;

//import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Table(name = "books")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "AUTHOR", nullable = false)
	private String author;
	
	@Column(name = "ISBN", nullable = false)
	private String isbn;
	
	/* конструктор класса */
	protected Book() {}
	
	public String getName() {
		return this.name;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getIsbn() {
		return this.isbn;
	}
	
	@Override
	public String toString() {
		return name + "," + author + "," + isbn;
	}
}