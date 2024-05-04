package com.travel.api.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.travel.api.repository.ProductRepository;
import com.travel.api.vo.Product;

@SpringBootTest
class ProductServiceTest {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    public void list() {
        ProductService productService = new ProductService(productRepository);
        List<Product> products = productService.getProducts();

        assertThat(products).isNotNull();
        assertThat(products.get(0).getId()).isEqualTo(1L); // 예시로 첫 번째 제품의 ID가 1인지 확인합니다.
        // assertThat(products.size()).isEqualTo(1); // 예시로 데이터베이스에 2개의 제품이 있다고 가정합니다.
    }


}
