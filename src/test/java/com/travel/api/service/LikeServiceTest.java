package com.travel.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.travel.api.entity.LikeEntity;
import com.travel.api.repository.LikeRepository;
import com.travel.api.vo.LikeCompositeKey;

import io.github.bucket4j.Bucket;


class LikeServiceTest {
    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private Bucket bucket;

    private final String userId = "user123";
    private final String productCd = "product456";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        likeService.setBucket(productCd + ":" + userId, bucket);
    }

    @Test
    public void testProcessLike_Success() {
        // Given
        when(bucket.tryConsume(1)).thenReturn(true);
        when(likeRepository.findById_UserIdAndId_ProductCd(userId, productCd)).thenReturn(Optional.empty());

        // When
        boolean result = likeService.processLike(productCd, userId, true);

        // Then
        assertTrue(result);
        List<LikeEntity> pendingLikes = likeService.getPendingLikes();
        assertEquals(1, pendingLikes.size());
        assertEquals(userId, pendingLikes.get(0).getId().getUserId());
        assertEquals(productCd, pendingLikes.get(0).getId().getProductCd());
    }

    @Test
    public void testProcessLike_AlreadyLiked() {
        // Given
        when(bucket.tryConsume(1)).thenReturn(true);
        LikeEntity existingLike = new LikeEntity();
        existingLike.setId(new LikeCompositeKey(userId, productCd));
        when(likeRepository.findById_UserIdAndId_ProductCd(userId, productCd)).thenReturn(Optional.of(existingLike));

        // When
        boolean result = likeService.processLike(productCd, userId, true);

        // Then
        assertTrue(result);
        verify(likeRepository, never()).saveAll(anyList());
    }

    @Test
    public void testProcessLike_RateLimitExceeded() {
        // Given
        when(bucket.tryConsume(1)).thenReturn(false);

        // When
        boolean result = likeService.processLike(productCd, userId, true);

        // Then
        assertFalse(result);
        verify(likeRepository, never()).saveAll(anyList());
    }

    @Test
    public void testProcessUnlike_Success() {
        // Given
        when(bucket.tryConsume(1)).thenReturn(true);
        when(likeRepository.findById_UserIdAndId_ProductCd(userId, productCd)).thenReturn(Optional.of(new LikeEntity()));

        // When
        boolean result = likeService.processLike(productCd, userId, false);

        // Then
        assertTrue(result);
        List<LikeEntity> pendingUnLikes = likeService.getPendingUnLikes();
        assertEquals(1, pendingUnLikes.size());
        assertEquals(userId, pendingUnLikes.get(0).getId().getUserId());
        assertEquals(productCd, pendingUnLikes.get(0).getId().getProductCd());
    }

    @Test
    public void testProcessUnlike_NotLiked() {
        // Given
        when(bucket.tryConsume(1)).thenReturn(true);
        when(likeRepository.findById_UserIdAndId_ProductCd(userId, productCd)).thenReturn(Optional.empty());

        // When
        boolean result = likeService.processLike(productCd, userId, false);

        // Then
        assertTrue(result);
        verify(likeRepository, never()).deleteAll(anyList());
    }
}
