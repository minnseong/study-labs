package labs.coupon.api.service.coupon.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponVO {

    private String couponId;
    private String userId;

    public static CouponVO of(Long couponId, Long userId) {
        return CouponVO.builder()
                .couponId(couponId.toString())
                .userId(userId.toString())
                .build();
    }
}
