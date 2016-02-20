package ru.khadzhinov.bookshelf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shelves")
public class Shelf {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHELF_ID", nullable = false, updatable = false)
    private Long id;
	
	@Column(name = "NAME", nullable = false, unique = true)
	private String name;
	
	@Column(name = "DESCR", nullable = false)
	private String description;
	
	/* constructors */
	Shelf() {}
	Shelf(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/* setters and getters */
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
