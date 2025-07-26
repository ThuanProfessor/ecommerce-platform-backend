package com.htw.repositories;

import com.htw.pojo.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Trung Hau
 */
public interface ProductRepository {

    List<Product> getProducts(Map<String, String> params);

    Product getProductById(int id);

    Product addOrUpdateProduct(Product p);

    void deleleProduct (int id);
}
