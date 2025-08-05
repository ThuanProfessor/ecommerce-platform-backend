package com.htw.repositories.impl;

import com.htw.pojo.User;
import com.htw.repositories.UserRepository;

import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private LocalSessionFactoryBean factory;

    UserRepositoryImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM User", User.class);

        return query.getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();

        Query q = s.createNamedQuery("User.findByUsername", User.class);

        q.setParameter("username", username);

        return (User) q.getSingleResult();
    }

    @Override
    public boolean authenticate(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();

        s.persist(u);

        return u;
    }

    @Override
    public User updateUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(u);
        return u;
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public User addOrUpdateUserInfo(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        if (user.getId() == null) {
            s.persist(user);
        } else {
            s.merge(user);
        }

        return user;
    }

    @Override
    public List<User> getUserByRoleSeller() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User WHERE role=: role");
        q.setParameter("role", "ROLE_SELLER");

        return q.getResultList();
    }

}
