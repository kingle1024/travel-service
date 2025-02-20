package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.api.vo.Product_link;

@Repository
public interface ProductLinkRepository extends JpaRepository<Product_link, Long>, ProductLinkRepositoryCustom {

}
