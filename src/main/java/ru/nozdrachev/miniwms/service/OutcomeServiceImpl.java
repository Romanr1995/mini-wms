package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.PairCountAndUnitName;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class OutcomeServiceImpl implements OutcomeService {


    private final StockRepo stockRepo;
    private final UnitConversionService unitConversionService;

    public OutcomeServiceImpl(StockRepo stockRepo, UnitConversionService unitConversionService) {
        this.stockRepo = stockRepo;
        this.unitConversionService = unitConversionService;
    }


    @Transactional
    @Override
    @Deprecated
    public void doOutcome(Map<String, BigDecimal> out) {

        for (Map.Entry<String, BigDecimal> e : out.entrySet()) {

            String name = e.getKey();
            BigDecimal cnt = e.getValue();

            if (cnt.equals(new BigDecimal(0)) || cnt.compareTo(new BigDecimal(0)) <= 0) {
                throw new RuntimeException("Недопустимое значение.Значение должно быть больше 0");
            }

            StockEntity stockEntity = stockRepo.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Товар с наименованием " + name + " не найден"));


            if (cnt.compareTo(stockEntity.getStockCnt()) < 0) {
                stockEntity.subtractStockCnt(cnt);

                stockRepo.save(stockEntity);
            } else if (cnt.compareTo(stockEntity.getStockCnt()) == 0) {
                stockRepo.delete(stockEntity);
            } else {
                throw new RuntimeException("На складе нет необходимого количества " + name);
            }
        }
    }

    @Transactional
    @Override
    public void doOutcomeV2(Map<String, PairCountAndUnitName> out) {
        for (Map.Entry<String, PairCountAndUnitName> e : out.entrySet()) {
            String name = e.getKey();
            PairCountAndUnitName cntAndUnit = e.getValue();

            String unitName = cntAndUnit.getUnitName();
            BigDecimal count = cntAndUnit.getCount();

            UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.valueOf(unitName);
            BigDecimal baseCnt = unitConversionService.calculateBaseCnt(name, count, unitOfMeasurement);

            if (baseCnt.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Недопустимое значение.Значение должно быть больше 0");
            }

            StockEntity stockEntity = stockRepo.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Товар с наименованием " + name + " не найден"));

            if (baseCnt.compareTo(stockEntity.getStockCnt()) < 0) {
                stockEntity.subtractStockCnt(baseCnt);

                stockRepo.save(stockEntity);
            } else if (baseCnt.compareTo(stockEntity.getStockCnt()) == 0) {
                stockRepo.delete(stockEntity);
            } else {
                throw new RuntimeException("На складе нет необходимого количества " + name);
            }
        }
    }
}
