package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.PairCountAndUnitName;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final StockRepo stockRepo;
    private final UnitConversionService unitConversionService;

    public IncomeServiceImpl(StockRepo stockRepo, UnitConversionService unitConversionService) {
        this.stockRepo = stockRepo;
        this.unitConversionService = unitConversionService;
    }

    @Transactional
    @Override
    @Deprecated
    public void doIncome(Map<String, BigDecimal> in) {
        for (Map.Entry<String, BigDecimal> e : in.entrySet()) {
            String name = e.getKey();
            BigDecimal cnt = e.getValue();

            if (cnt.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Недопустимое значение.Значение должно быть больше 0");
            }

            Optional<StockEntity> entityOpt = stockRepo.findByName(name);
            if (entityOpt.isPresent()) {

                StockEntity stockEntity = entityOpt.get();
                stockEntity.addStockCnt(cnt);

                stockRepo.save(stockEntity);
            } else {
                stockRepo.save(
                        new StockEntity()
                                .setName(name)
                                .setStockCnt(cnt)
                );
            }
        }
    }

    @Transactional
    @Override
    public void doIncomeV2(Map<String, PairCountAndUnitName> in) {
        for (Map.Entry<String, PairCountAndUnitName> e : in.entrySet()) {
            String name = e.getKey();
            PairCountAndUnitName cntAndUnit = e.getValue();

            String unitName = cntAndUnit.getUnitName();
            BigDecimal count = cntAndUnit.getCount();

            UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.valueOf(unitName);
            BigDecimal baseCount = unitConversionService.calculateBaseCnt(name, count, unitOfMeasurement);

            if (count.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Недопустимое значение.Значение должно быть больше 0");
            }

            Optional<StockEntity> entityOpt = stockRepo.findByName(unitName);

            if (entityOpt.isPresent()) {

                StockEntity stockEntity = entityOpt.get();
                stockEntity.addStockCnt(baseCount);

                stockRepo.save(stockEntity);
            } else {
                stockRepo.save(
                        new StockEntity()
                                .setName(name)
                                .setStockCnt(baseCount)
                );
            }
        }
    }
}

