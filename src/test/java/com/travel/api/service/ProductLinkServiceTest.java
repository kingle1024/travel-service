package com.travel.api.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import com.travel.api.repository.ProductLinkRepository;

class ProductLinkServiceTest {
    @Mock
    private ProductLinkRepository productLinkRepository;

    @InjectMocks
    private ProductLinkService productLinkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductCdByRegionCd() {
        // Given
        List<String> regionCds = Arrays.asList("REGION1", "REGION2");
        List<String> expectedProductCds = Arrays.asList("PRODUCT1", "PRODUCT2");

        when(productLinkRepository.findByRegionCds(regionCds)).thenReturn(expectedProductCds);

        // When
        List<String> actualProductCds = productLinkService.getProductCdByRegionCd(regionCds);

        // Then
        assertEquals(expectedProductCds, actualProductCds);
        verify(productLinkRepository).findByRegionCds(regionCds);
    }

    @Test
    public void testGetProductCdByProductCd() {
        // Given
        String productCd = "PRODUCT1";
        List<String> expectedProductCds = Arrays.asList("REGION1", "REGION2");

        when(productLinkRepository.findByProductCd(productCd)).thenReturn(expectedProductCds);

        // When
        List<String> actualProductCds = productLinkService.getProductCdByProductCd(productCd);

        // Then
        assertEquals(expectedProductCds, actualProductCds);
        verify(productLinkRepository).findByProductCd(productCd);
    }
}
