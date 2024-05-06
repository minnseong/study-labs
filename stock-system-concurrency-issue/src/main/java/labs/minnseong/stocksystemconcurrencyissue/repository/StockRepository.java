package labs.minnseong.stocksystemconcurrencyissue.repository;

import java.util.Optional;
import labs.minnseong.stocksystemconcurrencyissue.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductId(Long productId);

}
