package com.htw.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htw.pojo.User;
import com.htw.repositories.UserRepository;
import com.htw.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUser() {
        return this.userRepository.getUsers();
    }
}
