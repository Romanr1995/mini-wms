package ru.nozdrachev.miniwms.repo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nozdrachev.miniwms.entity.StockEntity;

public interface StockRepo extends CrudRepository<StockEntity, Long> {

//    @Query("Select s from StockEntity s where s.name = :name")
//    Optional<StockEntity> findByName(@Param("name") String name);

    Optional<StockEntity> findByName(String name);
}
