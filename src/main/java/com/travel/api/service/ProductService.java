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
import com.travel.api.vo.Product_mst;
import com.travel.api.vo.Region_mst;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RegionRepository regionRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, RegionRepository regionRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.regionRepository = regionRepository;
    }

    public List<Product_mst> getProducts(String regionCd) {

        if(isEmpty(regionCd)) {
            return productRepository.findAll();
        } else {
            // regionCd를 이용하여 Region을 조회
            List<Region_mst> regionMsts = regionRepository.findByRegionCdIn(Arrays.asList(regionCd.split("\\|")));

            // region이 null이면 해당 regionCd에 매칭되는 데이터가 없는 것으로 처리
            if (regionMsts == null) {
                return Collections.emptyList();
            }

            List<String> productCds = new ArrayList<>();
            for(Region_mst item : regionMsts) {
                productCds.add(item.getLEVEL4());
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
