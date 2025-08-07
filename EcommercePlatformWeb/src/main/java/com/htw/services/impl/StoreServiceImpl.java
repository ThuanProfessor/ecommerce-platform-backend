/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.services.impl;



import com.htw.pojo.Product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;




import com.htw.pojo.Store;
import com.htw.repositories.StoreRepository;
import com.htw.services.StoreService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;


import java.util.logging.Level;
import java.util.logging.Logger;


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
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private Cloudinary cloudinary;

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
    public List<Store> getStores() {
        return this.storeRepository.getStores();
    }

    @Override
    public Store addOrUpdateStore(Store store) {
        if (!store.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(store.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                store.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.storeRepository.addOrUpdateStore(store);
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
