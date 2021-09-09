package ru.nozdrachev.miniwms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplication implements CommandLineRunner {
//
//    private final StockRepo stockRepo;
//
//    public InitApplication(StockRepo repo) {
//        stockRepo = repo;
//    }

    @Override public void run(String... args) throws Exception {
//        stockRepo.save(
//            new StockEntity().setName("apple").setStockCnt(new BigDecimal(10))
//        );
    }
}
