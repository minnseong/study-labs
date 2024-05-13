package labs.coupon.api.service;

import labs.coupon.api.domain.coupon.Coupon;
import labs.coupon.api.domain.coupon.repository.CouponRepository;
import labs.coupon.api.producer.CouponCreateProducer;
import labs.coupon.api.repository.AppliedUserRepository;
import labs.coupon.api.repository.CouponCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final CouponCountRepository couponCountRepository;
    private final AppliedUserRepository appliedUserRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final CouponRepository couponRepository;

    public void apply(Long userId, Long couponId) {
        Long apply = appliedUserRepository.add(userId);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();

        if (apply != 1) {
            return;
        }

        Long count = couponCountRepository.increment(couponId);

        if (count > coupon.getMaxCount()) {
            return;
        }

        couponCreateProducer.create(userId, couponId);
    }

}
