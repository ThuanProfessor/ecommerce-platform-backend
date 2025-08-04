/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.services.impl;

import com.htw.pojo.Product;
import com.htw.pojo.Store;
import com.htw.repositories.StoreRepository;
import com.htw.services.StoreService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService{
    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Store> getStores() {
        return storeRepository.getStores();
    }

    @Override
    public void deleteStore(int id) {
    }

    @Override
    public List<Store> getStores(Map<String, String> params) {
        return this.storeRepository.getStores(params);
    }

    @Override
    public Store getStoreById(int id) {
        return this.storeRepository.getStoreById(id);
    }

    @Override
    public Store createStore(Store store) {
        return this.storeRepository.createStore(store);
    }

    @Override
    public List<Product> getStoreProducts(int storeId) {
        return this.storeRepository.getStoreProducts(storeId);
    }

    @Override
    public Store getMyStore() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return this.storeRepository.getStoreByUsername(username);
    }

    
}
