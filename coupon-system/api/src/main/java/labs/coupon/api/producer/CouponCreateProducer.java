package labs.coupon.api.producer;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreateProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void create(Long userId, Long couponId) {

        Map<String, Long> message = new HashMap<>();
        message.put("userId", userId);
        message.put("couponId", couponId);

        kafkaTemplate.send("coupon_create", message);
    }

}
