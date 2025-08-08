package com.htw.services.impl;

import com.htw.configs.VNPayConfig;
import com.htw.services.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
public class VNPayServiceImpl implements VNPayService {
    
    @Autowired
    private VNPayConfig vnPayConfig;
    
    @Override
    public Map<String, Object> createPaymentUrl(Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String paymentUrl = generatePaymentUrl(request);
            response.put("paymentUrl", paymentUrl);
            response.put("orderId", request.get("orderId"));
            response.put("status", "SUCCESS");
            response.put("message", "Payment URL created successfully");
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Failed to create payment URL: " + e.getMessage());
        }
        
        return response;
    }
    
    @Override
    public String generatePaymentUrl(Map<String, Object> request) {
        String vnp_Version = "2.1.0";
        String vnp_Command = vnPayConfig.getCommand();
        String vnp_TxnRef = (String) request.get("orderId");
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = vnPayConfig.getTmnCode();
        
  
        Double amount = Double.parseDouble(request.get("amount").toString());
        String vnp_Amount = String.valueOf((long)(amount * 100));
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", vnPayConfig.getCurrCode());
        vnp_Params.put("vnp_BankCode", "");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", (String) request.get("orderInfo"));
        vnp_Params.put("vnp_OrderType", request.get("orderType") != null ? (String) request.get("orderType") : "other");
        vnp_Params.put("vnp_Locale", request.get("locale") != null ? (String) request.get("locale") : vnPayConfig.getLocale());
        vnp_Params.put("vnp_ReturnUrl", request.get("returnUrl") != null ? (String) request.get("returnUrl") : vnPayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpnUrl", request.get("ipnUrl") != null ? (String) request.get("ipnUrl") : vnPayConfig.getIpnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = createSignature(hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayConfig.getPaymentUrl() + "?" + queryUrl;
        
        return paymentUrl;
    }
    
    @Override
    public String createSignature(String inputData) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((vnPayConfig.getHashSecret() + inputData).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error creating signature", e);
        }
    }
    
    @Override
    public boolean verifyPaymentResponse(Map<String, String> responseParams) {
        String vnp_SecureHash = responseParams.get("vnp_SecureHash");
        if (vnp_SecureHash == null || vnp_SecureHash.isEmpty()) {
            return false;
        }
        
        Map<String, String> paramsForSignature = new HashMap<>(responseParams);
        paramsForSignature.remove("vnp_SecureHash");
        
        List<String> fieldNames = new ArrayList<>(paramsForSignature.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = paramsForSignature.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }
        
        String calculatedSignature = createSignature(hashData.toString());
        return calculatedSignature.equals(vnp_SecureHash);
    }
    
    @Override
    public Map<String, Object> processPaymentCallback(Map<String, String> callbackParams) {
        Map<String, Object> result = new HashMap<>();
        
        try {
           
            if (!verifyPaymentResponse(callbackParams)) {
                result.put("status", "ERROR");
                result.put("message", "Invalid signature");
                return result;
            }
            
            String orderId = callbackParams.get("vnp_TxnRef");
            String responseCode = callbackParams.get("vnp_ResponseCode");
            String transactionId = callbackParams.get("vnp_TransactionNo");
            String amount = callbackParams.get("vnp_Amount");
            String bankCode = callbackParams.get("vnp_BankCode");
            String payDate = callbackParams.get("vnp_PayDate");
            String orderInfo = callbackParams.get("vnp_OrderInfo");
            
            result.put("orderId", orderId);
            result.put("transactionId", transactionId);
            result.put("amount", amount);
            result.put("bankCode", bankCode);
            result.put("payDate", payDate);
            result.put("orderInfo", orderInfo);
            
            if ("00".equals(responseCode)) {
                result.put("status", "SUCCESS");
                result.put("message", "Payment successful");
                result.put("responseCode", responseCode);
            } else {
                result.put("status", "FAILED");
                result.put("message", "Payment failed");
                result.put("responseCode", responseCode);
            }
            
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Failed to process callback: " + e.getMessage());
        }
        
        return result;
    }
}