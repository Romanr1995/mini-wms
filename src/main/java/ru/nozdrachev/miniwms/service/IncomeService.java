package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.PairCountAndUnitName;

import java.math.BigDecimal;
import java.util.Map;


public interface IncomeService {

    @Deprecated
    void doIncome(Map<String, BigDecimal> in);

    void doIncomeV2(Map<String, PairCountAndUnitName> in);
}
