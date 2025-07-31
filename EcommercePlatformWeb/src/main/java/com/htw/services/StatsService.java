package com.htw.services;

import java.util.List;
import java.util.Map;

public interface StatsService {
    List<Object[]> statsRevenueByProduct();
    List<Object[]> statsRevenueByTime(String time, int year);
    List<Object[]> countOrdersByMonth();
    List<Object[]> countProductsByCategory();
    List<Object[]> countUsersByRole();
    long countUsers();
    long countProducts();
    long countStores();
    long countOrders();
    long countCategories();
    Map<String, Object> getDashboardStats();
}