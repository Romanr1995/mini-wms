package ru.nozdrachev.miniwms.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "UNIT_CONVERSION_ENTITY",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "altUnit"}))
@Getter
@Setter
@NoArgsConstructor
public class UnitConversionEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "altUnit")
    private UnitOfMeasurement altUnit;

    /**
     * Коэффициент coeff используется для перевода из базовой единицы измерения в альтернативную и наоборот.
     * base -> alternative: alt_cnt = base_cnt/coeff.
     * alternative -> base: base_cnt = alt_cnt*coeff.
     */
    private BigDecimal coeff;
}
