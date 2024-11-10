package com.travel.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.api.repository.LikeRepository;
import com.travel.api.vo.Like_mst;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public List<Like_mst> getProductCds(String userId) {
        return likeRepository.findByUserId(userId);
    }
}
