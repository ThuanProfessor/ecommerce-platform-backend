/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.controllers;

import com.htw.pojo.Store;
import com.htw.pojo.Product;
import com.htw.services.StoreService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Trung Hau
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiStoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.storeService.getStores(params), HttpStatus.OK);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<Store> retrieve(@PathVariable(value = "storeId") int id) {
        return new ResponseEntity<>(this.storeService.getStoreById(id), HttpStatus.OK);
    }

    @PostMapping("/stores")
    public ResponseEntity<Store> create(@RequestBody Store store) {
        return new ResponseEntity<>(this.storeService.createStore(store), HttpStatus.CREATED);
    }

    @DeleteMapping("/stores/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable(value = "storeId") int id) {
        this.storeService.deleteStore(id);
    }

    @GetMapping("/stores/{storeId}/products")
    public ResponseEntity<List<Product>> getStoreProducts(@PathVariable(value = "storeId") int storeId) {
        return new ResponseEntity<>(this.storeService.getStoreProducts(storeId), HttpStatus.OK);
    }

    @GetMapping("/stores/my-store")
    public ResponseEntity<Store> getMyStore() {
        return new ResponseEntity<>(this.storeService.getMyStore(), HttpStatus.OK);
    }
}