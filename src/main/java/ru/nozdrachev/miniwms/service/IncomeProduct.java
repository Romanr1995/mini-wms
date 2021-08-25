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

    @Autowired
    StockRepo stockRepo;

    public IncomeProduct(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public void doIncome(Map<String, BigDecimal> in){

        for(Map.Entry<String,BigDecimal> e: in.entrySet()) {
            String key = e.getKey();
            BigDecimal value = e.getValue();

                StockEntity stockEntity = stockRepo.findByName(key);
                if (stockEntity == null) {
                    throw new RuntimeException("Значение не найдено");
                }
                stockRepo.save(stockEntity.setStockCnt(stockEntity.getStockCnt().add(value)));

        }
    }
}
