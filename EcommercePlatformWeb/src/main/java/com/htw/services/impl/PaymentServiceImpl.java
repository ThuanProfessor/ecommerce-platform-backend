package com.htw.services.impl;

import com.htw.pojo.Payment;
import com.htw.pojo.SaleOrder;
import com.htw.pojo.User;
import com.htw.repositories.OrderDetailRepository;
import com.htw.repositories.OrderRepository;
import com.htw.repositories.PaymentRepository;
import com.htw.repositories.ProductRepository;
import com.htw.repositories.UserRepository;
import com.htw.services.PaymentService;
import com.htw.services.VNPayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private OrderRepository orderRepository;
    
    
    //them cai nay
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    //them cai nay
    
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

        // orderId có thể là int hoặc String -> ép an toàn
        Integer orderId = null;
        Object orderIdObj = paymentData.get("orderId");
        if (orderIdObj != null) {
            try {
                orderId = Integer.parseInt(String.valueOf(orderIdObj));
            } catch (NumberFormatException ignored) {}
        }

        BigDecimal amount = new BigDecimal(String.valueOf(paymentData.get("amount")));
        String type = String.valueOf(paymentData.getOrDefault("type", "VNPAY"));

        payment.setAmount(amount);
        payment.setType(type);
        payment.setStatus("PENDING");
        payment.setCreatedDate(new Date());

        if (orderId != null) {
            SaleOrder order = orderRepository.getOrderById(orderId);
            if (order != null) {
                payment.setOrderId(order);
            }
        }

        return this.paymentRepository.addPayment(payment);
    }

    @Override
    public Payment processWebhook(String payload) {
        // chưa dùng
        return null;
    }

    @Override
    public List<Payment> getPaymentHistory() {
        return this.paymentRepository.getPaymentHistory();
    }

