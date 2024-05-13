package labs.coupon.api.domain.user.repository;

import labs.coupon.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
