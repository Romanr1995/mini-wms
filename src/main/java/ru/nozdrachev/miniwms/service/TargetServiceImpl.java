package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.dto.PairNumbers;
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
    public void doTarget(Map<String, PairNumbers> inTarget) {

        for (Map.Entry<String, PairNumbers> e : inTarget.entrySet()) {
            String name = e.getKey();
            PairNumbers targetCnt = e.getValue();

            Optional<StockEntity> entityOpt = stockRepo.findByName(name);
            if (targetCnt.getFirst() == entityOpt.get().getTargetCnt()) {
                if (entityOpt.isEmpty()) {
                    stockRepo.save(
                            new StockEntity()
                                    .setName(name)
                                    .setTargetCnt(targetCnt.getSecond())
                                    .setStockCnt(BigDecimal.valueOf(0))
                    );
                } else {
                    StockEntity stock = entityOpt.get();
                    stock.setTargetCnt(targetCnt.getSecond());

                    stockRepo.save(stock);
                }
            } else {
                throw new RuntimeException("Неверно задано текущее значение");
            }
        }
    }
}
