package com.travel.api.repository;

import java.util.List;

public interface ProductLinkRepositoryCustom {
    List<String> findByRegionCds(List<String> productCds);
    List<String> findByProductCd(String productCd);
}
