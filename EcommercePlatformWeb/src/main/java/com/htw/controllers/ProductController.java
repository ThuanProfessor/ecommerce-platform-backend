package com.htw.controllers;

import com.htw.pojo.Product;
import com.htw.services.ProductService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Trung Hau
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String viewProduct(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("products", this.productService.getProducts(params));
        return "product";
    }

    @GetMapping("/products/add")
    public String addView(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/products/save")
    public String add(@ModelAttribute(value = "product") Product p) {
        this.productService.addOrUpdateProduct(p);
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}")
    public String updateView(Model model, @PathVariable(value = "productId") int id) {
        model.addAttribute("product", this.productService.getProductById(id));
        return "product-form";
    }

}
