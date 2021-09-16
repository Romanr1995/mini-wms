package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StockPrice {

    Map<String, BigDecimal> getPrice(List<String> product);
}
