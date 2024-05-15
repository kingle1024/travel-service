package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.api.vo.Product_mst;

@Repository
public interface ProductRepository extends JpaRepository<Product_mst, Long> {
    List<Product_mst> findByProductCdIn(List<String> productCds);
}
