package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.travel.api.vo.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findByRegionCdIn(List<String> regionCd);

    @Query("SELECT r.regionCd, r.regionNm FROM Region r GROUP BY r.regionCd, r.regionNm")
    List<Object[]> findAllGroupByRegionCdAndRegionNm();
}
