package ru.nozdrachev.miniwms.entity;

import java.io.Serializable;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "STOCK_ENTITY")
@Getter
@Setter
@NoArgsConstructor
public class StockEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private BigDecimal stockCnt;

    @Positive
    private BigDecimal targetCnt;

    public void addStockCnt(BigDecimal stockCnt) {
        this.stockCnt = this.stockCnt.add(stockCnt);
    }

    public void subtractStockCnt(BigDecimal stockCnt) {
        this.stockCnt = this.stockCnt.subtract(stockCnt);
    }
}
