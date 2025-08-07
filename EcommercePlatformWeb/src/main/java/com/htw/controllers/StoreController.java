/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.controllers;

import com.htw.pojo.Store;
import com.htw.services.StoreService;
import com.htw.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nguye
 */
@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @GetMapping("/stores")
    public String listStore(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("stores", storeService.getStores(params));
        return "store-list";
    }

    @GetMapping("/stores/add")
    public String addStore(Model model) {
        model.addAttribute("store", new Store());
        model.addAttribute("users", this.userService.getUserByRoleSeller());
        return "store-form";
    }

    @PostMapping("/stores/save")
    public String add(@ModelAttribute(value = "store") Store store) {
        this.storeService.addOrUpdateStore(store);

        return "redirect:/stores";
    }

    @GetMapping("/stores/{storeId}")
    public String viewStoreDetail(Model model, @PathVariable(value = "storeId") int id) {
        model.addAttribute("users", this.userService.getUserByRoleSeller());
        System.err.println(this.userService.getUser());
        model.addAttribute("store", this.storeService.getStoreById(id));
        return "store-form";
    }
}
