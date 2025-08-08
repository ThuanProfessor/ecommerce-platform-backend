package com.htw.services;

import java.util.Map;

public interface VNPayService {
    String createPaymentUrl(String orderId, long amountVnd, String orderInfo,
                        String returnUrl, String ipnUrl, String locale, String clientIp) throws Exception;
    boolean verifySignature(Map<String, String> allParams);
}
