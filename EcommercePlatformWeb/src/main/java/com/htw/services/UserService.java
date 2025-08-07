/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.htw.services;

import com.htw.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nguye
 */
public interface UserService extends UserDetailsService {

    List<User> getUser();
    List<User> getUsers(Map<String, String> params);

    List<User> getUserByRoleSeller();

    User getUserById(int id);

    User addOrUpdateUserInfo(User user);

    User getUserByUsername(String username);

    User addUser(Map<String, String> params, MultipartFile avatar);

    User updateUser(User user);

    boolean authenticate(String username, String password);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
