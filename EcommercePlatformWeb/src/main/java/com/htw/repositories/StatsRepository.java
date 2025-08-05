package com.htw.repositories;

import java.util.List;

public interface StatsRepository {

    List<Object[]> statsRevenueByProduct();

    List<Object[]> statsCategory();

    List<Object[]> statsRevenueByStore();

}
