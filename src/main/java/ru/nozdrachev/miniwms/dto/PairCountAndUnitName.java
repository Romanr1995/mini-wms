package ru.nozdrachev.miniwms.dto;

import lombok.Getter;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Getter
public class PairCountAndUnitName {

    private BigDecimal count;
    private String unitName;

    @ConstructorProperties({"count", "unitName"})
    public PairCountAndUnitName(BigDecimal count, String unitName) {
        this.count = count;
        this.unitName = unitName;
    }
}
