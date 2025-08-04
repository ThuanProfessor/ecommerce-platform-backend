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

}
