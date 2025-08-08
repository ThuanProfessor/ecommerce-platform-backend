package com.htw.controllers;

import com.htw.pojo.Company;
import com.htw.services.CompanyService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Trung Hau
 */
@Controller
@RequestMapping("/api")
public class ApiCompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/secure/company")
    public ResponseEntity<Company> create(@RequestParam Map<String, String> params,
            @RequestParam(value = "avatar") MultipartFile avatar) {
        return new ResponseEntity<>(this.companyService.addOrUpdateCompany(params, avatar), HttpStatus.CREATED);
    }

    @GetMapping("/secure/company")
    public ResponseEntity<Company> getMyCompany() {
        return new ResponseEntity<>(this.companyService.getCompanyByUsername(), HttpStatus.OK);
    }
}
