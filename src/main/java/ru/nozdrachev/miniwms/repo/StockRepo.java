package ru.nozdrachev.miniwms.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.nozdrachev.miniwms.entity.StockEntity;

import java.util.List;
import java.util.Optional;

public interface StockRepo extends CrudRepository<StockEntity, Long> {

//    @Query("Select s from StockEntity s where s.name = :name")
//    Optional<StockEntity> findByName(@Param("name") String name);


    Optional<StockEntity> findByName(String name);

    @Query("Select s from StockEntity s where s.targetCnt > s.stockCnt")
    List<StockEntity> getAllWithShortage();

}
