package labs.minnseong.stocksystemconcurrencyissue.service;

import labs.minnseong.stocksystemconcurrencyissue.annotation.DistributedLock;
import labs.minnseong.stocksystemconcurrencyissue.domain.Stock;
import labs.minnseong.stocksystemconcurrencyissue.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @DistributedLock(key = "#productId")
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductId(productId).orElseThrow();
        stock.decrease(quantity);
    }
}
