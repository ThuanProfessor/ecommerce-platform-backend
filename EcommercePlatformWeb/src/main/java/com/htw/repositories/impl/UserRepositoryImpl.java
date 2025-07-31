
package com.htw.repositories.impl;

import com.htw.pojo.User;
import com.htw.repositories.UserRepository;

import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<User> getUsers() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM User", User.class);

        return query.getResultList();
    }

}
