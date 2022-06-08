package com.example.revature.project1.services;

import com.example.revature.project1.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    boolean register(User us);
    User login (String Email, String password);
    void logout();
    List<User> getUsers();
    boolean isUserExists(int userId);
    User getUser(int userId);
    boolean updateUser(User user);
    boolean delete(int userId);
}
