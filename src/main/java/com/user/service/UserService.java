package com.user.service;

import com.user.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers(String firstName);

    User getUser(String ssn);

    String addUser(User user);

    void deleteUser(String ssn);
}
