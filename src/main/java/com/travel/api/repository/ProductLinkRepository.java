package com.travel.api.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLinkRepository {
    List<String> findByRegionCds(List<String> productCds);

    List<String> findByProductCd(String productCd);
}
