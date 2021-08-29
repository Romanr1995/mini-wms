package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.util.ArrayList;
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

        Iterable<StockEntity> all = stockRepo.findAll();
        for (StockEntity s : all) {
            listStock.add(s);
        }

        return listStock;
    }
}
