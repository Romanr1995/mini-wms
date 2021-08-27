package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.dto.PairNumbers;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.util.Map;

@Service
public class OutcomeServiceImpl implements OutcomeService {


    private final StockRepo stockRepo;

    public OutcomeServiceImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public void doOutcome(Map<String, PairNumbers> out) {

        for (Map.Entry<String, PairNumbers> e : out.entrySet()) {

            String name = e.getKey();
            PairNumbers cnt = e.getValue();

            StockEntity stockEntity = stockRepo.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Товар с наименованием " + name + " не найден"));

            if (cnt.getFirst() == stockEntity.getStockCnt()) {
                if (cnt.getSecond().compareTo(stockEntity.getStockCnt()) < 0) {
                    stockEntity.subtractStockCnt(cnt.getSecond());

                    stockRepo.save(stockEntity);
                } else if (cnt.getSecond().compareTo(stockEntity.getStockCnt()) == 0) {
                    stockRepo.delete(stockEntity);

                } else {
                    throw new RuntimeException("На складе нет необходимого количества " + name);
                }
            } else {
                throw new RuntimeException("Неверно задано текущее значение");
            }
        }
    }
}
