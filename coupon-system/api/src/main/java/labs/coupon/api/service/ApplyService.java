package labs.coupon.api.service;

import labs.coupon.api.domain.Coupon;
import labs.coupon.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final CouponRepository couponRepository;

    public void apply(Long userId) {
        long count = couponRepository.count();

        if (count > 100) {
            return;
        }

        couponRepository.save(Coupon.builder().userId(userId).build());
    }

}
