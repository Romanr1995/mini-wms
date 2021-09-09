package ru.nozdrachev.miniwms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_ENTITY")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private UnitOfMeasurement base;
}
