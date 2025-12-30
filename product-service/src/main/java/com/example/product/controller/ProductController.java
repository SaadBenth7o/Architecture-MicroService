package com.example.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produits")
public class ProductController {
    
    @GetMapping
    public String getProducts() {
        return "Liste des produits depuis product-service";
    }
}

