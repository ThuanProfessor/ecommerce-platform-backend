package com.htw.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.htw.pojo.User;

/**
 *
 * @author nguye
 */
public interface UserRepository {

    User getUserById(int id);
    
    User addOrUpdateUserInfo(User user);

    List<User> getUsers();

    User getUserByUsername(String username);

    User addUser(User u);

    User updateUser(User u);

    boolean authenticate(String username, String password);
}
