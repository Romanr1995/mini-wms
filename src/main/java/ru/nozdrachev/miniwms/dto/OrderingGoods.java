package ru.nozdrachev.miniwms.dto;

import java.math.BigDecimal;
import java.util.Map;

public interface OrderingGoods {

    Map<String, BigDecimal> getOrder();
}
