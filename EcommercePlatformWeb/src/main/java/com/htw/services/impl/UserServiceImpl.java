package com.htw.services.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.htw.pojo.User;
import com.htw.repositories.UserRepository;
import com.htw.services.UserService;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<User> getUser() {
        return this.userRepository.getUser();
    }

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return this.userRepository.getUsers(params);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);

        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        u.setUsername(params.get("username"));
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setFullName(params.get("fullname"));
        u.setNumberPhone(params.get("number_phone"));

        u.setRole(params.get("role") != null ? params.get("role") : "ROLE_USER");

        // if (!avatar.isEmpty()) {
        //     try {
        //         Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        //         u.setAvatar(res.get("secure_url").toString());
        //     } catch (IOException ex) {
        //         Logger.getLogger(Us(erServiceImpl.class.getName()).logLevel.SEVERE, null, ex);
        //     }
        // }
        if (params.get("role").equals("ROLE_CUSTOMER")) {
            u.setIsVerified(Boolean.TRUE);
        } else {
            u.setIsVerified(Boolean.FALSE);
        }
        u.setRole(params.get("role"));

        if (!avatar.isEmpty()) {

            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (params.get("is_verified") != null) {
            u.setIsVerified("1".equals(params.get("is_verified")));
        }

        return this.userRepository.addUser(u);

    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepository.authenticate(username, password);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.updateUser(user);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.updateUser(user);
        return true;
    }

    @Override
    public User getUserById(int id) {
        return this.userRepository.getUserById(id);
    }

    @Override
    public User addOrUpdateUserInfo(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if (!user.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.userRepository.addOrUpdateUserInfo(user);
    }

    @Override
    public List<User> getUserByRoleSeller() {
        return this.userRepository.getUserByRoleSeller();
    }

    @Override
    public List<User> getUserNotVerified() {
        return this.userRepository.getUserNotVerified();
    }

    @Override
    public Boolean updateIsVerified(int userId) {
        return this.userRepository.updateIsVerified(userId);
    }
}
