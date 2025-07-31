/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.services.impl;

import com.htw.pojo.Store;
import com.htw.repositories.StoreRepository;
import com.htw.services.StoreService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService{
    @Autowired
    private StoreRepository storeRepo;

    @Override
    public List<Store> getStores() {
        return storeRepo.getStores();
    }

    @Override
    public void deleteStore(int id) {
    }
}
