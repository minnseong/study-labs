package labs.coupon.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import labs.coupon.api.domain.coupon.Coupon;
import labs.coupon.api.domain.coupon.repository.CouponBoxRepository;
import labs.coupon.api.domain.user.User;
import labs.coupon.api.domain.user.repository.UserRepository;
import labs.coupon.api.domain.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponBoxRepository couponBoxRepository;

    @Test
    public void 한번_응모() {

        // given
        User user = userRepository.save(User.builder().name("User_A").build());
        Coupon coupon = couponRepository.save(Coupon.builder().title("Coupon_A").maxCount(10).build());

        // when
        applyService.apply(user.getId(), coupon.getId());

        // then
        long count = couponBoxRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void 여러명_응모() throws InterruptedException {

        // given
        Coupon coupon = couponRepository.save(Coupon.builder().title("Coupon_A").maxCount(100).build());

        // when
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    applyService.apply(userId, coupon.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);

        // then
        long count = couponBoxRepository.count();

        assertThat(count).isEqualTo(coupon.getMaxCount());
    }

    @Test
    public void 한명당_한개의쿠폰만_응모() throws InterruptedException {

        // given
        Coupon coupon = couponRepository.save(Coupon.builder().title("Coupon_A").maxCount(100).build());

        // when
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    applyService.apply(1L, coupon.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);

        // then
        long count = couponBoxRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void 여러명_중복불가_응모() throws InterruptedException {

        // given
        Coupon coupon = couponRepository.save(Coupon.builder().title("Coupon_A").maxCount(100).build());

        // when
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i % 200;
            executorService.submit(() -> {
                try {
                    applyService.apply(userId, coupon.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);

        // then
        long count = couponBoxRepository.count();

        assertThat(count).isEqualTo(coupon.getMaxCount());
    }
}