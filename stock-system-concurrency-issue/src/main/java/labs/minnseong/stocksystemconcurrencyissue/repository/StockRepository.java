package labs.minnseong.stocksystemconcurrencyissue.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import labs.minnseong.stocksystemconcurrencyissue.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select s from Stock s where s.productId = :productId")
    Optional<Stock> findByProductIdWithPessimisticLock(@Param("productId") Long productId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = "select s from Stock s where s.productId = :productId")
    Optional<Stock> findByProductIdWithOptimisticLock(@Param("productId") Long productId);
}
