/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.controllers;

import com.htw.pojo.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nguye
 */
@Controller
@Transactional
public class HomeController {
    @Autowired
    private LocalSessionFactoryBean factory;
    
    
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("msg", "He Thong Web");
        
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Category", Category.class);
        
        model.addAttribute("categories", q.getResultList());
        
        return "index";
    }
}
