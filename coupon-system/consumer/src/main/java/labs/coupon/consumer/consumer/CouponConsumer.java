package labs.coupon.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.coupon.consumer.consumer.message.CouponCreateMessage;
import labs.coupon.consumer.domain.CouponBox;
import labs.coupon.consumer.repository.CouponBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CouponConsumer {

    private final CouponBoxRepository couponBoxRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void receive(String message) {
        log.info("Received Coupon Create message : {}", message);

        try {
            CouponCreateMessage couponCreateMessage = objectMapper.readValue(message, CouponCreateMessage.class);
            couponBoxRepository.save(
                    CouponBox.builder()
                            .userId(couponCreateMessage.getUserId())
                            .couponId(couponCreateMessage.getCouponId())
                            .build());
        } catch (Exception e) {
            log.error("failed to create coupon::" + message);
        }
    }

}
