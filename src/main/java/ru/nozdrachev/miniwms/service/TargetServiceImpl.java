package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class TargetServiceImpl implements TargetService {

    private final StockRepo stockRepo;

    public TargetServiceImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public void doTarget(Map<String, BigDecimal> inTarget) {

        for (Map.Entry<String, BigDecimal> e : inTarget.entrySet()) {
            String name = e.getKey();
            BigDecimal targetCnt = e.getValue();

            Optional<StockEntity> entityOpt = stockRepo.findByName(name);
            if (entityOpt.isEmpty()) {
                stockRepo.save(
                        new StockEntity()
                                .setName(name)
                                .setTargetCnt(targetCnt)
                                .setStockCnt(BigDecimal.valueOf(0))
                );
            } else {
                StockEntity stock = entityOpt.get();
                stock.setTargetCnt(targetCnt);

                stockRepo.save(stock);
            }
        }
    }
}
