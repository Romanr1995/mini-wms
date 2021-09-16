package ru.nozdrachev.miniwms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StockPriceServiceImpl implements StockPriceService {

    private final StockRepo stockRepo;

    public StockPriceServiceImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Transactional
    @Override
    public Map<String, BigDecimal> getPrice(List<String> product) {
        Map<String, BigDecimal> mapPrice = new HashMap<>();

        if (product.isEmpty()) {
            throw new RuntimeException("Не задано ни одного поля в запросе");
        }
        for (String s : product) {
            Optional<StockEntity> stockEntityOptional = stockRepo.findByProductName(s);

            if (stockEntityOptional.isPresent()) {
                StockEntity stockEntity = stockEntityOptional.get();

                String name = s;
                BigDecimal cnt = stockEntity.getStockCnt();
                BigDecimal priceCnt = stockEntity.getProduct().getPrice();

                BigDecimal totalPriceProduct = cnt.multiply(priceCnt);

                mapPrice.put(name, totalPriceProduct);
            } else if (stockEntityOptional.isEmpty()) {
                mapPrice.put(s, BigDecimal.valueOf(0));
            }
        }
        return mapPrice;
    }
}
