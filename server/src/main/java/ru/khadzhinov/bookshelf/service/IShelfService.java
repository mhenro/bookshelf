package ru.khadzhinov.bookshelf.service;

import java.util.List;

import ru.khadzhinov.bookshelf.entity.Shelf;

public interface IShelfService {
	Shelf getShelfById(long id);
    List<Shelf> getAllShelves();
    Shelf save(Shelf shelf);
}
