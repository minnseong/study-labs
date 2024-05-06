package labs.minnseong.stocksystemconcurrencyissue.facade;

import java.util.concurrent.TimeUnit;
import labs.minnseong.stocksystemconcurrencyissue.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final StockService stockService;

    public void decrease(Long productId, Long quantity) {

        // TODO: pub-sub 구조와 코드 부분과 매칭해서 알아보기
        RLock lock = redissonClient.getLock(productId.toString());

        try {
            boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);

            if (!available) {
                log.info("lock 획득 실패");
                return;
            }

            stockService.decrease(productId, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
