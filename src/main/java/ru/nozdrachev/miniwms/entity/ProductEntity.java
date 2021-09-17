package ru.nozdrachev.miniwms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

@Setter
@Getter
@Entity
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String name;

    /**
     * Вес одной базовой единицы в киллограмах.
     */
    @PositiveOrZero
    private BigDecimal weight;

    /**
     * Стоимость одной базовой единицы в рублях.
     */
    @PositiveOrZero
    private BigDecimal price;

    /**
     * Тип базовой единицы измерения товара.
     */
    @Column(nullable = false)
    private UnitOfMeasurement baseUnit;
}
