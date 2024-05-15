package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.travel.api.vo.Region_mst;

@Repository
public interface RegionRepository extends JpaRepository<Region_mst, Long> {

    List<Region_mst> findByRegionCdIn(List<String> regionCd);

    @Query("SELECT r.LEVEL4 FROM Region_mst r GROUP BY r.LEVEL4")
    List<String> findAllGroupByLevel4();

    @Query("SELECT r.LEVEL2 FROM Region_mst r GROUP BY r.LEVEL2")
    List<String> findAllGroupByLevel2();
}
