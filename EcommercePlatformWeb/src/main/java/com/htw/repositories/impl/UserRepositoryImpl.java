package com.htw.repositories.impl;

import com.htw.pojo.User;
import com.htw.repositories.UserRepository;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.persistence.criteria.Predicate;

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
    public List<User> getUsers(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);


        Root<User> root = q.from(User.class);
        q.select(root);

        if(params != null){
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if(kw != null && !kw.isEmpty()){
                predicates.add(b.like(root.get("username"), String.format("%%%s%%", kw)));;
            }

            String role = params.get("role");
            if(role != null && !role.isEmpty()){
                predicates.add(b.equal(root.get("role"), role));
            }

            String isVerifiedStr = params.get("isVerified");
            if (isVerifiedStr != null && !isVerifiedStr.isEmpty()) {
                Boolean isVerified = "1".equals(isVerifiedStr) || "true".equalsIgnoreCase(isVerifiedStr);
                predicates.add(b.equal(root.get("isVerified"), isVerified));
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(Predicate[]::new));
            }

            String sort = params.getOrDefault("sort", "id");
            q.orderBy(b.desc(root.get(sort)));

        }

        Query query = session.createQuery(q);

        if(params != null){
            String page = params.get("page");
            if(page != null){
                int p = Integer.parseInt(page);
                int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "20"));

                int start = (p - 1) * pageSize;

                query.setFirstResult(start);
                query.setMaxResults(pageSize);
            }
        }

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
        s.merge(u);
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
    public List<User> getUser() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getUserByRoleSeller() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User WHERE role=: role");
        q.setParameter("role", "ROLE_SELLER");

        return q.getResultList();
    }

   

}
