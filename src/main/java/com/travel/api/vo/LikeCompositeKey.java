package com.travel.api.vo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class LikeCompositeKey implements Serializable {
    private final String userId;
    private final String productCd;

    public LikeCompositeKey(String userId, String productCd) {
        this.userId = userId;
        this.productCd = productCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeCompositeKey)) return false;
        LikeCompositeKey that = (LikeCompositeKey) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(productCd, that.productCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productCd);
    }
}
