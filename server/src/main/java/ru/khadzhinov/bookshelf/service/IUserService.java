package ru.khadzhinov.bookshelf.service;

import java.util.List;

import ru.khadzhinov.bookshelf.entity.User;

public interface IUserService {
    User getUserById(long id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User save(User user);
   // User create(UserCreateForm form);
}