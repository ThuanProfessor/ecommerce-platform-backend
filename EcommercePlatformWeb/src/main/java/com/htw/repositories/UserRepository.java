package com.htw.repositories;

import java.util.List;

import com.htw.pojo.User;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nguye
 */
public interface UserRepository {

    List<User> getUsers();

    User getUserByUsername(String username);

    User addUser(Map<String, String> params, MultipartFile avatar);

    boolean authenticate(String username, String password);
}
