package com.travel.api.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.travel.api.service.ProductLinkService;
import com.travel.api.service.ProductService;
import com.travel.api.service.RegionService;
import com.travel.api.vo.Product_mst;
import com.travel.api.vo.Region_mst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductLinkService productLinkService;
    private final RegionService regionService;

    @Autowired
    public ProductController(ProductService productService, ProductLinkService productLinkService, RegionService regionService) {
        this.productService = productService;
        this.productLinkService = productLinkService;
        this.regionService = regionService;
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
    public ResponseEntity<Map<String, Object>> detail(@PathVariable Long id) {
        Product_mst product = productService.findById(id);
        // List<REGION_CD>를 가져온다.
        List<String> regionCds = productLinkService.getProductCdByProductCd(String.valueOf(id));
        // x, y좌표를 가져온다.
        List<Region_mst> regions = regionService.getRegionMstsByRegionCds(regionCds);
        Map<String, Object> results = new HashMap<>();
        results.put("product", product);
        results.put("regions", regions);

        if(product == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }
}
