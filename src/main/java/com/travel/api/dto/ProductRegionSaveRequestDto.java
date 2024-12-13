package com.travel.api.dto;

import java.util.List;

import com.travel.api.vo.Product_mst;
import com.travel.api.vo.Region_mst;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRegionSaveRequestDto {
    private Product_mst productMst;
    private List<Region_mst> regionMst;
}
