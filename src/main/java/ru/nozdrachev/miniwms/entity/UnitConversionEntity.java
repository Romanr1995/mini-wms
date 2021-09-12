package ru.nozdrachev.miniwms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "UNIT_CONVERSION_ENTITY",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product", "altUnit"}))
@Getter
@Setter
@NoArgsConstructor
public class UnitConversionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private ProductEntity product;

    private UnitOfMeasurement altUnit;

    /**
     * Коэффициент coeff используется для перевода из базовой единицы измерения в альтернативную и наоборот.
     * base -> alternative: alt_cnt = base_cnt/coeff
     * alternative -> base: base_cnt = alt_cnt*coeff
     */
    private BigDecimal coeff;

}
