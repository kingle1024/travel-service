package com.travel.api.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.travel.api.repository.ProductRepository;
import com.travel.api.repository.RegionRepositoryImpl;
import com.travel.api.vo.Product_mst;

@SpringBootTest
class ProductMstServiceTest {
    private final ProductRepository productRepository;
    private final RegionRepositoryImpl regionRepository;
    private final EntityManager entityManager;

    @Autowired
    public ProductMstServiceTest(ProductRepository productRepository, RegionRepositoryImpl regionRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.regionRepository = regionRepository;
        this.entityManager = entityManager;
    }

    @Test
    public void list() {
        ProductService productService = new ProductService(productRepository);
        // List<Product_mst> productMsts = productService.getProducts(Collections.emptyList());

        // assertThat(productMsts).isNotNull();
        // assertThat(productMsts.get(0).getId()).isEqualTo(1L); // 예시로 첫 번째 제품의 ID가 1인지 확인합니다.
        // assertThat(products.size()).isEqualTo(1); // 예시로 데이터베이스에 2개의 제품이 있다고 가정합니다.
    }


}
