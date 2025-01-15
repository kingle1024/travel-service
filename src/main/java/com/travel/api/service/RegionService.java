package com.travel.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.api.repository.RegionRepository;
import com.travel.api.repository.RegionRepositoryImpl;
import com.travel.api.vo.Region_mst;

@Service
public class RegionService {
    private final RegionRepositoryImpl regionRepositoryImpl;
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepositoryImpl regionRepositoryImpl, RegionRepository regionRepository) {
        this.regionRepositoryImpl = regionRepositoryImpl;
        this.regionRepository = regionRepository;
    }

    public List<Region_mst> getRegions4(List<String> level4List) {
        List<String> items = regionRepositoryImpl.findAllGroupByLevel4(level4List);

        List<Region_mst> regionMsts = new ArrayList<>();
        for (String item : items) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLevel4(item);
            regionMsts.add(regionMst);
        }

        return regionMsts;
    }

    public List<Region_mst> getRegions2() {
        List<String> items = regionRepositoryImpl.findAllGroupByLevel2();

        List<Region_mst> regionMsts = new ArrayList<>();
        for(String item : items) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLevel2(item);
            regionMsts.add(regionMst);
        }

        return regionMsts;
    }

    public List<String> getRegionCds(List<String> level2List, List<String> level4List) {
        return regionRepositoryImpl.findRegionCdsByLevel(level2List, level4List);
    }

    public List<Region_mst> getRegionMstsByRegionCds(List<String> regionCds) {
        return regionRepositoryImpl.findByRegionCdIn(regionCds);
    }

    public List<Region_mst> save(List<Region_mst> regionMst) {
        for (Region_mst region : regionMst) {
            region.setRegionCd(UUID.randomUUID().toString().substring(0, 6));
        }
        regionRepository.saveAll(regionMst);
        return regionMst;
    }
}
