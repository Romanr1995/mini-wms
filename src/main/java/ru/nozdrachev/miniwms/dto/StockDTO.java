package ru.nozdrachev.miniwms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class StockDTO {

    private final String name;

    private final BigDecimal cnt;

    private final BigDecimal target;

}
