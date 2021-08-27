package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.dto.PairNumbers;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

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
    public void doIncome(Map<String, PairNumbers> in) {

        for (Map.Entry<String, PairNumbers> e : in.entrySet()) {
            String name = e.getKey();
            PairNumbers cnt = e.getValue();

            Optional<StockEntity> entityOpt = stockRepo.findByName(name);
            if (cnt.getFirst() == entityOpt.get().getTargetCnt()) {
                if (entityOpt.isPresent()) {
                    StockEntity stockEntity = entityOpt.get();
                    stockEntity.addStockCnt(cnt.getSecond());

                    stockRepo.save(stockEntity);
                } else {
                    stockRepo.save(
                            new StockEntity()
                                    .setName(name)
                                    .setStockCnt(cnt.getSecond())
                    );
                }
            } else {
                throw new RuntimeException("Неверно задано текущее значение");
            }
        }
    }
}
