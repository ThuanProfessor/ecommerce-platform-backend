/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.controllers;

import com.htw.services.StoreService;
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
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;
    
    @GetMapping("")
    public String listStore(Model model){
        model.addAttribute("stores", storeService.getStores());
        return "index";
    }
}
