package com.htw.repositories;

import com.htw.pojo.Company;
import java.util.List;

/**
 *
 * @author Trung Hau
 */
public interface CompanyRepository {

    List<Company> listCompany();

    Company getCompanyById(int id);

    Company getCompanyByUsername(String username);

    Company addOrUpdateCompany(Company company);

}
