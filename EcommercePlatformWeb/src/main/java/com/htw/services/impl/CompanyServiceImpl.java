package com.htw.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.htw.pojo.Company;
import com.htw.repositories.CompanyRepository;
import com.htw.repositories.UserRepository;
import com.htw.services.CompanyService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Trung Hau
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Company> listCompany() {
        return this.companyRepo.listCompany();
    }

    @Override
    public Company addOrUpdateCompany(Map<String, String> params, MultipartFile avatar) {
        Company company = new Company();
        company.setName(params.get("name"));
        company.setTax(params.get("tax"));
        company.setType(params.get("type"));
        company.setUserId(this.userRepo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                company.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.companyRepo.addOrUpdateCompany(company);
    }

    @Override
    public Company getCompanyByUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.err.println("username: " + username);
        return this.companyRepo.getCompanyByUsername(username);
    }

    @Override
    public Company addOrUpdateCompany(Company company) {
        if (!company.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(company.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                company.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.companyRepo.addOrUpdateCompany(company);
    }

    @Override
    public Company getCompanyById(int id) {
        return this.companyRepo.getCompanyById(id);
    }

}
