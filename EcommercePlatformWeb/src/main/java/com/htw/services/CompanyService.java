package com.htw.services;

import com.htw.pojo.Company;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Trung Hau
 */
public interface CompanyService {

    List<Company> listCompany();
    
    Company getCompanyById(int id);

    Company getCompanyByUsername();

    Company addOrUpdateCompany(Map<String, String> params, MultipartFile avatar);

    Company addOrUpdateCompany(Company company);
}
