package labs.coupon.api.producer.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponCreateSendRequest {

    private Long userId;
    private Long couponId;

    public static CouponCreateSendRequest of(Long userId, Long couponId) {
        return CouponCreateSendRequest.builder()
                .userId(userId)
                .couponId(couponId)
                .build();
    }
}
