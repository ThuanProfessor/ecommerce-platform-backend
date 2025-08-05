/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.controllers;

import com.htw.pojo.Product;
import com.htw.pojo.Review;
import com.htw.services.ProductService;
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
 * @author nguye
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.productService.getProducts(params), HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> retrieve(@PathVariable(value = "productId") int id) {
        return new ResponseEntity<>(this.productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return new ResponseEntity<>(this.productService.addOrUpdateProduct(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable(value = "productId") int id) {
        this.productService.deleleProduct(id);
    }

    @GetMapping("/products/{productId}/reviews-alt")
    public ResponseEntity<List<Review>> getReviews(@PathVariable(value = "productId") int id) {
        return new ResponseEntity<>(this.productService.getProductReviews(id), HttpStatus.OK);
    }

    @GetMapping("/products/compare")
    public ResponseEntity<List<Product>> compareProducts(@RequestParam List<Integer> productIds) {
        return new ResponseEntity<>(this.productService.getProductsByIds(productIds), HttpStatus.OK);
    }
}