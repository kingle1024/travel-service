package com.travel.api.repository;

import java.util.List;

import com.querydsl.core.Tuple;
import com.travel.api.vo.Region_mst;

public interface RegionRepository {
    List<Tuple> findAllGroupByLevel2();
    List<Tuple> findAllGroupByLevel4(List<String> items);
    List<Region_mst> findByRegionCdIn(List<String> items);
    List<String> findRegionCdsByLevel(List<String> level2List, List<String> level4List);
}
