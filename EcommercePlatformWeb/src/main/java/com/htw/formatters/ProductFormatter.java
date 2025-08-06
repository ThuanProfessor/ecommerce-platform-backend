package com.htw.formatters;

import com.htw.pojo.Product;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Trung Hau
 */
public class ProductFormatter implements Formatter<Product> {

    @Override
    public String print(Product product, Locale locale) {
        return String.valueOf(product.getId());
    }

    @Override
    public Product parse(String productId, Locale locale) throws ParseException {
        Product product = new Product(Integer.valueOf(productId));

        return product;
    }
}
