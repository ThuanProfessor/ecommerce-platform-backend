/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.htw.ecommerceplatform;

import com.htw.repositories.impl.ProductRepositotyImpl;
import com.htw.repositories.impl.StatsRepositoryImpl;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author nguye
 */
public class EcommercePlatform {

    public static void main(String[] args) {
//        CategoryRepositoryImpl s = new CategoryRepositoryImpl();
//        s.getCates().forEach(c -> System.out.println(c.getName()));
//
//        Map<String, String> params = new HashMap<>();
////        params.put("kw", "samsung");
////        params.put("fromprice", "12000000");
//
//        //Page
//        params.put("page", "2");
//
//        ProductRepositotyImpl prods = new ProductRepositotyImpl();
//
//        prods.getProducts(params).forEach(p -> System.out.printf("%d - %s - %s - %s - %s - %s\n", p.getId(), p.getName(), p.getPrice(), p.getImage(),
//                p.getDescription(), p.getActive(), p.getCreatedDate()));
//
        StatsRepositoryImpl s = new StatsRepositoryImpl();
        s.statsDataOverview("QUATER", 2023).forEach(o -> System.out.printf("%d : %d\n", o[0], o[1]));

    }
}
