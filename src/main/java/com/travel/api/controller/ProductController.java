package com.travel.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.service.ProductService;
import com.travel.api.service.RegionService;
import com.travel.api.vo.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProductController {

    private final ProductService productService;
    private final RegionService regionService;

    @Autowired
    public ProductController(ProductService productService, RegionService regionService) {
        this.productService = productService;
        this.regionService = regionService;
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list (
        @RequestParam(required = false) String regionCd
    ) {
        Map<String, Object> items = new HashMap<>();
        items.put("product", productService.getProducts(regionCd));
        items.put("region", regionService.getRegions());

        return ResponseEntity.ok(items);
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
