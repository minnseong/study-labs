package labs.coupon.api.service;

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

    public void apply(Long userId) {
        Long apply = appliedUserRepository.add(userId);

        if (apply != 1) {
            return;
        }

        Long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }

}
