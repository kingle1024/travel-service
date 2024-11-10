package com.travel.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.service.LikeService;
import com.travel.api.service.ProductService;
import com.travel.api.vo.Like_mst;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final LikeService likeService;
    private final ProductService productService;

    @Autowired
    public MypageController(LikeService likeService, ProductService productService) {
        this.likeService = likeService;
        this.productService = productService;
    }

    @GetMapping("/likes")
    public ResponseEntity<Map<String, Object>> likes(HttpServletRequest request) {
        final String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                .body(Map.of("error", "로그인 정보가 없습니다."));
        }

        List<Like_mst> items = likeService.getProductCds(userId);
        List<String> productCds = new ArrayList<>();
        for (Like_mst like : items) {
            productCds.add(like.getProductCd());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("product", productService.getProducts(productCds));
        return ResponseEntity.ok(result);
    }
}
