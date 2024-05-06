package labs.minnseong.stocksystemconcurrencyissue.facade;

import labs.minnseong.stocksystemconcurrencyissue.repository.RedisLockRepository;
import labs.minnseong.stocksystemconcurrencyissue.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {
    // TODO: Facade 클래스의 역할? Facade Pattern 알아보기

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public void decrease(Long productId, Long quantity) throws InterruptedException {

        while (!redisLockRepository.lock(productId)) {
            Thread.sleep(100);
        }

        try {
            stockService.decrease(productId, quantity);
        } finally {
            redisLockRepository.unlock(productId);
        }
    }

}
