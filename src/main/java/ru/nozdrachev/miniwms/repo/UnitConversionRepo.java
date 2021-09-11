package ru.nozdrachev.miniwms.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;

import java.util.Optional;

public interface UnitConversionRepo extends CrudRepository<UnitConversionEntity, Long> {


    @Query("Select s from UnitConversionEntity s " +
            "where s.productName = :productName and s.altUnit = :unit")
    Optional<UnitConversionEntity> findByProductNameAltUnit(@Param("productName") String productName,
                                                            @Param("unit") UnitOfMeasurement unit);
}
