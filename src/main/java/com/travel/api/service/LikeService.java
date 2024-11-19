package com.travel.api.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.api.repository.LikeRepository;
import com.travel.api.entity.LikeEntity;
import com.travel.api.vo.LikeCompositeKey;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class LikeService {

    private static final Logger log = LoggerFactory.getLogger(LikeService.class);
    private final Map<String, Bucket> buckets = new HashMap<>();
    private final LikeRepository likeRepository;

    // 대기 중인 좋아요 리스트
    private final List<LikeEntity> pendingLikes = new ArrayList<>();
    private final List<LikeEntity> pendingUnLikes = new ArrayList<>();

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public List<LikeEntity> getProductCds(String userId) {
        return likeRepository.findById_UserId(userId);
    }

    public boolean isLiked(String productCd, String userId) {
        Optional<LikeEntity> isLiked = likeRepository.findById_UserIdAndId_ProductCd(userId, productCd);
        return isLiked.isPresent();
    }

    public void queueLike(String productCd, String userId) {
        LikeCompositeKey likeKey = new LikeCompositeKey(userId, productCd);

        LikeEntity likeMst = LikeEntity.builder()
                .id(likeKey)
                .build();

        if(!pendingLikes.contains(likeMst)) {
            pendingLikes.add(likeMst);
        }
        pendingUnLikes.remove(likeMst);
    }

    public void queueUnlike(String productCd, String userId) {
        LikeCompositeKey likeKey = new LikeCompositeKey(userId, productCd);
        LikeEntity likeMst = LikeEntity.builder()
                .id(likeKey) // 복합 키 설정
                .build();

        if(!pendingUnLikes.contains(likeMst)) {
            pendingUnLikes.add(likeMst);
        }
        pendingLikes.remove(likeMst);
    }


    @Transactional
    public void processLikesInBatch() {
        try {
            if (!pendingLikes.isEmpty()) {
                likeRepository.saveAll(pendingLikes); // 여러 좋아요를 배치로 저장
                pendingLikes.clear(); // 처리 후 리스트 초기화
            }

            if (!pendingUnLikes.isEmpty()) {
                likeRepository.deleteAll(pendingUnLikes); // 여러 좋아요를 배치로 삭제
                pendingUnLikes.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error processing likes in batch", e);
        }
    }

    @Scheduled(fixedRate = 60_000) // 1분마다 실행
    public void scheduleProcessLikes() {
        processLikesInBatch(); // 좋아요 추가
    }

    public boolean processLike(String productCd, String userId, boolean isLike) {
        Bucket userBucket = resolveBucket(productCd, userId);
        boolean consumed = userBucket.tryConsume(1);

        if(consumed) {
            if(isLike) {
                queueLike(productCd, userId);
            } else {
                queueUnlike(productCd, userId);
            }
            return true;
        } else {
            return false;
        }
    }

    // 사용자별 Bucket 생성 또는 반환
    protected Bucket resolveBucket(String productCd, String userId) {
        final String key = productCd + ":" + userId; // 조합 키 생성
        return buckets.computeIfAbsent(key, k -> Bucket.builder()
                .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofSeconds(5)))) // 10초에 3번 호출 허용
                .build());
    }

    public List<LikeEntity> getPendingLikes() {
        return new ArrayList<>(pendingLikes);
    }

    public List<LikeEntity> getPendingUnLikes() {
        return new ArrayList<>(pendingUnLikes);
    }

    public void setBucket(String key, Bucket bucket) {
        buckets.put(key, bucket);
    }
}
