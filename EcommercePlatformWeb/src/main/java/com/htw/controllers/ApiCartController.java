package com.htw.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htw.pojo.Cart;
import com.htw.services.CartService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class ApiCartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable("userId") String userId){
        try{
            List<Cart> cartItems = cartService.getCarts(userId);

            double total = cartService.getCartTotal(userId);

            Map<String, Object> res = new HashMap<>();

            res.put("item", cartItems);
            res.put("total", total);
            res.put("itemCount", cartItems.size());

            return ResponseEntity.ok(res);
            
        } catch(Exception ex){
            return ResponseEntity.badRequest().body(Map.of("error", "Lỗi khi lấy cart: " + ex.getMessage() ) );

        }
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<?> addCart(@PathVariable("userId") String userId, @RequestBody Map<String, Object> request){
        try{
            int productId = (Integer) request.get("productId");
            int quantity = (Integer) request.get("quantity");

            Cart cartItem = cartService.addCart(userId, productId, quantity);

            Map<String, Object> res = new HashMap<>();

            res.put("message", "Them vao gio hang thanh cong");
            res.put("item", cartItem);
            res.put("total", cartService.getCartTotal(userId));

            return ResponseEntity.ok(res);
        } catch(Exception er){
            return ResponseEntity.badRequest().body(Map.of("error", "Them vao gio hang khong thanh cong" + er.getMessage()));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateCartItem(@PathVariable String userId, @RequestBody Map<String, Object> ress ) {
        try{
            int productId = (Integer) ress.get("productId");
            int quantity = (Integer) ress.get("quantity");
            
            Cart cartItem = cartService.updateCart(userId, productId, quantity);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cart updated successfully");
            response.put("item", cartItem);
            response.put("total", cartService.getCartTotal(userId));
            
            return ResponseEntity.ok(response);
        } catch(Exception exx){
            return ResponseEntity.badRequest().body(Map.of("error", "Cap nhat that bai: " + exx.getMessage()));
        }

    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<?> removeCart(@PathVariable String userId, @PathVariable int productId) {
        try {
            cartService.removeCart(userId, productId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Da xoa");
            response.put("total", cartService.getCartTotal(userId));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Loi: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable String userId) {
        try {
            cartService.clearCart(userId);
            
            return ResponseEntity.ok(Map.of("message", "Clear done"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Loi to clear: " + e.getMessage()));
        }
    }
}
