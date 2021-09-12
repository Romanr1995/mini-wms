package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.ProductRecordDTO;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.List;
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
    public void doOutcomeV2(List<ProductRecordDTO> out) {
        for (ProductRecordDTO req : out) {
            String name = req.getName();

            BigDecimal count = req.getCount();

            UnitOfMeasurement unitOfMeasurement = req.getUnitName();
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
