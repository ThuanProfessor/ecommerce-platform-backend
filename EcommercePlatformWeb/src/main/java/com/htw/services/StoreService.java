/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.htw.services;

import com.htw.pojo.Product;
import com.htw.pojo.Store;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguye
 */
public interface StoreService {
    List<Store> getStores();

    public void deleteStore(int id);

     List<Store> getStores(Map<String, String> params);
    
    Store getStoreById(int id);
    
    Store createStore(Store store);
    
    List<Product> getStoreProducts(int storeId);
    Store getMyStore();
}
