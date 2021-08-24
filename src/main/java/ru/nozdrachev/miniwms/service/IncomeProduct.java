package ru.nozdrachev.miniwms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class IncomeProduct implements IncomeService{

    @PersistenceContext
    EntityManager em;

    @Autowired
    StockRepo stockRepo;


    @Transactional
    @Override
    public void doIncome(Map<String, BigDecimal> in){

        for(Map.Entry e: in.entrySet()) {
            String key =(String) e.getKey();
            BigDecimal value = (BigDecimal) e.getValue();

            StockEntity stockEntity = stockRepo.findByName(key).orElseThrow();

            stockEntity.setStockCnt(stockEntity.getStockCnt().add(value));
        }
    }
}
