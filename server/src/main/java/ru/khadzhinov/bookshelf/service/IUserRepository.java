package ru.khadzhinov.bookshelf.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.khadzhinov.bookshelf.entity.MyUser;

public interface IUserRepository extends CrudRepository<MyUser, Long> {
    MyUser findOneByEmail(String email);
    MyUser findOneByToken(String token);
    List<MyUser> deleteByEmail(String email);
}