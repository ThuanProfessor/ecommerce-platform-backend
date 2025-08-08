package com.htw.services.impl;

import com.htw.configs.VNPayConfig;
import com.htw.services.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VNPayServiceImpl implements VNPayService {

    private final VNPayConfig config;

    @Autowired
    public VNPayServiceImpl(VNPayConfig config) {
        this.config = config;
    }

    @Override
    public String createPaymentUrl(String orderId,
                                   long amountVnd,
                                   String orderInfo,
                                   String returnUrl,
                                   String ipnUrl,
                                   String locale,
                                   String clientIp) throws Exception {

        
        long amount = amountVnd * 100L;

       
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime now = LocalDateTime.now(zone);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnpCreateDate = now.format(fmt);
        String vnpExpireDate = now.plusMinutes(15).format(fmt);

        // Dùng default
        String effectiveReturnUrl = isBlank(returnUrl) ? config.getReturnUrl() : returnUrl;
        String effectiveIpnUrl    = isBlank(ipnUrl)    ? config.getIpnUrl()    : ipnUrl;
        String effectiveLocale    = isBlank(locale)    ? config.getLocale()    : locale;
        String effectiveClientIp  = isBlank(clientIp)  ? "0.0.0.0"             : clientIp;

        
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", nvl(config.getCommand(), "pay"));
        params.put("vnp_TmnCode", config.getTmnCode());
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", nvl(config.getCurrCode(), "VND"));
        params.put("vnp_TxnRef", orderId);
        params.put("vnp_OrderInfo", nvl(orderInfo, "Thanh toan don hang " + orderId));
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", effectiveLocale);
        params.put("vnp_ReturnUrl", effectiveReturnUrl);
        params.put("vnp_IpAddr", effectiveClientIp);
        params.put("vnp_CreateDate", vnpCreateDate);
        params.put("vnp_ExpireDate", vnpExpireDate);

        

        Map<String, String> sorted = new TreeMap<>(params);
        String signData = buildQuery(sorted, true);
        String secureHash = hmacSHA512(config.getHashSecret(), signData);

        String query = buildQuery(sorted, true) + "&vnp_SecureHash=" + URLEncoder.encode(secureHash, StandardCharsets.UTF_8.name());

        return config.getPaymentUrl() + "?" + query;
    }

    @Override
    public boolean verifySignature(Map<String, String> allParams) {
        if (allParams == null || allParams.isEmpty()) return false;

        String vnpSecureHash = allParams.get("vnp_SecureHash");
        if (isBlank(vnpSecureHash)) return false;

        
        Map<String, String> filtered = new HashMap<>(allParams);
        filtered.remove("vnp_SecureHash");
        

       
        Map<String, String> sorted = new TreeMap<>(filtered);

        // Ký lại
        String signData = buildQuery(sorted, true);
        String myHash = hmacSHA512(config.getHashSecret(), signData);

        return myHash.equalsIgnoreCase(vnpSecureHash);
    }

    

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String nvl(String s, String def) {
        return isBlank(s) ? def : s;
    }

    private static String buildQuery(Map<String, String> params, boolean urlEncode) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : params.entrySet()) {
            String k = e.getKey();
            String v = e.getValue();
            if (v == null) continue;
            if (sb.length() > 0) sb.append('&');
            if (urlEncode) {
                sb.append(URLEncoder.encode(k, StandardCharsets.UTF_8))
                  .append('=')
                  .append(URLEncoder.encode(v, StandardCharsets.UTF_8));
            } else {
                sb.append(k).append('=').append(v);
            }
        }
        return sb.toString();
    }

    private static String hmacSHA512(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            byte[] bytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder out = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) out.append(String.format("%02x", b));
            return out.toString();
        } catch (Exception ex) {
            throw new RuntimeException("HMAC SHA512 error", ex);
        }
    }
}
