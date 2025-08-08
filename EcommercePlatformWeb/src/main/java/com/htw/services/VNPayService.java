package com.htw.services;

import java.util.Map;

public interface VNPayService {
    Map<String, Object> createPaymentUrl(Map<String, Object> request);
    boolean verifyPaymentResponse(Map<String, String> responseParams);
    String generatePaymentUrl(Map<String, Object> request);
    String createSignature(String inputData);
    Map<String, Object> processPaymentCallback(Map<String, String> callbackParams);
}
