package ru.khadzhinov.bookshelf.service;

import org.springframework.data.repository.CrudRepository;

import ru.khadzhinov.bookshelf.entity.User;

public interface IUserRepository extends CrudRepository<User, Long> {
    User findOneByEmail(String email);
}