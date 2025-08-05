package com.htw.controllers;

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
}
