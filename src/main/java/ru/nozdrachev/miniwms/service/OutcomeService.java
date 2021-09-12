package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.ProductRecordDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OutcomeService {

    @Deprecated
    void doOutcome(Map<String, BigDecimal> out);

    void doOutcomeV2(List<ProductRecordDTO> out);
}
