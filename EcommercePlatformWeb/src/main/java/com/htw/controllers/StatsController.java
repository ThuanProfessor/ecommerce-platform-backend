package com.htw.controllers;

import com.htw.repositories.StatsRepository;
import com.htw.services.CategoryService;
import com.htw.services.StatsService;
import com.htw.services.StoreService;
import com.htw.services.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private StatsRepository statsRepo;

    @GetMapping("/stats")
    public String stats(Model model,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "quarter", required = false) Integer quarter,
            @RequestParam(value = "year", required = false) Integer year) {

        int currentYear = LocalDate.now().getYear();
        List<Integer> years = IntStream.rangeClosed(2022, currentYear)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("years", years);

        if (month != null && quarter == null && year == null) {
            model.addAttribute("statsRevenueAllStoreByMonth", statsRepo.statsRevenueAllStoreByMonth(month));
        }

        if (quarter != null && year != null) {
            model.addAttribute("statsRevenueAllStoreByQuarterAndYear", statsRepo.statsRevenueAllStoreByQuarterAndYear(quarter, year));
        }
        model.addAttribute("productRevenues", statsService.statsRevenueByProduct());
        model.addAttribute("statsCategoryByCountProduct", statsService.statsCategory());
        model.addAttribute("statsRevenueByStore", statsService.statsRevenueByStore());
        model.addAttribute("byMonth", statsRepo.statsRevenueByMonth());
        model.addAttribute("byYear", statsRepo.statsRevenueByYear());
        model.addAttribute("byQuarter", statsRepo.statsRevenueByQuarter());
        return "stats";
    }
}
