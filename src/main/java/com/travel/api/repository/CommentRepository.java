package com.travel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.travel.api.vo.Comment_mst;

@Repository
public interface CommentRepository extends JpaRepository<Comment_mst, Long>, QuerydslPredicateExecutor<Comment_mst> {
    List<Comment_mst> findByProductCdOrderByInsertDtsDesc(String productCd);
}
