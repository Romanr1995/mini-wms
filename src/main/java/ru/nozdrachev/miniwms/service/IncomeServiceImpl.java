package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.ProductRecordDTO;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.ProductRepo;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final StockRepo stockRepo;
    private final UnitConversionService unitConversionService;
    private final ProductRepo productRepo;

    public IncomeServiceImpl(
        StockRepo stockRepo,
        UnitConversionService unitConversionService,
        ProductRepo productRepo
    ) {
        this.stockRepo = stockRepo;
        this.unitConversionService = unitConversionService;
        this.productRepo = productRepo;
    }

    @Transactional
    @Override
    public void doIncomeV2(List<ProductRecordDTO> in) {
        for (ProductRecordDTO req : in) {
            String name = req.getName();

            BigDecimal count = req.getCount();

            UnitOfMeasurement unitOfMeasurement = req.getUnitName();
            BigDecimal baseCount = unitConversionService.calculateBaseCnt(name, count, unitOfMeasurement);

            if (count.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Недопустимое значение.Значение должно быть больше 0");
            }

            Optional<StockEntity> entityOpt = stockRepo.findByProductName(name);

            if (entityOpt.isPresent()) {

                StockEntity stockEntity = entityOpt.get();
                stockEntity.addStockCnt(baseCount);

                stockRepo.save(stockEntity);
            } else {

                ProductEntity product = productRepo.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Продукт с названием " + name + " не найден, " +
                                                              "пожалуйста, добавьте его вручную"));

                stockRepo.save(
                        new StockEntity()
                                .setProduct(product)
                                .setStockCnt(baseCount)
                );
            }
        }
    }
}

