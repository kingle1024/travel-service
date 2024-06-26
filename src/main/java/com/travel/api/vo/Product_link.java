package com.travel.api.vo;

import javax.persistence.Entity;
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
@Table(name = "product_link")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product_link {
    @Id
    private Long id;
    private String productCd;
    private String regionCd;
    private int rank;
}
