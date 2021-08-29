package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.Map;


public interface IncomeService {

    void doIncome(Map<String, BigDecimal> in);
}
