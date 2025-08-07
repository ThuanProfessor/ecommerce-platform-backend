package com.htw.controllers;

import com.htw.repositories.StatsRepository;
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
    private StatsRepository statsRepo;

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("productRevenues", statsService.statsRevenueByProduct());
        model.addAttribute("statsCategoryByCountProduct", statsService.statsCategory());
        model.addAttribute("statsRevenueByStore", statsService.statsRevenueByStore());
        model.addAttribute("byMonth", statsRepo.statsRevenueByMonth());
        model.addAttribute("byYear", statsRepo.statsRevenueByYear());
        model.addAttribute("byQuarter", statsRepo.statsRevenueByQuarter());
        return "stats";
    }
}
