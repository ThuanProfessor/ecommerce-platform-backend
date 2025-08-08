package com.htw.controllers;

import com.htw.pojo.Company;
import com.htw.services.CompanyService;
import com.htw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Trung Hau
 */
@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @GetMapping("/companies")
    public String listCompanies(Model model) {
        model.addAttribute("companies", this.companyService.listCompany());

        return "company-list";
    }

    @GetMapping("companies/add")
    public String addView(Model model) {
        model.addAttribute("users", this.userService.getUserByRoleSeller());
        model.addAttribute("company", new Company());

        return "company-form";
    }

    @PostMapping("/companies/save")
    public String add(@ModelAttribute(value = "company") Company company) {
        this.companyService.addOrUpdateCompany(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/{companyId}")
    public String viewCompanyDetail(Model model, @PathVariable(value = "companyId") int id) {
        model.addAttribute("company", this.companyService.getCompanyById(id));
        return "company-form";
    }
}
