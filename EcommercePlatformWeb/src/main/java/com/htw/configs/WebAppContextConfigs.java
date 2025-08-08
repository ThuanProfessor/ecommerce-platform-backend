/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.htw.formatters.CategoryFormatter;
import com.htw.formatters.OrderFormatter;
import com.htw.formatters.ProductFormatter;
import com.htw.formatters.StoreFormatter;
import com.htw.formatters.UserFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author nguye
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.htw.controllers",
    "com.htw.repositories",
    "com.htw.services",
    "com.htw.configs"
})

@PropertySources({
    @PropertySource("classpath:databases.properties")
})
public class WebAppContextConfigs implements WebMvcConfigurer {
    
    // @Bean
    // public VNPayConfig vnPayConfig() {
    //     VNPayConfig config = new VNPayConfig();
    //     config.setTmnCode("ZCLPDHUS");
    //     config.setHashSecret("9NJLQF95551EHKJ8DTALDNOVTZSPKXTK");
    //     config.setPaymentUrl("https://sandbox.vnpayment.vn/paymentv2/vpcpay.html");
    //     config.setReturnUrl("http://localhost:8080/EcommercePlatformWeb/api/payments/vnpay/callback");
    //     config.setIpnUrl("http://localhost:8080/EcommercePlatformWeb/api/payments/vnpay/ipn");
    //     config.setCommand("pay");
    //     config.setCurrCode("VND");
    //     config.setLocale("vn");
    //     return config;
    // }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new CategoryFormatter());
        registry.addFormatter(new StoreFormatter());
        registry.addFormatter(new UserFormatter());
        registry.addFormatter(new OrderFormatter());
        registry.addFormatter(new ProductFormatter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
    }
    
     

        
}
