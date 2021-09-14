package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import ru.nozdrachev.miniwms.dto.StockDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockDTOBalancesImpl implements StockDTOBalances {

    private final StockBalances stockBalances;

    public StockDTOBalancesImpl(StockBalances stockBalances) {
        this.stockBalances = stockBalances;
    }


    @Override
    public List<StockDTO> stockBalances() {
        List<StockDTO> stockDTOS = new ArrayList<>();

        stockDTOS = stockBalances.getStock().stream()
                .map(st -> new StockDTO(st.getProduct().getName(), st.getStockCnt(), st.getTargetCnt()))
                .collect(Collectors.toList());

        return stockDTOS;
    }
}
