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

    @PersistenceContext
    EntityManager em;

    @Autowired
    StockRepo stockRepo;

    @Transactional
    @Override
    public void doOutcome(Map<String, BigDecimal> out) {

        for (Map.Entry e : out.entrySet()) {

            String key = (String) e.getKey();
            BigDecimal value = (BigDecimal) e.getValue();

            StockEntity stockEntity = stockRepo.findByName(key).orElseThrow();

           if (value.compareTo(stockEntity.getStockCnt()) <= 0) {
               stockEntity.setStockCnt(stockEntity.getStockCnt().subtract(value));
           } else {
               stockRepo.delete(stockEntity);
           }
        }
    }
}
