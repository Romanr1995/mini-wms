package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import java.math.BigDecimal;

public interface UnitConversionService {

    /**
     *Метод calculateBaseCnt используется для перевода количества товара из альтернативной единицы измерения
     * в количество товара в базовой единице измерения
     * @param productName -> наименование товара
     * @param alternativeCnt -> количество товара в альтернативных единицах
     * @param altUnit -> текущая альтернативная единица хранения товара
     * @return -> количество товара в базовой единице
     */
    BigDecimal calculateBaseCnt(String productName, BigDecimal alternativeCnt, UnitOfMeasurement altUnit);

    /**
     *Метод calculateAltCnt используется для перевода количества товара в базовой единице измерения
     *в количество товара в альтернативной единице измерения
     * @param productName -> наименование товара
     * @param baseCnt -> количество товара в базовой единице
     * @param altUnit -> единица хранения товара в которую необходимо перевести
     * @return -> количество товара в альтернативной единице
     */
    BigDecimal calculateAltCnt(String productName, BigDecimal baseCnt, UnitOfMeasurement altUnit);
}
