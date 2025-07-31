package com.htw.repositories;

import java.util.List;

public interface StatsRepository {
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
}