package com.travel.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.service.ProductService;
import com.travel.api.vo.Product;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> list() {

        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/save")
    public void save() {
        Product product = Product.builder()
            .title("random_" + UUID.randomUUID().toString().substring(0, 5))
            .build();

        productService.save(product);
    }

    @GetMapping("/edit")
    public void edit() {
        Product product = productService.findById(1L);
        product.setTitle("edit"+ UUID.randomUUID().toString().substring(0, 5));

        productService.save(product);
    }
}
