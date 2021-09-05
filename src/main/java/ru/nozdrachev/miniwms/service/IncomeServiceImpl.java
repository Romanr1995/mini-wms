package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class IncomeSericeImpl implements IncomeService {

    private final StockRepo stockRepo;

    public IncomeSericeImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public void doIncome(Map<String, BigDecimal> in) {

        for (Map.Entry<String, BigDecimal> e : in.entrySet()) {
            String name = e.getKey();
            BigDecimal cnt = e.getValue();

            if (cnt.equals(new BigDecimal(0)) || cnt.compareTo(new BigDecimal(0)) <= 0) {
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
}

