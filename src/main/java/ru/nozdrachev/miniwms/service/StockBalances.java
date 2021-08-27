package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.entity.StockEntity;

import java.util.List;

public interface StockBalances {

    List<StockEntity> getStock();
}
