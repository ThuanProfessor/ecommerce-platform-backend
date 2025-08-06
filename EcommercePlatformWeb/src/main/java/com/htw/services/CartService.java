package com.htw.services;

import java.util.List;

import com.htw.pojo.Cart;

public interface CartService {
    List<Cart> getCarts(String userId);

    Cart addCart(String userId, int productId, int quantity);
    Cart updateCart(String userId, int productId, int quantity);

    void removeCart(String userId, int productId);
    
    void clearCart(String userId);

    double getCartTotal(String userId);



}
