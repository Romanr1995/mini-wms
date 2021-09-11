package ru.nozdrachev.miniwms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import java.math.BigDecimal;

@Component
public class InitApplication implements CommandLineRunner {

    private final ProductRepo productRepo;
    private final UnitConversionRepo unitConversionRepo;

    public InitApplication(ProductRepo productRepo, UnitConversionRepo unitConversionRepo) {
        this.productRepo = productRepo;
        this.unitConversionRepo = unitConversionRepo;
    }

    @Override public void run(String... args) throws Exception {

        productRepo.save(
                new ProductEntity().setName("apple").setBase(UnitOfMeasurement.KILOGRAM)
        );

        unitConversionRepo.save(
                new UnitConversionEntity().setProductName("apple").setAltUnit(UnitOfMeasurement.BOX)
                .setCoeff(new BigDecimal(1.5))
        );
    }
}
