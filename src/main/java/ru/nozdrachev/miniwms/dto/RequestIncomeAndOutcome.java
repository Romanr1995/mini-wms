package ru.nozdrachev.miniwms.dto;

import lombok.Getter;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

@Getter
public class RequestIncomeAndOutcome {

    private String name;
    private BigDecimal count;
    private UnitOfMeasurement unitName;

    @ConstructorProperties({"name", "count", "unitName"})
    public RequestIncomeAndOutcome(String name, BigDecimal count, UnitOfMeasurement unitName) {
        this.name = name;
        this.count = count;
        this.unitName = unitName;
    }
}
