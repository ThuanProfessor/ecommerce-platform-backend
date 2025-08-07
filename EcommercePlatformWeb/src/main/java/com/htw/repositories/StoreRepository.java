/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.htw.repositories;

import com.htw.pojo.Product;
import com.htw.pojo.Store;
import jakarta.data.repository.Query;

import java.util.List;
import java.util.Map;

/**
 *
 * @author nguye
 */
public interface StoreRepository {

    @Query("SELECT s From Store s")



    List<Store> getStores(Map<String, String> params);
    List<Store> getStores();
    
    Store getStoreById(int id);
    
    Store createStore(Store store);
    
    void deleteStore(int id);
    
    List<Product> getStoreProducts(int storeId);
    
    Store getStoreByUsername(String username);

    Store addOrUpdateStore(Store store);



}
