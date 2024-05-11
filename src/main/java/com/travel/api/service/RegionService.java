package com.travel.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.api.repository.RegionRepository;
import com.travel.api.vo.Region;

@Service
public class RegionService {
    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {
        List<Object[]> results = regionRepository.findAllGroupByRegionCdAndRegionNm();
        List<Region> regions = new ArrayList<>();
        for (Object[] result : results) {
            Region region = new Region();
            region.setRegionCd((String) result[0]);
            region.setRegionNm((String) result[1]);
            regions.add(region);
        }

        return regions;
    }
}
