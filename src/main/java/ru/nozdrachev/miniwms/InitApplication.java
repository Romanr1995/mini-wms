package ru.nozdrachev.miniwms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.ProductRepo;
import ru.nozdrachev.miniwms.repo.StockRepo;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Profile("initapplication")
@Component
public class InitApplication implements CommandLineRunner {

    private final ProductRepo productRepo;
    private final StockRepo stockRepo;
    private final UnitConversionRepo unitConversionRepo;

    public InitApplication(
        ProductRepo repo,
        StockRepo stockRepo,
        UnitConversionRepo unitConversionRepo
    ) {
        productRepo = repo;
        this.stockRepo = stockRepo;
        this.unitConversionRepo = unitConversionRepo;
    }

    @PostConstruct
    public void init() {
        System.out.println("Init");
    }

    @Override
    public void run(String... args) throws Exception {
        ProductEntity apple = productRepo.save(new ProductEntity()
                                                   .setName("apple")
                                                   .setBaseUnit(UnitOfMeasurement.KILOGRAM));

        ProductEntity banana = productRepo.save(new ProductEntity()
                                                    .setName("banana")
                                                    .setBaseUnit(UnitOfMeasurement.KILOGRAM));

        stockRepo.save(new StockEntity()
                           .setProduct(apple)
                           .setStockCnt(BigDecimal.valueOf(100))
                           .setTargetCnt(BigDecimal.valueOf(300))
        );

        stockRepo.save(new StockEntity().setProduct(banana)
                           .setStockCnt(BigDecimal.valueOf(500))
                           .setTargetCnt(BigDecimal.valueOf(500))
        );

        unitConversionRepo.save(new UnitConversionEntity()
                                    .setProduct(apple)
                                    .setAltUnit(UnitOfMeasurement.BOX)
                                    .setCoeff(BigDecimal.valueOf(1.5)));
        unitConversionRepo.save(new UnitConversionEntity()
                                    .setProduct(banana)
                                    .setAltUnit(UnitOfMeasurement.BAG)
                                    .setCoeff(new BigDecimal(1.1)));
    }
}
