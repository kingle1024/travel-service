package com.travel.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.travel.api.entity.LikeEntity;
import com.travel.api.vo.LikeCompositeKey;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, LikeCompositeKey>, QuerydslPredicateExecutor<LikeEntity> {
    List<LikeEntity> findById_UserId(String userId);

    Optional<LikeEntity> findById_UserIdAndId_ProductCd(String userId, String productId);
}
