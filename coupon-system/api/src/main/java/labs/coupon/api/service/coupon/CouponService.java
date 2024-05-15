package labs.coupon.api.service.coupon;

import labs.coupon.api.domain.coupon.Coupon;
import labs.coupon.api.domain.coupon.repository.CouponRepository;
import labs.coupon.api.exception.coupon.CouponDuplicateApplyException;
import labs.coupon.api.exception.coupon.CouponSoldOutException;
import labs.coupon.api.producer.CouponCreateProducer;
import labs.coupon.api.producer.request.CouponCreateSendRequest;
import labs.coupon.api.service.coupon.vo.CouponVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final CouponRedisOperation couponRedisOperation;

    public void apply(Long userId, Long couponId) {

        CouponVO couponVO = CouponVO.of(couponId, userId);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();

        checkCouponDuplicateApply(couponVO);
        checkCouponSoldOut(couponVO, coupon.getMaxCount());

        couponCreateProducer.create(CouponCreateSendRequest.of(userId, couponId));
    }

    private void checkCouponSoldOut(CouponVO couponVO, int maxCount) {
        Long count = couponRedisOperation.incr(couponVO);

        if (count > maxCount) {
            throw new CouponSoldOutException();
        }
    }

    private void checkCouponDuplicateApply(CouponVO couponVO) {
        Long apply = couponRedisOperation.addSet(couponVO);

        if (apply != 1) {
            throw new CouponDuplicateApplyException();
        }
    }

}
