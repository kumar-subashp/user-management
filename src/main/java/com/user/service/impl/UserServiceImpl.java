package com.user.service.impl;

import com.user.model.User;
import com.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users = new CopyOnWriteArrayList<>();

    @Override
    public List<User> getAllUsers(String firstName) {
        if (firstName == null || firstName.trim() == "") {
            return users;
        }

        return users.stream()
                .filter(input -> input.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(String ssn) {
        return users.stream()
                .filter(x -> x.getSsn().equals(ssn))
                .findFirst().get();
    }

    @Override
    public String addUser(User user) {
        users.add(user);
        return user.getSsn();
    }

    @Override
    public void deleteUser(String ssn) {
        User user = new User();
        user.setSsn(ssn);
        users.remove(user);
    }
}
