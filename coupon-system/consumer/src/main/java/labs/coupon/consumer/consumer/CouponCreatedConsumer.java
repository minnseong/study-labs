package labs.coupon.consumer.consumer;

import labs.coupon.consumer.domain.Coupon;
import labs.coupon.consumer.domain.FailedEvent;
import labs.coupon.consumer.repository.CouponRepository;
import labs.coupon.consumer.repository.FailedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;
    private final FailedEventRepository failedEventRepository;

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        try {
            couponRepository.save(Coupon.builder().userId(userId).build());
        } catch (Exception e) {
            log.error("failed to create coupon::" + userId);
            failedEventRepository.save(FailedEvent.builder().userId(userId).build());
        }
    }

}
