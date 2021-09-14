package ru.nozdrachev.miniwms.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    /**
     * Вес одной базовой единицы в киллограмах.
     */
    private BigDecimal weight;

    /**
     * Стоимость одной базовой единицы в рублях.
     */
    private BigDecimal price;

    /**
     * Тип базовой единицы измерения товара.
     */
    @Column(nullable = false)
    private UnitOfMeasurement baseUnit;
}
