package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.PairCapacityAndCount;

import java.math.BigDecimal;
import java.util.Map;


public interface IncomeService {

    @Deprecated
    void doIncome(Map<String, BigDecimal> in);


    void doIncome(Map<String, PairCapacityAndCount> in);
}
