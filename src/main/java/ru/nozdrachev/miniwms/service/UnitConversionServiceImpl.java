package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.ProductRepo;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UnitConversionServiceImpl implements UnitConversionService {

    private final ProductRepo productRepo;
    private final UnitConversionRepo unitRepo;

    public UnitConversionServiceImpl(ProductRepo productRepo, UnitConversionRepo unitRepo) {
        this.productRepo = productRepo;
        this.unitRepo = unitRepo;
    }

    @Override
    public BigDecimal calculateBaseCnt(String productName, BigDecimal alternativeCnt, UnitOfMeasurement altUnit) {

        ProductEntity productEntity = productRepo.findByName(productName)
                .orElseThrow(() -> new RuntimeException("Неверное название товара: " + productName));

        UnitOfMeasurement productEntityBase = productEntity.getBase();

        BigDecimal baseCnt = new BigDecimal(0);

        List<UnitConversionEntity> fromProductName = unitRepo.getAllUnitConversionFromProductName(productName);

        for (UnitConversionEntity un : fromProductName) {
            if (un.getAlternative() == altUnit) {
                BigDecimal coeff = un.getCoeff();
                baseCnt = alternativeCnt.divide(coeff);
            }
        }
        return baseCnt;
    }

    @Override
    public BigDecimal calculateAltCnt(String productName, BigDecimal baseCnt, UnitOfMeasurement altUnit) {
        ProductEntity productEntity = productRepo.findByName(productName)
                .orElseThrow(() -> new RuntimeException("Неверное название товара: " + productName));

        UnitOfMeasurement productEntityBase = productEntity.getBase();

        BigDecimal alternativeCnt = new BigDecimal(0);

        List<UnitConversionEntity> fromProductName = unitRepo.getAllUnitConversionFromProductName(productName);

        for (UnitConversionEntity un : fromProductName) {
            if (un.getAlternative() == altUnit) {
                BigDecimal coeff = un.getCoeff();
                alternativeCnt = baseCnt.add(coeff);
            }
        }
        return baseCnt;
    }
}
