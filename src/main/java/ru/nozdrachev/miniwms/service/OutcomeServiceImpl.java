package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class OutcomeServiceImpl implements OutcomeService {


    private final StockRepo stockRepo;

    public OutcomeServiceImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public void doOutcome(Map<String, BigDecimal> out) {

        for (Map.Entry<String, BigDecimal> e : out.entrySet()) {

            String name = e.getKey();
            BigDecimal cnt = e.getValue();

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
}
