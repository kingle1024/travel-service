package com.travel.api.service;

import static org.springframework.util.ObjectUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.api.repository.ProductRepository;
import com.travel.api.repository.RegionRepository;
import com.travel.api.vo.Product;
import com.travel.api.vo.Region;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RegionRepository regionRepository;
    private final EntityManager entityManager;
    @Autowired
    public ProductService(ProductRepository productRepository, RegionRepository regionRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.regionRepository = regionRepository;
        this.entityManager = entityManager;
    }

    public List<Product> getProducts(String regionCd) {

        if(isEmpty(regionCd)) {
            return productRepository.findAll();
        } else {
            // regionCd를 이용하여 Region을 조회
            List<Region> regions = regionRepository.findByRegionCdIn(Arrays.asList(regionCd.split("\\|")));

            // region이 null이면 해당 regionCd에 매칭되는 데이터가 없는 것으로 처리
            if (regions == null) {
                return Collections.emptyList();
            }

            List<String> productCds = new ArrayList<>();
            for(Region item : regions) {
                productCds.add(item.getProductCd());
            }

            return productRepository.findByProductCdIn(productCds);
        }
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product findById(long id) {
        Optional<Product> byId = productRepository.findById(id);
        return byId.orElse(null);
    }
}
