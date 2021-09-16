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
public class StockPriceImpl implements StockPrice {

    private final StockRepo stockRepo;

    public StockPriceImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

//    @Transactional
//    @Override
//    public Map<String, BigDecimal> getPrice(List<String> product) {
//        Map<String, BigDecimal> mapPrice = new HashMap<>();
//
//        Iterable<StockEntity> all = stockRepo.findAll();
//
//        for (StockEntity stockEntity : all) {
//
//            String name = stockEntity.getProduct().getName();
//
//            if (product.contains(name)) {
//                BigDecimal cnt = stockEntity.getStockCnt();
//                BigDecimal priceCnt = stockEntity.getProduct().getPrice();
//
//                BigDecimal totalPriceProduct = cnt.multiply(priceCnt);
//
//                mapPrice.put(name, totalPriceProduct);
//                product.remove(name);
//            }
//        }
//
//        for (String s : product) {
//            mapPrice.put(s, BigDecimal.valueOf(0));
//        }
//        return mapPrice;
//    }

    @Transactional
    @Override
    public Map<String, BigDecimal> getPrice(List<String> product) {
        Map<String, BigDecimal> mapPrice = new HashMap<>();

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
