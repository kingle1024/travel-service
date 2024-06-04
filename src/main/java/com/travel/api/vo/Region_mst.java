package com.travel.api.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "region_mst")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region_mst {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regionCd;
    private String LEVEL1;
    private String LEVEL2;
    private String LEVEL3;
    private String LEVEL4;
    private String lat;
    private String lng;
    private String title;
}
