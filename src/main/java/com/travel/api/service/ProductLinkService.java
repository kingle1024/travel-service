package com.travel.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.api.repository.ProductLinkRepository;
import com.travel.api.repository.ProductLinkRepositoryImpl;
import com.travel.api.vo.Product_link;
import com.travel.api.vo.Product_mst;
import com.travel.api.vo.Region_mst;

@Service
public class ProductLinkService {

    private final ProductLinkRepository productLinkRepository;
    private final ProductLinkRepositoryImpl productLinkRepositoryImpl;

    @Autowired
    public ProductLinkService(ProductLinkRepository productLinkRepository,
        ProductLinkRepositoryImpl productLinkRepositoryImpl) {
        this.productLinkRepository = productLinkRepository;
        this.productLinkRepositoryImpl = productLinkRepositoryImpl;
    }

    public List<String> getProductCdByRegionCd(List<String> regionCds) {
        return productLinkRepository.findByRegionCds(regionCds);
    }

    public List<String> getProductCdByProductCd(String productCd) {
        return productLinkRepository.findByProductCd(productCd);
    }

    public void save(Product_mst productItem, List<Region_mst> regionItems) {

        List<Product_link> items = new ArrayList<>();
        for (Region_mst item : regionItems) {
            Product_link vo = Product_link.builder()
                .productCd(productItem.getProductCd())
                .regionCd(item.getRegionCd())
                .build();
            items.add(vo);
        }
        productLinkRepository.saveAll(items);
    }
}
