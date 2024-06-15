package com.travel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.travel.api.vo.Product_mst;

@Repository
public interface ProductRepository extends JpaRepository<Product_mst, Long>, ProductRepositoryCustom {

}
