/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.controllers;

import com.htw.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nguye
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService cateSer;
    
    @GetMapping("")
    public String getCate(Model model){
        model.addAttribute("categories", cateSer.getCates());
        return "category-list";
        
    }
}
