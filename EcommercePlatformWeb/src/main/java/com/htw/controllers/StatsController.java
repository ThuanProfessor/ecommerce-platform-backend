package com.htw.controllers;

import com.htw.services.CategoryService;
import com.htw.services.StatsService;
import com.htw.services.StoreService;
import com.htw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatsController {

    @Autowired
    private StatsService statsService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private StoreService storeService;

    @GetMapping("/stats")
    public String index(Model model) {
        model.addAttribute("productRevenues", statsService.statsRevenueByProduct());
        model.addAttribute("users", userService.getUser());
        model.addAttribute("stores", storeService.getStores());

        return "stats";
    }
}
