/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.htw.services;

import com.htw.pojo.Store;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface StoreService {

    List<Store> getStores();

    Store getStoreById(int id);

    Store addOrUpdateStore(Store store);

    public void deleteStore(int id);

}
