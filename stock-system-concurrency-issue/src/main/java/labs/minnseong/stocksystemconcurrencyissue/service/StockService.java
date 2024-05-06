package labs.minnseong.stocksystemconcurrencyissue.service;

import labs.minnseong.stocksystemconcurrencyissue.domain.Stock;
import labs.minnseong.stocksystemconcurrencyissue.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    // TODO: Named 락 사용할 때, 트랜잭션을 분리해야 하는 이유
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductId(productId).orElseThrow();
        stock.decrease(quantity);
    }
}
