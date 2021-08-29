package ru.nozdrachev.miniwms.dto;

import org.springframework.stereotype.Service;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.service.StockBalances;

import java.util.ArrayList;
import java.util.List;
//toDo исправить в следующей версии
@Deprecated
@Service
public class DtoBalancesImpl implements DtoBalances{

    private final StockBalances stockBalances;

    public DtoBalancesImpl(StockBalances stockBalances) {
        this.stockBalances = stockBalances;
    }

    @Override
    public List<StockDTO> getStockDTO() {
        List<StockDTO> stockDTOS = new ArrayList<>();

        for (StockEntity se: stockBalances.getStock()) {
            stockDTOS.add(new StockDTO(se.getName(),se.getStockCnt(),se.getTargetCnt()));
        }
        return stockDTOS;
    }
}
