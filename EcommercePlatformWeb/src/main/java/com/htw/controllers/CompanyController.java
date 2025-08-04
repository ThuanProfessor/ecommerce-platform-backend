package com.htw.controllers;

import com.htw.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Trung Hau
 */
@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public String listCompanies(Model model) {
        model.addAttribute("companies", this.companyService.listCompany());

        return "company-list";
    }
}
