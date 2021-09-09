package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.PairCountAndUnitName;

import java.math.BigDecimal;
import java.util.Map;

public interface OutcomeService {

    @Deprecated
    void doOutcome(Map<String, BigDecimal> out);

    void doOutcomeV2(Map<String, PairCountAndUnitName> out);
}
