package com.htw.repositories.impl;

import com.htw.pojo.Company;
import com.htw.repositories.CompanyRepository;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Trung Hau
 */
@Repository
@Transactional
public class CompanyRepositoryImpl implements CompanyRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Company> listCompany() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Company", Company.class);
        return q.getResultList();
    }

    @Override
    public Company addOrUpdateCompany(Company company) {
        Session s = this.factory.getObject().getCurrentSession();

        if (company.getId() == null) {
            s.persist(company);
        } else {
            s.merge(company);
        }

        return company;
    }

    @Override
    public Company getCompanyByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        System.err.println(username);
        Query q = s.createQuery("FROM Company WHERE userId.username = :username", Company.class);
        q.setParameter("username", username);
        List<Company> list = q.getResultList();
        System.err.println(list);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Company getCompanyById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Company.class, id);
    }

}
