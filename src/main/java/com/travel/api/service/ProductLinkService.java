package com.travel.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.api.repository.ProductLinkRepository;

@Service
public class ProductLinkService {

    private final ProductLinkRepository productLinkRepository;

    @Autowired
    public ProductLinkService(ProductLinkRepository productLinkRepository) {
        this.productLinkRepository = productLinkRepository;
    }

    public List<String> getProductCdByRegionCd(List<String> regionCds) {
        return productLinkRepository.findByRegionCds(regionCds);
    }

    public List<String> getProductCdByProductCd(String productCd) {
        return productLinkRepository.findByProductCd(productCd);
    }
}
