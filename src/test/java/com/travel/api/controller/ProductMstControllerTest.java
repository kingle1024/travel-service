package com.travel.api.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import com.travel.api.filter.AuthFilter;
import com.travel.api.service.CommentService;
import com.travel.api.service.LikeService;
import com.travel.api.service.ProductLinkService;
import com.travel.api.service.ProductService;
import com.travel.api.service.RegionService;
import com.travel.api.vo.Product_mst;

@WebMvcTest(ProductController.class)
class ProductMstControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductLinkService productLinkService;

    @MockBean
    private RegionService regionService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private LikeService likeService;

    @Mock
    private AuthFilter authFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDetail_withNewViewCount() throws Exception {
        Long productId = 1L;
        Product_mst product = new Product_mst(); // 필요한 속성을 설정
        product.setId(productId); // 예시로 ID 설정
        String productCd = String.valueOf(productId);

        when(productService.findById(productId)).thenReturn(product);
        when(productLinkService.getProductCdByProductCd(productCd)).thenReturn(new ArrayList<>());
        when(regionService.getRegionMstsByRegionCds(anyList())).thenReturn(new ArrayList<>());
        when(commentService.getCommentsByProductCd(productCd)).thenReturn(new ArrayList<>());
        when(likeService.isLiked(anyString(), anyString())).thenReturn(false);
        when(authFilter.getUserIdByRequest(any())).thenReturn("testUser");

        // MockHttpServletRequest에 속성 설정
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("userId", "testUser"); // 속성 이름이 null이 아님을 보장

        mockMvc.perform(get("/detail/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("userId", "testUser")) // 세션 속성으로 userId 설정
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").exists());

        verify(productService).incrementViewCount(productId); // 조회수 증가 확인
    }

    @Test
    public void testDetail_productNotFound() throws Exception {
        Long productId = 2L;

        when(productService.findById(productId)).thenReturn(null);

        mockMvc.perform(get("/detail/{id}", productId))
                .andExpect(status().isNotFound());
    }
}
