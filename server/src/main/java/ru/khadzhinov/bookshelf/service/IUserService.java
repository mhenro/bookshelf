package ru.khadzhinov.bookshelf.service;

import java.util.List;

import ru.khadzhinov.bookshelf.entity.MyUser;

public interface IUserService {
    MyUser getUserById(long id);
    MyUser getUserByEmail(String email);
    List<MyUser> getAllUsers();
    MyUser save(MyUser user);
}