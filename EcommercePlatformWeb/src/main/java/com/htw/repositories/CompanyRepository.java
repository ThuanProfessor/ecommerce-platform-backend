package com.htw.repositories;

import com.htw.pojo.Company;
import java.util.List;

/**
 *
 * @author Trung Hau
 */
public interface CompanyRepository {

    List<Company> listCompany();

    Company addOrUpdateCompany(Company company);

}
