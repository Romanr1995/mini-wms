package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;

import java.math.BigDecimal;

public interface UnitConversionService {

    /**
     * Метод calculateBaseCnt используется для перевода количества товара из альтернативной единицы измерения
     * в количество товара в базовой единице измерения.
     *
     * @param productName    -> наименование товара.
     * @param alternativeCnt -> количество товара в альтернативных единицах.
     * @param altUnit        -> текущая альтернативная единица хранения товара.
     * @return -> количество товара в базовой единице.
     */
    BigDecimal calculateBaseCnt(String productName, BigDecimal alternativeCnt, UnitOfMeasurement altUnit);

}
