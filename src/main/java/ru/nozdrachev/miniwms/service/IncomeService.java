package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.ProductRecordDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface IncomeService {

    void doIncomeV2(List<ProductRecordDTO> in);
}
