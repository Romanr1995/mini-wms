package ru.nozdrachev.miniwms.dto;

import ru.nozdrachev.miniwms.entity.StockEntity;

import java.util.List;

public interface DtoBalances {

    List<StockDTO> getStockDTO();
}
