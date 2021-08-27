package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;
import ru.nozdrachev.miniwms.service.StockBalances;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StockBalancesImpl implements StockBalances {

    private final StockRepo stockRepo;

    public StockBalancesImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public List<StockEntity> getStock() {
        List<StockEntity> listStock = new ArrayList<>();

        Iterator<StockEntity> stockEntityIterator = stockRepo.findAll().iterator();
        while (stockEntityIterator.hasNext()) {
            StockEntity stockEntity = stockRepo.findAll().iterator().next();

            listStock.add(stockEntity);
        }
        return listStock;
    }
}
