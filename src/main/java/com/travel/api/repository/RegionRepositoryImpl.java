package com.travel.api.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.api.vo.QRegion_mst;
import com.travel.api.vo.Region_mst;

@Repository
public class RegionRepositoryImpl implements RegionRepository {
    private final JPAQueryFactory queryFactory;
    private final QRegion_mst qRegion_mst = QRegion_mst.region_mst;

    @Autowired
    public RegionRepositoryImpl( EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Tuple> findAllGroupByLevel4(List<String> items) {
        return queryFactory
                .select(qRegion_mst.LEVEL4, qRegion_mst.regionCd)
                .from(qRegion_mst)
                .where(items != null ? qRegion_mst.LEVEL2.in(items) : null)
                .groupBy(qRegion_mst.LEVEL4)
                .fetch();
    }

    @Override
    public List<Tuple> findAllGroupByLevel2() {
        return queryFactory
                .select(qRegion_mst.LEVEL2, qRegion_mst.regionCd)
                .from(qRegion_mst)
                .groupBy(qRegion_mst.LEVEL2)
                .fetch();
    }

    @Override
    public List<Region_mst> findByRegionCdIn(List<String> items) {
        return queryFactory
                .select(qRegion_mst)
                .from(qRegion_mst)
                .where(qRegion_mst.regionCd.in(items))
                .fetch();
    }

    @Override
    public List<String> findRegionCdsByLevel(List<String> level2List, List<String> level4List) {
        BooleanBuilder whereClause = new BooleanBuilder();
        if (level4List != null && !level4List.isEmpty()) {
            whereClause.or(qRegion_mst.LEVEL4.in(level4List));
        } else if(level2List != null && !level2List.isEmpty()) {
            whereClause.or(qRegion_mst.LEVEL2.in(level2List));
        }

        return queryFactory
            .select(qRegion_mst.regionCd)
            .from(qRegion_mst)
            .where(whereClause)
            .fetch();
    }
}
