package com.htw.services.impl;

import com.htw.repositories.StatsRepository;
import com.htw.services.StatsService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> statsRevenueByProduct() {
        return this.statsRepo.statsRevenueByProduct();
    }

    @Override
    public List<Object[]> statsCategory() {
        return this.statsRepo.statsCategory();
    }

    @Override
    public List<Object[]> statsRevenueByStore() {
        return this.statsRepo.statsRevenueByStore();
    }

    @Override
    public List<Object[]> statsRevenueAllStoreByMonth(Integer month) {
        System.err.println("Tháng: " + month);

        System.err.println(this.statsRepo.statsRevenueAllStoreByMonth(month));
        return this.statsRepo.statsRevenueAllStoreByMonth(month);
    }

    @Override
    public List<Object[]> statsRevenueAllStoreByQuarterAndYear(Integer quarter, Integer year) {
        return this.statsRepo.statsRevenueAllStoreByQuarterAndYear(quarter, year);
    }

    @Override
    public List<Object[]> statsRevenueStoreByUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return this.statsRepo.statsRevenueByStore(username);
    }

}
