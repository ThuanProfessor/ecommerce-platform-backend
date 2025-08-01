package com.htw.services.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.htw.pojo.User;
import com.htw.repositories.UserRepository;
import com.htw.services.UserService;

import jakarta.transaction.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    

    @Override
    public List<User> getUser() {
        return this.userRepository.getUsers();
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
        u.setRole("ROLE_USER");

        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
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
}
