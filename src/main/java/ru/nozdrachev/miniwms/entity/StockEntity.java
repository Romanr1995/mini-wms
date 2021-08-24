package ru.nozdrachev.miniwms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STOCK_ENTITY")
@Getter
@Setter
@NoArgsConstructor
public class StockEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal stockCnt;

    private BigDecimal targetCnt;

}
