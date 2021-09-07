package ru.nozdrachev.miniwms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.nozdrachev.miniwms.service.UnitOfMeasurement;

import javax.persistence.Entity;
import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
public class PairCapacityAndCount {

    private BigDecimal cnt;
    private UnitOfMeasurement capacity;

    @ConstructorProperties({"cnt", "capacity"})
    public PairCapacityAndCount(BigDecimal cnt, UnitOfMeasurement capacity) {
        this.cnt = cnt;
        this.capacity = capacity;
    }
}
