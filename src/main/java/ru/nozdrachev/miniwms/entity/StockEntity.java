package ru.nozdrachev.miniwms.entity;

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
public class StockEntity {

    @Id
    @GeneratedValue
    private Long id;

    private ProductEntity product;

    @Column(nullable = false)
    private BigDecimal stockCnt;

    private BigDecimal targetCnt;

    public void addStockCnt(BigDecimal stockCnt) {
        this.stockCnt = this.stockCnt.add(stockCnt);
    }

    public void subtractStockCnt(BigDecimal stockCnt) {
        this.stockCnt = this.stockCnt.subtract(stockCnt);
    }
}
