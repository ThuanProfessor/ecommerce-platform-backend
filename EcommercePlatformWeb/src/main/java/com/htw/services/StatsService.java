package com.htw.services;

import java.util.List;

public interface StatsService {

    List<Object[]> statsRevenueByProduct();

    List<Object[]> statsCategory();

    List<Object[]> statsRevenueByStore();

    List<Object[]> statsRevenueStoreByUsername();

    List<Object[]> statsRevenueAllStoreByMonth(Integer month);

    List<Object[]> statsRevenueAllStoreByQuarterAndYear(Integer quarter, Integer year);
}
