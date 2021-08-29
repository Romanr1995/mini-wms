package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalcShortageServiceImpl implements CalcShortageService {

    private final StockRepo stockRepo;

    public CalcShortageServiceImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Override
    public Map<String, BigDecimal> calcShortage() {

        return stockRepo.getAllWithShortage().stream()
                .collect(
                        Collectors.toMap(
                                st -> st.getName(),
                                st -> st.getTargetCnt().subtract(st.getStockCnt())
                        )
                );
    }
}
