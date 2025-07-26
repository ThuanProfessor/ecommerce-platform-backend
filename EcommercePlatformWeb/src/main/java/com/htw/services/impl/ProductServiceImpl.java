package com.htw.services.impl;

import com.htw.pojo.Product;
import com.htw.repositories.ProductRepository;
import com.htw.services.ProductService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trung Hau
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts(Map<String, String> params) {
        return this.productRepository.getProducts(params);
    }

    @Override
    public Product getProductById(int id) {
        return this.productRepository.getProductById(id);
    }

    @Override
    public Product addOrUpdateProduct(Product p) {
        return this.productRepository.addOrUpdateProduct(p);
    }

    @Override
    public void deleleProduct(int id) {
        this.deleleProduct(id);
    }

}
