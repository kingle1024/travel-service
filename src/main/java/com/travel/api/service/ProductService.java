package com.travel.api.service;

import static org.springframework.util.ObjectUtils.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.api.repository.ProductRepository;
import com.travel.api.vo.Product_mst;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product_mst> getProducts(List<String> productCds) {

        if(isEmpty(productCds)) {
            return productRepository.findAll();
        } else {
            // region이 null이면 해당 regionCd에 매칭되는 데이터가 없는 것으로 처리
            if (productCds.isEmpty()) {
                return Collections.emptyList();
            }

            return productRepository.findByProductCdIn(productCds);
        }
    }

    public void save(Product_mst productMst) {
        productRepository.save(productMst);
    }

    public Product_mst findById(long id) {
        Optional<Product_mst> byId = productRepository.findById(id);
        return byId.orElse(null);
    }
}
