package ru.nozdrachev.miniwms.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nozdrachev.miniwms.entity.StockEntity;

import java.util.List;
import java.util.Optional;

public interface StockRepo extends CrudRepository<StockEntity, Long> {

    @Query("select s from StockEntity s " +
            "join ProductEntity p on (s.product = p) " +
            "where p.name = :productName")
    Optional<StockEntity> findByProductName(@Param("productName") String productName);

    @Query("Select s from StockEntity s where s.targetCnt > s.stockCnt")
    List<StockEntity> getAllWithShortage();

}
