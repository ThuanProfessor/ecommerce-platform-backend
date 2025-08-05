package com.htw.services;

import com.htw.pojo.Product;
import com.htw.pojo.Review;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Trung Hau
 */
public interface ProductService {

    List<Product> getProducts(Map<String, String> params);

    Product getProductById(int id);

    Product addOrUpdateProduct(Product p);

    void deleleProduct(int id);

     List<Product> getProductsByIds(List<Integer> productIds);
    
    List<Product> getProductsByStore(int storeId);
    List<Review> getProductReviews(int productId);
}
