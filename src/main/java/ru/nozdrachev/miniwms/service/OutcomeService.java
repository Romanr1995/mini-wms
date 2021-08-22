package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.Map;

public interface OutcomeService {

    void doOutcome(Map<String, BigDecimal> out);
}
