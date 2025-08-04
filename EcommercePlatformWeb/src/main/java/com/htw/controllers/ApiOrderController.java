package com.htw.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htw.pojo.SaleOrder;
import com.htw.services.OrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<SaleOrder>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.orderService.getOrders(params), HttpStatus.OK);
    }
    
    //lay don hàng cụ thể
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<SaleOrder> retrieve(@PathVariable(value = "orderId") int id) {
        return new ResponseEntity<>(this.orderService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<SaleOrder> create(@RequestBody Map<String, Object> orderData) {
        return new ResponseEntity<>(this.orderService.createOrder(orderData), HttpStatus.CREATED);
    }

    @GetMapping("/orders/my-orders")
    public ResponseEntity<List<SaleOrder>> getMyOrders() {
        return new ResponseEntity<>(this.orderService.getMyOrders(), HttpStatus.OK);
    }
    //cập nhật trnag thái đơn hàng
    
}
