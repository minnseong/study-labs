package labs.minnseong.stocksystemconcurrencyissue.facade;

import labs.minnseong.stocksystemconcurrencyissue.repository.LockRepository;
import labs.minnseong.stocksystemconcurrencyissue.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockService stockService;

    public void decrease(Long productId, Long quantity) {
        try {
            lockRepository.getLock(productId.toString());
            stockService.decrease(productId, quantity);
        } finally {
            lockRepository.releaseLock(productId.toString());
        }
    }
}
