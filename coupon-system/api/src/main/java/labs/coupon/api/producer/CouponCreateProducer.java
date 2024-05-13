package labs.coupon.api.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import labs.coupon.api.producer.request.CouponCreateSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreateProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void create(CouponCreateSendRequest request) {

        try {
            final String payload = objectMapper.writeValueAsString(request);
            kafkaTemplate.send("coupon_create", payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
