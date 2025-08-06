package com.htw.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.htw.pojo.Cart;
import com.htw.pojo.Product;
import com.htw.services.CartService;
import com.htw.services.ProductService;

@Service
public class CartServiceImpl implements CartService{
    private static final Map<String, Map<Integer, Cart>> userCarts = new ConcurrentHashMap<>();

    @Autowired
    private ProductService productService;

    @Override
    public List<Cart> getCarts(String userId) {
        Map<Integer, Cart> userCart = userCarts.get(userId);

        if(userCart == null){
            return new ArrayList<>();
        }

        return new ArrayList<>(userCart.values());
    }


    @Override
    public Cart addCart(String userId, int productId, int quantity) {
        Product product = productService.getProductById(productId);
        if(product == null){
            throw new RuntimeException("Chưa có sản phẩm nào");
        }

        Map<Integer, Cart> userCart = userCarts.computeIfAbsent(userId, d -> new HashMap<>());

        Cart cartItem = userCart.get(productId);

        if(cartItem == null){
            cartItem = new Cart();
            cartItem.setId(productId);
            cartItem.setName(product.getName());
            cartItem.setPrice(product.getPrice().longValue());
            cartItem.setQuantity(quantity);

        } else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        userCart.put(productId, cartItem);

        return cartItem;
        
    }

    @Override
    public Cart updateCart(String userId, int productId, int quantity) {
       Map<Integer, Cart> userCart = userCarts.get(userId);

       if(userCart == null){
            throw new RuntimeException("Giỏ hàng rỗng");
        }

        Cart cartItem = userCart.get(productId);
        if(cartItem == null){
            throw new RuntimeException("Không có danh sách sản phẩm");
        }

        if(quantity <= 0){
            userCart.remove(productId);
            return null;
        }

        cartItem.setQuantity(quantity);
        return cartItem;

    }

    @Override
    public void removeCart(String userId, int productId) {
        Map<Integer, Cart> userCart = userCarts.get(userId);

        if(userCart != null){
            userCart.remove(productId);
        }
    }
     @Override
    public void clearCart(String userId) {
        userCarts.remove(userId);
    }
    
    @Override
    public double getCartTotal(String userId) {
        Map<Integer, Cart> userCart = userCarts.get(userId);
        if (userCart == null) {
            return 0.0;
        }
        
        return userCart.values().stream().mapToDouble(item -> item.getPrice()* item.getQuantity()).sum();
                  
    }

    
}
