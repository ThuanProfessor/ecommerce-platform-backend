package com.htw.repositories;

import java.util.List;
import java.util.Map;

import com.htw.pojo.Payment;

public interface PaymentRepository {
    List<Payment> getPayments();
    
    Payment getPaymentById(int id);
    
    Payment addPayment(Payment payment);
    
    Payment updatePayment(Payment payment);
    void deletePayment(int id);
    
    List<Payment> getPayments(Map<String, String> params);

    List<Payment> getPaymentHistory();
    
    Payment save(Payment payment);
    
    Payment findByOrderCode(String orderCode);
}