//    @Override
//    public Map<String, Object> createVNPayPayment(Map<String, Object> request) {
//        Map<String, Object> resp = new HashMap<>();
//        try {
//            Object amountObj = request.get("amount");
//            Object orderIdObj = request.get("orderId");
//            if (amountObj == null) throw new IllegalArgumentException("Amount is required");
//            if (orderIdObj == null) throw new IllegalArgumentException("orderId is required");
//
//            // orderCode bạn đang dùng = orderId từ request (giữ nguyên theo code cũ)
//            String orderCode = String.valueOf(orderIdObj);
//            long amountVnd   = new BigDecimal(String.valueOf(amountObj)).longValue(); // VND (chưa x100)
//            String orderInfo = String.valueOf(request.getOrDefault("orderInfo", "Thanh toan don hang " + orderCode));
//            String returnUrl = (String) request.get("returnUrl"); // có thể null -> VNPayServiceImpl dùng default từ VNPayConfig
//            String ipnUrl    = (String) request.get("ipnUrl");    // có thể null -> VNPayServiceImpl dùng default
//            String locale    = (String) request.getOrDefault("locale", "vn");
//            String clientIp  = (String) request.getOrDefault("clientIp", "0.0.0.0");
//
//            // 1) Tạo & lưu Payment PENDING trước
//            Payment payment = new Payment();
//            payment.setAmount(new BigDecimal(String.valueOf(amountObj)));
//            payment.setType("VNPAY");
//            payment.setStatus("PENDING");
//            payment.setCreatedDate(new Date());
//            payment.setOrderCode(orderCode); // dùng làm vnp_TxnRef
//
//            // Nếu có saleOrderId → gắn FK SaleOrder cho Payment
//            Object saleOrderIdObj = request.get("saleOrderId");
//            if (saleOrderIdObj != null) {
//                try {
//                    int saleOrderId = Integer.parseInt(String.valueOf(saleOrderIdObj));
//                    SaleOrder order = orderRepository.getOrderById(saleOrderId);
//                    if (order != null) {
//                        payment.setOrderId(order);
//                    }
//                } catch (NumberFormatException ignored) {}
//            }
//
//            Payment saved = paymentRepository.addPayment(payment);
//
//            // 2) Gọi VNPay đúng chữ ký interface (x100 amount nằm trong VNPayServiceImpl)
//            String paymentUrl = vnPayService.createPaymentUrl(
//                    orderCode, amountVnd, orderInfo, returnUrl, ipnUrl, locale, clientIp
//            );
//
//            resp.put("status", "SUCCESS");
//            resp.put("paymentUrl", paymentUrl);
//            resp.put("transactionId", saved.getId());
//            return resp;
//
//        } catch (Exception e) {
//            resp.put("status", "ERROR");
//            resp.put("message", "Failed to create VNPay payment: " + e.getMessage());
//            return resp;
//        }
//    }

    
    
    
    
    
    
    @Override
    public Map<String, Object> createVNPayPayment(Map<String, Object> request) {
        Map<String, Object> resp = new HashMap<>();
        try {
         

            Object amountObj = request.get("amount");
            Object orderIdObj = request.get("orderId");
            if (amountObj == null) throw new IllegalArgumentException("Amount is required");
            if (orderIdObj == null) throw new IllegalArgumentException("orderId is required");

   
            String orderCode = String.valueOf(orderIdObj);
            long amountVnd   = new BigDecimal(String.valueOf(amountObj)).longValue();
            String orderInfo = String.valueOf(request.getOrDefault("orderInfo", "Thanh toan don hang " + orderCode));
            String returnUrl = (String) request.get("returnUrl");
            String ipnUrl    = (String) request.get("ipnUrl");
            String locale    = (String) request.getOrDefault("locale", "vn");
            String clientIp  = (String) request.getOrDefault("clientIp", "127.0.0.1");

          

            @SuppressWarnings("unchecked")
            List<Map<String,Object>> items = (List<Map<String,Object>>) request.get("items");

            
            SaleOrder order = new SaleOrder();
            order.setCreatedDate(new Date());
            order.setStatus("PENDING");
            
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated() && 
                    !"anonymousUser".equals(authentication.getName())) {

            
                    String username = authentication.getName();
               

                   
                    User currentUser = userRepository.getUserByUsername(username);
                    if (currentUser != null) {
                        order.setUserId(currentUser);
                       
                    } else {
                       
                    }
                } else {
                    System.out.println("No process");
                }
            } catch (Exception e) {
                System.out.println("Lỗi " + e.getMessage());
            }
            
            order = orderRepository.addOrder(order);
     

            if (items != null && !items.isEmpty()) {
                
                for (Map<String,Object> it : items) {
                    Integer productId = Integer.parseInt(String.valueOf(it.get("productId")));
                    Integer qty = Integer.parseInt(String.valueOf(it.get("quantity")));

                    com.htw.pojo.Product p = productRepository.getProductById(productId);
                    if (p == null) {
                   
                        continue;
                    }

                    com.htw.pojo.OrderDetail od = new com.htw.pojo.OrderDetail();
                    od.setOrderId(order); // FK đến SaleOrder
                    od.setProductId(p);
                    od.setQuantity(qty != null ? qty : 1);
                    od.setUnitPrice(p.getPrice());

                    orderDetailRepository.addOrderDetail(od);
              
                }
            }

      
            Payment payment = new Payment();
            payment.setAmount(new BigDecimal(String.valueOf(amountObj)));
            payment.setType("VNPAY");
            payment.setStatus("PENDING");
            payment.setCreatedDate(new Date());
            payment.setOrderCode(orderCode);  
            payment.setOrderId(order);        
            Payment saved = paymentRepository.addPayment(payment);
          

          
          
            String paymentUrl = vnPayService.createPaymentUrl(
                    orderCode, amountVnd, orderInfo, returnUrl, ipnUrl, locale, clientIp
            );
   

            resp.put("status", "SUCCESS");
            resp.put("paymentUrl", paymentUrl);
            resp.put("transactionId", saved.getId());
            resp.put("orderCode", orderCode);
            return resp;

        } catch (Exception e) {
            System.err.println("eroror");
            e.printStackTrace();
            resp.put("status", "ERROR");
            resp.put("message", "Failed to create VNPay payment: " + e.getMessage());
            return resp;
        }
    }   
    
    
    @Override
    public Payment processVNPayCallback(Map<String, String> callbackParams) {
        // Lấy tham số từ VNPay
        String orderCode = callbackParams.get("vnp_TxnRef");   
        String respCode  = callbackParams.get("vnp_ResponseCode"); 

        if (orderCode == null || orderCode.isBlank()) {
            throw new RuntimeException("Missing vnp_TxnRef in callback");
        }

        Payment payment = getPaymentByOrderId(orderCode);
        if (payment == null) {
            throw new RuntimeException("Payment not found for order: " + orderCode);
        }

       //kí ten
        boolean signatureOk = vnPayService.verifySignature(callbackParams);

       
        if (!signatureOk) {
            payment.setStatus("FAILED");
        } else if ("00".equals(respCode)) {
            payment.setStatus("SUCCESS");
        } else if ("24".equals(respCode)) {
            payment.setStatus("CANCELLED");
        } else {
            payment.setStatus("FAILED");
        }

        payment = paymentRepository.updatePayment(payment);

      
        SaleOrder order = payment.getOrderId();
        if (order != null) {
            if ("SUCCESS".equals(payment.getStatus())) {
                order.setStatus("PAID");     
            } else if ("CANCELLED".equals(payment.getStatus())) {
                order.setStatus("CANCELLED");
            } else {
                order.setStatus("FAILED");
            }
            
            orderRepository.updateOrder(order);
        }

        return payment;
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

    @Override
    public List<Payment> getUserPaymentHistory() {
        try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            User u = userRepository.getUserByUsername(username);
            if (u != null) {
                return paymentRepository.getPaymentsByUserId(u.getId());
            }
        }
        return new ArrayList<>();
    } catch (Exception e) {
        System.out.println("Lỗi khi lấy danh sách thanh toán: " + e.getMessage());
        return new ArrayList<>();
    }
    }
}
