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

    Company addOrUpdateCompany(Map<String, String> params, MultipartFile avatar);
}
