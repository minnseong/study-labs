package labs.minnseong.stocksystemconcurrencyissue.service;

import labs.minnseong.stocksystemconcurrencyissue.domain.Stock;
import labs.minnseong.stocksystemconcurrencyissue.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithPessimisticLock(productId).orElseThrow();
        stock.decrease(quantity);
    }
}
