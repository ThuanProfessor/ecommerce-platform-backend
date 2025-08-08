package com.htw.repositories;

import java.util.List;

public interface StatsRepository {

    List<Object[]> statsRevenueByProduct();

    List<Object[]> statsCategory();

    List<Object[]> statsRevenueByStore();

    List<Object[]> statsRevenueByMonth();

    List<Object[]> statsRevenueByQuarter();

    List<Object[]> statsRevenueByYear();

    List<Object[]> statsRevenueAllStoreByMonth(Integer month);

}
