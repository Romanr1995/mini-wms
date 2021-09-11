package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class UnitConversionServiceImpl implements UnitConversionService {

    private final UnitConversionRepo unitRepo;

    public UnitConversionServiceImpl(UnitConversionRepo unitRepo) {
        this.unitRepo = unitRepo;
    }

    @Override
    public BigDecimal calculateBaseCnt(String productName, BigDecimal alternativeCnt, UnitOfMeasurement altUnit) {

        BigDecimal coeff = unitRepo.findByProductNameAltUnit(productName, altUnit)
                .orElseThrow(() -> new RuntimeException("Не найдена альтернативная единица измерения " +
                        altUnit + " для товара " + productName)).getCoeff();

        return alternativeCnt.multiply(coeff);
    }

    @Override
    public BigDecimal calculateAltCnt(String productName, BigDecimal baseCnt, UnitOfMeasurement altUnit) {

        BigDecimal coeff = unitRepo.findByProductNameAltUnit(productName, altUnit)
                .orElseThrow(() -> new RuntimeException("Неверно задано название " + productName +
                        " или альтернативная единица измерения " + altUnit)).getCoeff();


        return baseCnt.divide(coeff, 5, RoundingMode.HALF_UP);
    }
}
