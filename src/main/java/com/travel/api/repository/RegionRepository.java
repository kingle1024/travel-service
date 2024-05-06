package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.api.vo.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findByRegionCdIn(List<String> regionCd);
}
