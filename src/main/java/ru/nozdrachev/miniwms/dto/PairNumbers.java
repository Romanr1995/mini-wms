package ru.nozdrachev.miniwms.dto;

import lombok.Getter;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;


@Getter
public class PairNumbers {

    private BigDecimal first;
    private BigDecimal second;

    @ConstructorProperties({"first","second"})
    public PairNumbers(BigDecimal first, BigDecimal second) {
        this.first = first;
        this.second = second;
    }
}
