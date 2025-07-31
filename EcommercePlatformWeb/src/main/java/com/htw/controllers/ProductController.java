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
    public String addView(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("products", this.productService.getProducts(params));
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping("/products/add")
    public String add(@ModelAttribute("product") Product p) {
        System.out.println("Product: " + p.getName());
        if (p.getId() != null) {
            Product existing = productService.getProductById(p.getId());
            p.setCreatedDate(existing.getCreatedDate());
            p.setImage(existing.getImage());
        } else {
            p.setCreatedDate(new Date());
        }

        this.productService.addOrUpdateProduct(p);
        return "redirect:/products";
    }

    @GetMapping("/products/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @GetMapping("/products/{productId}")
    public String updateProduct(Model model, @PathVariable(value = "productId") int id) {
        model.addAttribute("product", this.productService.getProductById(id));

        return "product-form";
    }

}
