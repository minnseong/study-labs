package labs.coupon.consumer.consumer.message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponCreateMessage {

    private Long userId;
    private Long couponId;

    public static CouponCreateMessage of(Long userId, Long couponId) {
        return CouponCreateMessage.builder()
                .userId(userId)
                .couponId(couponId)
                .build();
    }
}
