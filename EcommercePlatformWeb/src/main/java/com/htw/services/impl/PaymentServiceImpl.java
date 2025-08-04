package com.htw.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.htw.pojo.Payment;
import com.htw.pojo.SaleOrder;
import com.htw.repositories.OrderRepository;
import com.htw.repositories.PaymentRepository;
import com.htw.services.PaymentService;

public class PaymentServiceImpl implements PaymentService {

     @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Payment> getPayments() {
        return this.paymentRepository.getPayments();
    }

    @Override
    public Payment getPaymentById(int id) {
        return this.paymentRepository.getPaymentById(id);
    }

    @Override
    public Payment addPayment(Map<String, Object> paymentData) {
        Payment payment = new Payment();
        
        Integer orderId = (Integer) paymentData.get("orderId");
        BigDecimal amount = new BigDecimal(paymentData.get("amount").toString());
        String type = (String) paymentData.get("type");
        
        payment.setAmount(amount);
        payment.setType(type);
        payment.setStatus("PENDING");
        payment.setCreatedDate(new Date());
        
        // Lấy order
        SaleOrder order = orderRepository.getOrderById(orderId);
        payment.setOrderId(order);
        
        return this.paymentRepository.addPayment(payment);
    }

    @Override
    public Payment processWebhook(String payload) {
      
        return null; 
    }

    @Override
    public List<Payment> getPaymentHistory() {
        return this.paymentRepository.getPaymentHistory();
    }
}
