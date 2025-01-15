package com.travel.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductLinkRequestDto {
    String productCd;
    String regionCd;
    Long rank;
}
