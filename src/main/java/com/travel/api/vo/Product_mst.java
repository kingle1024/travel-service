package com.travel.api.vo;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_mst")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product_mst {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String url;
    private String visitDt;
    private String productCd;
    private Long views;
    private String author;
}
