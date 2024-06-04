package com.travel.api.service;

import static com.travel.api.vo.QRegion_mst.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.Tuple;
import com.travel.api.repository.RegionRepositoryImpl;
import com.travel.api.vo.Region_mst;

@Service
public class RegionService {
    private final RegionRepositoryImpl regionRepository;

    @Autowired
    public RegionService(RegionRepositoryImpl regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region_mst> getRegions4(List<String> level4List) {
        List<Tuple> items = regionRepository.findAllGroupByLevel4(level4List);

        List<Region_mst> regionMsts = new ArrayList<>();
        for (Tuple tuple : items) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLEVEL4(tuple.get(region_mst.LEVEL4));
            regionMst.setRegionCd(tuple.get(region_mst.regionCd));
            regionMsts.add(regionMst);
        }

        return regionMsts;
    }

    public List<Region_mst> getRegions2() {
        List<Tuple> items = regionRepository.findAllGroupByLevel2();

        List<Region_mst> regionMsts = new ArrayList<>();
        for (Tuple tuple : items) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLEVEL2(tuple.get(region_mst.LEVEL2));
            regionMst.setRegionCd(tuple.get(region_mst.regionCd));
            regionMsts.add(regionMst);
        }

        return regionMsts;
    }

    public List<String> getRegionCds(List<String> level2List, List<String> level4List) {
        return regionRepository.findRegionCdsByLevel(level2List, level4List);
    }

    public List<Region_mst> getRegionMstsByRegionCds(List<String> regionCds) {
        return regionRepository.findByRegionCdIn(regionCds);
    }
}
