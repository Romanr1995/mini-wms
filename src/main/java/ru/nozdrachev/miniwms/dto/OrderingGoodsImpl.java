package ru.nozdrachev.miniwms.dto;

import org.springframework.stereotype.Service;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.service.StockBalances;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

@Service
public class OrderingGoodsImpl implements OrderingGoods {

    private final StockBalances stockBalances;

    public OrderingGoodsImpl(StockBalances stockBalances) {
        this.stockBalances = stockBalances;
    }

    @Override
    public Map<String, BigDecimal> getOrder() {
        Map<String, BigDecimal> order = new TreeMap<>();

        for (StockEntity s : stockBalances.getStock()) {
            BigDecimal stockCnt = s.getStockCnt();
            BigDecimal targetCnt = s.getTargetCnt();

            if (stockCnt.compareTo(targetCnt) < 0) {
                order.put(s.getName(), targetCnt.subtract(stockCnt));
            }
        }
        return order;
    }
}
