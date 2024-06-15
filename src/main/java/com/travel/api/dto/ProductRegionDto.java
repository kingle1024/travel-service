package com.travel.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRegionDto {
    private Long id;
    private String title;
    private String url;
    private String productCd;
    private String regionTitle;

    public ProductRegionDto(Long id, String title, String url, String productCd, String regionTitle) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.productCd = productCd;
        this.regionTitle = regionTitle;
    }

}
