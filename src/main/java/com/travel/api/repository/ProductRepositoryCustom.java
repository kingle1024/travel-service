package com.travel.api.repository;

import java.util.List;

import com.travel.api.dto.ProductRegionDto;

public interface ProductRepositoryCustom {
    List<ProductRegionDto> findByProductCdIn(List<String> productCds);
}
