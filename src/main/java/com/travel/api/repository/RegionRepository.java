package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.api.vo.Region_mst;

public interface RegionRepository extends JpaRepository<Region_mst, Long> {

}
