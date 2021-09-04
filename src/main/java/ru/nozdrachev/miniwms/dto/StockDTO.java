package ru.nozdrachev.miniwms.dto;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class StockDTO {

    private final String name;

    private final BigDecimal cnt;

    private final BigDecimal target;

    @ConstructorProperties({"name","cnt","target"})
    public StockDTO(String name, BigDecimal cnt, BigDecimal target) {
        this.name = name;
        this.cnt = cnt;
        this.target = target;
    }
}
