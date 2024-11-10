package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.travel.api.vo.Like_mst;

@Repository
public interface LikeRepository extends JpaRepository<Like_mst, Long>, QuerydslPredicateExecutor<Like_mst> {
    List<Like_mst> findByUserId(String userId);
}
