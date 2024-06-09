package com.travel.api.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.api.vo.QProduct_link;

@Repository
public class ProductLinkRepositoryImpl implements ProductLinkRepository {
    private final JPAQueryFactory queryFactory;
    private final QProduct_link qProductLink = QProduct_link.product_link;

    @Autowired
    public ProductLinkRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<String> findByRegionCds(List<String> regionCds) {
        return queryFactory
                .select(qProductLink.product_link.productCd)
                .from(qProductLink.product_link)
                .where(qProductLink.regionCd.in(regionCds))
                .fetch();
    }

    @Override
    public List<String> findByProductCd(String productCd) {
        return queryFactory
            .select(qProductLink.product_link.regionCd)
            .from(qProductLink.product_link)
            .where(qProductLink.product_link.productCd.eq(productCd))
            .fetch();
    }
}
