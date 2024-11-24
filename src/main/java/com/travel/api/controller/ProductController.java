package com.travel.api.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.filter.AuthFilter;
import com.travel.api.service.CommentService;
import com.travel.api.service.LikeService;
import com.travel.api.service.ProductLinkService;
import com.travel.api.service.ProductService;
import com.travel.api.service.RegionService;
import com.travel.api.vo.Comment_mst;
import com.travel.api.vo.Product_mst;
import com.travel.api.vo.Region_mst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductLinkService productLinkService;
    private final RegionService regionService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final AuthFilter authFilter;

    @Autowired
    public ProductController(ProductService productService, ProductLinkService productLinkService, RegionService regionService,
        CommentService commentService, LikeService likeService, AuthFilter authFilter) {
        this.productService = productService;
        this.productLinkService = productLinkService;
        this.regionService = regionService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.authFilter = authFilter;
    }



    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list (
        @RequestParam(required = false) String level2,
        @RequestParam(required = false) String level4
    ) {

        Map<String, Object> items = new HashMap<>();
        List<String> level2List = null;
        if (level2 != null && !level2.isEmpty()) {
            level2List = Arrays.asList(level2.split("\\|"));
        }
        List<String> level4List = null;
        if (level4 != null && !level4.isEmpty()) {
            level4List = Arrays.asList(level4.split("\\|"));
        }

        List<Region_mst> regions2 = regionService.getRegions2();
        List<Region_mst> regions4 = regionService.getRegions4(level2List);

        items.put("region2", regions2);
        items.put("region4", regions4);

        List<String> regionCdsList = regionService.getRegionCds(level2List, level4List);
        List<String> productCds = productLinkService.getProductCdByRegionCd(regionCdsList);
        items.put("product", productService.getProducts(productCds));

        return ResponseEntity.ok(items);
    }

    @GetMapping("/save")
    public void save() {
        Product_mst productMst = Product_mst.builder()
            .title("random_" + UUID.randomUUID().toString().substring(0, 5))
            .build();

        productService.save(productMst);
    }

    @GetMapping("/edit")
    public void edit() {
        Product_mst productMst = productService.findById(1L);
        productMst.setTitle("edit"+ UUID.randomUUID().toString().substring(0, 5));

        productService.save(productMst);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Map<String, Object>> detail(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        Product_mst product = productService.findById(id);
        final String productCd = String.valueOf(id);
        List<String> regionCds = productLinkService.getProductCdByProductCd(productCd);
        List<Region_mst> regions = regionService.getRegionMstsByRegionCds(regionCds);
        List<Comment_mst> comments = commentService.getCommentsByProductCd(productCd);

        Map<String, Object> results = new HashMap<>();
        final String userId = authFilter.getUserIdByRequest(request);
        boolean isLiked = likeService.isLiked(productCd, userId);
        results.put("like", isLiked);
        results.put("product", product);
        results.put("regions", regions);
        results.put("comments", comments);
        addViewCount(id, productCd, request, response);

        if(product == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }

    private void addViewCount(Long id, String productCd, HttpServletRequest request, HttpServletResponse response) {
        // 쿠키를 통해 조회수 증가 로직
        Cookie[] cookies = request.getCookies();
        String viewCountCookieName = "viewCount_" + productCd; // 제품별 조회수 쿠키 이름
        boolean hasVisited = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(viewCountCookieName)) {
                    hasVisited = true;
                    break;
                }
            }
        }

        // 쿠키가 존재하지 않는 경우 조회수 증가
        if (!hasVisited) {
            productService.incrementViewCount(id); // 서비스 메서드 호출
            Cookie viewCountCookie = new Cookie(viewCountCookieName, "1");
            viewCountCookie.setMaxAge(60 * 60 * 24); // 쿠키 유효 기간 설정 (1일)
            response.addCookie(viewCountCookie); // 응답에 쿠키 추가
        }
    }

    @PostMapping("/likes/{productCd}")
    public ResponseEntity<Void> likePost(@PathVariable String productCd, HttpServletRequest request) {
        return handleLike(productCd, true, request);
    }

    @DeleteMapping("/likes/{productCd}")
    public ResponseEntity<Void> likeDelete(@PathVariable String productCd, HttpServletRequest request) {
        return handleLike(productCd, false, request);
    }

    private ResponseEntity<Void> handleLike(String productCd, boolean isLike, HttpServletRequest request) {
        final String userId = authFilter.getUserIdByRequest(request);
        final boolean consumed = likeService.processLike(productCd, userId, isLike);

        if (consumed) {
            if(isLike) {
                likeService.queueLike(productCd, userId);
            } else {
                likeService.queueUnlike(productCd, userId);
            }

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build(); // 429 상태코드 반환
        }
    }
}
