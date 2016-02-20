package ru.khadzhinov.bookshelf.service;

import org.springframework.data.repository.CrudRepository;

import ru.khadzhinov.bookshelf.entity.MyUser;

public interface IUserRepository extends CrudRepository<MyUser, Long> {
    MyUser findOneByEmail(String email);
    MyUser findOneByToken(String token);
}