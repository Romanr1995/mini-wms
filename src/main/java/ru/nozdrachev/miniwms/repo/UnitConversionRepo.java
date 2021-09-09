package ru.nozdrachev.miniwms.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;

import java.util.List;
import java.util.Optional;

public interface UnitConversionRepo extends CrudRepository<UnitConversionEntity, Long> {

    Optional<StockEntity> findByName(String name);

    @Query("Select s from StockEntity s where s.productName = :productName")
    List<UnitConversionEntity> getAllUnitConversionFromProductName(@Param("productName") String productName);
}
