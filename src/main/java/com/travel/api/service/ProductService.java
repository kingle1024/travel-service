package com.travel.api.service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.api.dto.ProductRegionDto;
import com.travel.api.repository.ProductRepository;
import com.travel.api.vo.Product_mst;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductRegionDto> getProducts(List<String> productCds) {

        if (productCds == null || productCds.isEmpty()) {
            return Collections.emptyList(); // 빈 리스트를 반환
        }

        return productRepository.findByProductCdIn(productCds);
    }

    public void save(Product_mst productMst) {
        productRepository.save(productMst);
    }

    public Product_mst save(Product_mst productMst, String userId) {
        productMst.setViews(0L);
        productMst.setAuthor(userId);
        productMst.setProductCd(UUID.randomUUID().toString().substring(0, 6));
        productRepository.save(productMst);
        return productMst;
    }

    public Product_mst findById(long id) {
        Optional<Product_mst> byId = productRepository.findById(id);
        return byId.orElse(null);
    }

    public void addViewCount(Long id, String productCd, HttpServletRequest request, HttpServletResponse response) {
        // 쿠키를 통해 조회수 증가 로직
        Cookie[] cookies = request.getCookies();
        String viewCountCookieName = "viewCount_" + productCd; // 제품별 조회수 쿠키 이름

        // 쿠키가 존재하지 않는 경우 조회수 증가
        if (!hasVisited(cookies, viewCountCookieName)) {
            Product_mst product = findById(id);

            if (product != null) {
                incrementViewCount(product);
                setViewCountCookie(viewCountCookieName, response);
            }
        }
    }

    private void incrementViewCount(Product_mst product) {
        // 조회수 증가
        product.setViews(product.getViews() + 1);
        // 변경된 제품 저장
        save(product);
    }

    private void setViewCountCookie(String cookieName, HttpServletResponse response) {
        Cookie viewCountCookie = new Cookie(cookieName, "1");
        viewCountCookie.setMaxAge(60 * 60 * 24); // 쿠키 유효 기간 설정 (1일)
        response.addCookie(viewCountCookie);

    }

    private boolean hasVisited(Cookie[] cookies, String viewCountCookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(viewCountCookieName)) {
                    return true;
                }
            }
        }

        return false;
    }
}
