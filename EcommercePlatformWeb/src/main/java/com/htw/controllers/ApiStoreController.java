package com.htw.controllers;

import com.htw.pojo.Store;
import com.htw.services.CategoryService;
import com.htw.services.StoreService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<Store>> list() {
        return new ResponseEntity<>(this.storeService.getStores(), HttpStatus.OK);
    }
    
    @DeleteMapping("/stores/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable(value = "id") int id){
        this.storeService.deleteStore(id);
    }
}
