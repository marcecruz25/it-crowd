package com.crowd.service;

import com.crowd.model.User;

import java.util.List;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
}
