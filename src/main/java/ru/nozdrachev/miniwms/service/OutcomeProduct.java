package ru.nozdrachev.miniwms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Map;

@Component
public class OutcomeProduct implements OutcomeService {

    @Autowired
    StockRepo stockRepo;

    public OutcomeProduct(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public void doOutcome(Map<String, BigDecimal> out) {

        for (Map.Entry<String,BigDecimal> e : out.entrySet()) {

            String key = e.getKey();
            BigDecimal value = e.getValue();

            StockEntity stockEntity = stockRepo.findByName(key);


           if (value.compareTo(stockEntity.getStockCnt()) <= 0) {
               stockRepo.save(stockEntity.setStockCnt(stockEntity.getStockCnt().subtract(value)));
           } else {
               stockRepo.delete(stockEntity);
           }

        }
    }
}
