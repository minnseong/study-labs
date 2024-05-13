package labs.coupon.consumer.repository;

import labs.coupon.consumer.domain.CouponBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponBoxRepository extends JpaRepository<CouponBox, Long> {

}
