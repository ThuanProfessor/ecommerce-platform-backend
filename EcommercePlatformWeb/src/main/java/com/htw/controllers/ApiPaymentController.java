package com.htw.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.htw.pojo.Payment;
import com.htw.services.PaymentService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiPaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> list() {
        return new ResponseEntity<>(this.paymentService.getPayments(), HttpStatus.OK);
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<Payment> retrieve(@PathVariable(value = "paymentId") int id) {
        return new ResponseEntity<>(this.paymentService.getPaymentById(id), HttpStatus.OK);
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> create(@RequestBody Map<String, Object> paymentData) {
        return new ResponseEntity<>(this.paymentService.addPayment(paymentData), HttpStatus.CREATED);
    }

    @PutMapping("/payments/{paymentId}")
    public ResponseEntity<Payment> update(@PathVariable(value = "paymentId") int id, @RequestBody Payment payment) {
        payment.setId(id);
        return new ResponseEntity<>(this.paymentService.updatePayment(payment), HttpStatus.OK);
    }

    @DeleteMapping("/payments/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable(value = "paymentId") int id) {
        this.paymentService.deletePayment(id);
    }

    @PostMapping("/payments/webhook")
    public ResponseEntity<Payment> handleWebhook(@RequestBody String payload) {
        return new ResponseEntity<>(this.paymentService.processWebhook(payload), HttpStatus.OK);
    }

    @GetMapping("/payments/history")
    public ResponseEntity<List<Payment>> getPaymentHistory() {
        return new ResponseEntity<>(this.paymentService.getPaymentHistory(), HttpStatus.OK);
    }

    @PostMapping("/payments/vnpay/create")
    public ResponseEntity<Map<String, Object>> createVNPayPayment(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = paymentService.createVNPayPayment(request);
        if ("ERROR".equals(response.get("status"))) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/payments/vnpay/callback")
    public ResponseEntity<Payment> handleVNPayCallback(@RequestParam Map<String, String> callbackParams) {
        try {
            Payment payment = paymentService.processVNPayCallback(callbackParams);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/payments/vnpay/status/{orderId}")
    public ResponseEntity<Map<String, Object>> getVNPayPaymentStatus(@PathVariable String orderId) {
        Map<String, Object> result = paymentService.getPaymentStatus(orderId);
        if ("NOT_FOUND".equals(result.get("status"))) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if ("ERROR".equals(result.get("status"))) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/payments/vnpay/verify")
    public ResponseEntity<Map<String, Object>> verifyVNPayPayment(@RequestBody Map<String, String> verifyParams) {
        try {
            Map<String, Object> result = paymentService.getPaymentStatus(verifyParams.get("orderId"));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("status", "ERROR");
            errorResult.put("message", "Verification failed: " + e.getMessage());


            return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
        }
    }
}
