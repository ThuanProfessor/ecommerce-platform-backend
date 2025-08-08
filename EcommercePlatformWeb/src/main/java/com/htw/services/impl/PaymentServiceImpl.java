package com.htw.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.htw.services.VNPayService;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htw.pojo.Payment;
import com.htw.pojo.SaleOrder;
import com.htw.repositories.OrderRepository;
import com.htw.repositories.PaymentRepository;
import com.htw.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VNPayService vnPayService;
    
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
    public Payment updatePayment(Payment payment) {
        return this.paymentRepository.updatePayment(payment);
    }

    @Override
    public void deletePayment(int id) {
        this.paymentRepository.deletePayment(id);
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

    @Override
    public Map<String, Object> createVNPayPayment(Map<String, Object> request) {
        try {
  
            Payment payment = new Payment();
            payment.setAmount(new BigDecimal(request.get("amount").toString()));
            payment.setType("VNPAY");
            payment.setStatus("PENDING");
            payment.setCreatedDate(new Date());
            
        
            Payment savedPayment = paymentRepository.save(payment);
            
            
            Map<String, Object> response = vnPayService.createPaymentUrl(request);
            response.put("transactionId", savedPayment.getId());
            
            return response;
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "ERROR");
            errorResponse.put("message", "Failed to create VNPay payment: " + e.getMessage());
            return errorResponse;
        }
    }

    @Override
    public Payment processVNPayCallback(Map<String, String> callbackParams) {
         try {
          
            Map<String, Object> callbackResult = vnPayService.processPaymentCallback(callbackParams);
            
            String orderId = (String) callbackResult.get("orderId");
            String status = (String) callbackResult.get("status");
            
           
            Payment payment = getPaymentByOrderId(orderId);
            if (payment == null) {
                throw new RuntimeException("Payment not found for order: " + orderId);
            }
            
            
            if ("SUCCESS".equals(status)) {
                payment.setStatus("SUCCESS");
            } else {
                payment.setStatus("FAILED");
            }
            
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process VNPay callback: " + e.getMessage());
        }
    }

    @Override
    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderCode(orderId);
    }
    
    public Payment getPaymentByOrderCode(String orderCode) {
        return paymentRepository.findByOrderCode(orderCode);
    }

    @Override
    public Map<String, Object> getPaymentStatus(String orderId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Payment payment = getPaymentByOrderId(orderId);
            if (payment == null) {
                result.put("status", "NOT_FOUND");
                result.put("message", "Payment not found");
            } else {
                result.put("status", "FOUND");
                result.put("paymentStatus", payment.getStatus());
                result.put("amount", payment.getAmount());
                result.put("type", payment.getType());
                result.put("createdDate", payment.getCreatedDate());
            }
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Error getting payment status: " + e.getMessage());
        }
        
        return result;
    }
    
}
