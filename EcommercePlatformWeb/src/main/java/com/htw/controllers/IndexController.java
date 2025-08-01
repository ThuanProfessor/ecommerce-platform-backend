package com.htw.controllers;

import com.htw.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Trung Hau
 */
@Controller
@ControllerAdvice
public class IndexController {

    @Autowired
    private CategoryService cateService;

    @ModelAttribute
    public void commonCategory(Model model) {
        model.addAttribute("categories", this.cateService.getCates());
    }

    @RequestMapping("/")
    public String index(Model model) {
        System.err.println("Đây là trang chủ");
        System.err.println(model);
        return "index";
    }
}
