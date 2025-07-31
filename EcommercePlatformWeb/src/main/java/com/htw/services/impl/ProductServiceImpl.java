package com.htw.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.htw.pojo.Product;
import com.htw.repositories.ProductRepository;
import com.htw.services.ProductService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @Autowired
    private Cloudinary cloudinary;

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
        if (!p.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(p.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                p.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.productRepository.addOrUpdateProduct(p);
    }

    

    @Override
    public void deleleProduct(int id) {
        this.deleleProduct(id);
    }

}
