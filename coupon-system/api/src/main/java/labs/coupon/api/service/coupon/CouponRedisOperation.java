package labs.coupon.api.service.coupon;

import labs.coupon.api.service.coupon.vo.CouponVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponRedisOperation {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String COUPON_KEY_PATTERN = "coupon:%s";
    private static final String COUPON_APPLY_USERS_KEY_PATTERN = "coupon:%s:users";

    public Long addSet(CouponVO couponVO) {

        String key = generateKey(COUPON_APPLY_USERS_KEY_PATTERN, couponVO);

        return redisTemplate
                .opsForSet()
                .add(key, couponVO.getUserId());
    }

    public Long incr(CouponVO couponVO) {

        String key = generateKey(COUPON_KEY_PATTERN, couponVO);

        return redisTemplate
                .opsForValue()
                .increment(key);
    }

    private static String generateKey(String pattern, CouponVO couponVO) {
        return String.format(pattern, couponVO.getCouponId());
    }
}
