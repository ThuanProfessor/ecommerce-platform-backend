package com.htw.services.impl;

import com.htw.pojo.Company;
import com.htw.repositories.CompanyRepository;
import com.htw.services.CompanyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trung Hau
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepo;

    @Override
    public List<Company> listCompany() {
        return this.companyRepo.listCompany();
    }

}
