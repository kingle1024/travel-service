package com.travel.api.repository;

import static com.travel.api.vo.QProduct_link.*;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ProductLinkRepositoryImpl implements ProductLinkRepository {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProductLinkRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);;
    }

    @Override
    public List<String> findByRegionCds(List<String> regionCds) {
        return queryFactory
                .select(product_link.productCd)
                .from(product_link)
                .where(product_link.regionCd.in(regionCds))
                .fetch();
    }
}
