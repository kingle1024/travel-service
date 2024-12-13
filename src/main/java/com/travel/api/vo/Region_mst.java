package com.travel.api.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_seq")
    @SequenceGenerator(name = "region_seq", sequenceName = "region_seq", allocationSize = 1)
    private Long id;
    private String regionCd;
    private String level1;
    private String level2;
    private String level3;
    private String level4;
    private String lat;
    private String lng;
    private String title;
    private String rank;
}
