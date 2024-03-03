package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(User user);
    void delete(Long id);
    void change(User user);
    List<User> listUsers();
    Optional<User> findUserByUsername(String username);
}
