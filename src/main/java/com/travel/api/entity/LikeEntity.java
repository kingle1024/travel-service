package com.travel.api.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.travel.api.vo.LikeCompositeKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "like_mst")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeEntity {
    @EmbeddedId
    private LikeCompositeKey id; // 복합 키로 설정

    // 기본 생성자
    public LikeEntity(String userId, String productCd) {
        this.id = new LikeCompositeKey(userId, productCd);
    }
    public String getProductCd() {
        return id.getProductCd(); // LikeCompositeKey에서 productCd 가져오기
    }
}
