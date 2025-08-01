package com.htw.services;

import java.util.List;


public interface StatsService {
    List<Object[]> statsRevenueByProduct();
    List<Object[]> statsCategory();
}