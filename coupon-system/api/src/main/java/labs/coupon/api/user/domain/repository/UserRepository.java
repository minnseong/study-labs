package labs.coupon.api.user.domain.repository;

import labs.coupon.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
