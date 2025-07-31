package com.htw.services.impl;

import com.htw.repositories.StatsRepository;
import com.htw.services.StatsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Object[]> statsRevenueByTime(String time, int year) {
        return this.statsRepo.statsRevenueByTime(time, year);
    }

    @Override
    public List<Object[]> countOrdersByMonth() {
        return this.statsRepo.countOrdersByMonth();
    }

    @Override
    public List<Object[]> countProductsByCategory() {
        return this.statsRepo.countProductsByCategory();
    }

    @Override
    public List<Object[]> countUsersByRole() {
        return this.statsRepo.countUsersByRole();
    }

    @Override
    public long countUsers() {
        return this.statsRepo.countUsers();
    }

    @Override
    public long countProducts() {
        return this.statsRepo.countProducts();
    }

    @Override
    public long countStores() {
        return this.statsRepo.countStores();
    }

    @Override
    public long countOrders() {
        return this.statsRepo.countOrders();
    }

    @Override
    public long countCategories() {
        return this.statsRepo.countCategories();
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", countUsers());
        stats.put("totalProducts", countProducts());
        stats.put("totalStores", countStores());
        stats.put("totalOrders", countOrders());
        stats.put("totalCategories", countCategories());
        stats.put("ordersByMonth", countOrdersByMonth());
        stats.put("usersByRole", countUsersByRole());
        stats.put("productsByCategory", countProductsByCategory());
        return stats;
    }
}