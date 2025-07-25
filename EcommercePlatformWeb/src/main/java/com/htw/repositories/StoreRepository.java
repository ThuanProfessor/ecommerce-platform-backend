/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.htw.repositories;

import com.htw.pojo.Store;
import jakarta.data.repository.Query;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface StoreRepository {
    @Query("SELECT s From Store s")
    List<Store> getStores();
}
