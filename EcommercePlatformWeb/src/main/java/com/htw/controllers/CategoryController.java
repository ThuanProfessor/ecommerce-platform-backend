/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.controllers;

import com.htw.pojo.Category;
import com.htw.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nguye
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService cateSer;

    @GetMapping("/categories")
    public String getCate(Model model) {
        model.addAttribute("categories", this.cateSer.getCates());
        return "category-list";
    }
    
    @GetMapping("/categories/add")
    public String addView(Model model){
        model.addAttribute("category", new Category());
        return "category-form";
    }

    @PostMapping("/categories/save")
    public String addCate(@ModelAttribute(value = "category") Category cate) {
        System.err.println("Thong tin cua cate: " + cate.getName() + " " + cate.getDescription());
        this.cateSer.addOrUpdateCategory(cate);
        return "redirect:/categories";
    }

    @GetMapping("/categories/{cateId}")
    public String viewCate(Model model, @PathVariable(value = "cateId") int id) {
        System.err.println(id);
        model.addAttribute("category", this.cateSer.getCategoryById(id));
        System.err.println("Category nè " + this.cateSer.getCategoryById(id));
        return "category-form";
    }

}
