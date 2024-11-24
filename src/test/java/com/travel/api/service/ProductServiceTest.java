package com.travel.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.travel.api.dto.ProductRegionDto;
import com.travel.api.repository.ProductRepository;
import com.travel.api.vo.Product_mst;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProducts_EmptyProductCds() {
        List<ProductRegionDto> result = productService.getProducts(Collections.emptyList());
        assertEquals(Collections.emptyList(), result);
        verify(productRepository, never()).findByProductCdIn(any());
    }

    @Test
    void testGetProducts_WithProductCds() {
        List<String> productCds = List.of("1", "2");

        // ProductRegionDto의 생성자에 필요한 인자를 제공하여 객체 생성
        ProductRegionDto productRegionDto = new ProductRegionDto(1L, "product1", "description", "region", "category");
        when(productRepository.findByProductCdIn(productCds)).thenReturn(List.of(productRegionDto));

        List<ProductRegionDto> result = productService.getProducts(productCds);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productRegionDto, result.get(0)); // 결과가 예상한 객체와 일치하는지 확인
        verify(productRepository, times(1)).findByProductCdIn(productCds);
    }

    @Test
    void testSave() {
        Product_mst productMst = new Product_mst();
        productService.save(productMst);
        verify(productRepository, times(1)).save(productMst);
    }

    @Test
    void testFindById_Found() {
        long id = 1L;
        Product_mst productMst = new Product_mst();
        when(productRepository.findById(id)).thenReturn(Optional.of(productMst));

        Product_mst result = productService.findById(id);
        assertNotNull(result);
        assertEquals(productMst, result);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        long id = 2L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Product_mst result = productService.findById(id);
        assertNull(result);
        verify(productRepository, times(1)).findById(id);
    }
}
