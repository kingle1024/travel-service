package com.travel.api.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.api.dto.ProductRegionDto;
import com.travel.api.vo.Product_mst;
import com.travel.api.vo.QProduct_link;
import com.travel.api.vo.QProduct_mst;
import com.travel.api.vo.QRegion_mst;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QProduct_mst qProduct_mst = QProduct_mst.product_mst;
    private final QProduct_link qProduct_link = QProduct_link.product_link;
    private final QRegion_mst qRegion_mst = QRegion_mst.region_mst;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProductRegionDto> findByProductCdIn(List<String> productCds) {

        // product_link와 region_mst와 join하여 productCd와 title을 가져옴.
        List<Tuple> regionResults = queryFactory
            .select(
                qProduct_link.productCd,
                qRegion_mst.title
            )
            .from(qProduct_link)
            .join(qRegion_mst).on(qProduct_link.regionCd.eq(qRegion_mst.regionCd))
            .where(qProduct_link.productCd.in(productCds))
            .fetch();

        // productCd의 regionTitle을 추가
        Map<String, List<String>> regionMap = new HashMap<>();
        for (Tuple tuple : regionResults) {
            String productCd = tuple.get(qProduct_link.productCd);
            String regionTitle = tuple.get(qRegion_mst.title);

            regionMap.computeIfAbsent(productCd, k -> new ArrayList<>()).add(regionTitle);
        }

        // products 항목들을 가져옴
        List<Product_mst> products = queryFactory
            .selectFrom(qProduct_mst)
            .where(qProduct_mst.productCd.in(productCds))
            .fetch();

        // result 데이터 만들기
        List<ProductRegionDto> result = new ArrayList<>();
        for (Product_mst product : products) {
            String productCd = product.getProductCd();
            String regionTitles = String.join(",", regionMap.getOrDefault(productCd, Collections.emptyList()));

            result.add(new ProductRegionDto(
                product.getId(),
                product.getTitle(),
                product.getUrl(),
                product.getProductCd(),
                regionTitles
            ));
        }

        return result;
    }
}
