package ru.khadzhinov.bookshelf.service;

import org.springframework.data.repository.CrudRepository;

import ru.khadzhinov.bookshelf.entity.Shelf;

public interface IShelfRepository extends CrudRepository<Shelf, Long> {
}