package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import java.math.BigDecimal;

public interface UnitConversionService {

    /**
     *
     * @param productName -> наименование товара
     * @param alternativeCnt -> количество товара в альтернативных единицах
     * @param altUnit -> текущая альтернативная единица хранения товара
     * @return -> количество товара в базовой единице
     */
    BigDecimal calculateBaseCnt(String productName, BigDecimal alternativeCnt, UnitOfMeasurement altUnit);

    /**
     *
     * @param productName -> наименование товара
     * @param baseCnt -> количество товара в базовой единице
     * @param altUnit -> единица хранения товара в которую необходимо перевести
     * @return -> количество товара в альтернативной единице
     */
    BigDecimal calculateAltCnt(String productName, BigDecimal baseCnt, UnitOfMeasurement altUnit);
}
