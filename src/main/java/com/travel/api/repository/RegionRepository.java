package com.travel.api.repository;

import java.util.List;

import com.travel.api.vo.Region_mst;

public interface RegionRepository {
    List<String> findAllGroupByLevel2();
    List<String> findAllGroupByLevel4(List<String> items);
    List<Region_mst> findByRegionCdIn(List<String> items);
    List<String> findRegionCdsByLevel(List<String> level2List, List<String> level4List);
}
