package com.travel.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.api.repository.RegionRepository;
import com.travel.api.vo.Region_mst;

@Service
public class RegionService {
    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region_mst> getRegions4() {
        List<String> results = regionRepository.findAllGroupByLevel4();

        List<Region_mst> regionMsts = new ArrayList<>();
        for (String result : results) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLEVEL4(result);
            regionMsts.add(regionMst);
        }

        return regionMsts;
    }

    public Object getRegions2() {
        List<String> results = regionRepository.findAllGroupByLevel2();

        List<Region_mst> regionMsts = new ArrayList<>();
        for (String result : results) {
            Region_mst regionMst = new Region_mst();
            regionMst.setLEVEL2(result);
            regionMsts.add(regionMst);
        }

        return regionMsts;
    }
}
