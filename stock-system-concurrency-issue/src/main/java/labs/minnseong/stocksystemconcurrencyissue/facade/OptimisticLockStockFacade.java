package labs.minnseong.stocksystemconcurrencyissue.facade;

import labs.minnseong.stocksystemconcurrencyissue.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public void decrease(Long productId, Long quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.decrease(productId, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
