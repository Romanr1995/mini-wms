package ru.nozdrachev.miniwms.repo;

import org.springframework.data.repository.CrudRepository;
import ru.nozdrachev.miniwms.entity.StockEntity;

public interface StockRepo extends CrudRepository<StockEntity, Long> {

//    @Query("Select s from StockEntity s where s.name = :name")
//    Optional<StockEntity> findByName(@Param("name") String name);

    StockEntity findByName(String name);
}
