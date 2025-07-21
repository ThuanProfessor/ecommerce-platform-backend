package com.htw.repositories.impl;

import com.htw.ecommerceplatform.HibernateConfigs;
import com.htw.pojo.User;
import jakarta.persistence.Query;
import org.hibernate.Session;

public class UserRepositoryImpl {
    public User getUserByUsername(String username) {
        try (Session s = HibernateConfigs.getFACTORY().openSession()) {
            Query q = s.createNamedQuery("User.findByUsername", User.class);
            q.setParameter("username", username);

            return (User) q.getSingleResult();
        }
    }
}
