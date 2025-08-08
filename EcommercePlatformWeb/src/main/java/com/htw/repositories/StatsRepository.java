package com.htw.repositories;

import java.util.List;

public interface StatsRepository {

    List<Object[]> statsRevenueByProduct();

    List<Object[]> statsCategory();

    List<Object[]> statsRevenueByStore();

    List<Object[]> statsRevenueByStore(String username);

    List<Object[]> statsRevenueByMonth();

    List<Object[]> statsRevenueByQuarter();

    List<Object[]> statsRevenueByYear();

    List<Object[]> statsRevenueAllStoreByMonth(Integer month);

    List<Object[]> statsRevenueAllStoreByQuarterAndYear(Integer quarter, Integer year);

}
