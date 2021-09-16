package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import java.math.BigDecimal;

@Service
public class UnitConversionServiceImpl implements UnitConversionService {

    private final UnitConversionRepo unitRepo;

    public UnitConversionServiceImpl(UnitConversionRepo unitRepo) {
        this.unitRepo = unitRepo;
    }

    @Override
    public BigDecimal calculateBaseCnt(String productName, BigDecimal alternativeCnt, UnitOfMeasurement altUnit) {

        if (alternativeCnt.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Недопустимое значение.Значение должно быть больше 0");
        }

        BigDecimal coeff = unitRepo.findByProductNameAltUnit(productName, altUnit)
                .orElseThrow(() -> new RuntimeException("Не найдена альтернативная единица измерения " +
                        altUnit + " для товара " + productName)).getCoeff();

        return alternativeCnt.multiply(coeff);
    }
}
