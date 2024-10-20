package com.travel.api.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.querydsl.core.Tuple;
import com.travel.api.repository.RegionRepositoryImpl;
import com.travel.api.vo.QRegion_mst;
import com.travel.api.vo.Region_mst;

@Service
public class RegionService {
    private final RegionRepositoryImpl regionRepository;
    private final QRegion_mst qRegion_mst = QRegion_mst.region_mst;

    @Autowired
    public RegionService(RegionRepositoryImpl regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region_mst> getRegions4(List<String> level4List) {
        List<String> items = regionRepository.findAllGroupByLevel4(level4List);

        List<Region_mst> regionMsts = new ArrayList<>();
        for (String item : items) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLEVEL4(item);
            regionMsts.add(regionMst);
        }

        return regionMsts;
    }

    public List<Region_mst> getRegions2() {
        List<String> items = regionRepository.findAllGroupByLevel2();

        List<Region_mst> regionMsts = new ArrayList<>();
        for(String item : items) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLEVEL2(item);
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
