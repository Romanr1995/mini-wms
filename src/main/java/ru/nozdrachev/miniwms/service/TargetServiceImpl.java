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
            BigDecimal expectedTargetCnt = targetCnt.getFirst();
            BigDecimal newTargetCnt = targetCnt.getSecond();

            Optional<StockEntity> entityOpt = stockRepo.findByName(name);

            if (entityOpt.isEmpty()) {

                if (expectedTargetCnt.equals(BigDecimal.ZERO)) {
                    stockRepo.save(
                            new StockEntity()
                                    .setName(name)
                                    .setTargetCnt(newTargetCnt)
                                    .setStockCnt(BigDecimal.valueOf(0))
                    );
                } else {
                    throw new RuntimeException("Значение целевого количества для " + name +
                            " ожидалось 0,но получено " + expectedTargetCnt);
                }
            } else {
                StockEntity stock = entityOpt.get();
                BigDecimal stockTargetCnt = stock.getTargetCnt();

                if (expectedTargetCnt.equals(stockTargetCnt) || stockTargetCnt.equals(null)) {

                    stock.setTargetCnt(newTargetCnt);

                    stockRepo.save(stock);
                } else {
                    throw new RuntimeException("Значение целевого количества для " + name +
                            " ожидалось " + stock.getTargetCnt() + ",а получено " + expectedTargetCnt);
                }
            }
        }
    }
}
