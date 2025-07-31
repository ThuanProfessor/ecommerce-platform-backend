package com.htw.repositories;

import java.util.List;

import com.htw.pojo.User;

/**
 *
 * @author nguye
 */
public interface UserRepository {
    List<User> getUsers();
}
