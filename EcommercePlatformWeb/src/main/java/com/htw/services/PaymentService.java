package com.htw.services;

import java.util.List;
import java.util.Map;

import com.htw.pojo.Payment;

public interface PaymentService {
    List<Payment> getPayments();
    
    Payment getPaymentById(int id);
    
    Payment addPayment(Map<String, Object> paymentData);
    
    Payment updatePayment(Payment payment);
    
    void deletePayment(int id);
    
    Payment processWebhook(String payload);
    
    List<Payment> getPaymentHistory();
}
