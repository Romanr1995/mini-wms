package ru.nozdrachev.miniwms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Profile("initapplication")
@Component
public class InitApplication implements CommandLineRunner {

    private final UnitConversionRepo unitConversionRepo;

    public InitApplication(UnitConversionRepo unitConversionRepo) {
        this.unitConversionRepo = unitConversionRepo;
    }

    @PostConstruct
    public void init() {
        System.out.println("Init");
    }

    @Override
    public void run(String... args) throws Exception {

        unitConversionRepo.save(
                new UnitConversionEntity().setProductName("apple").setAltUnit(UnitOfMeasurement.BOX)
                        .setCoeff(new BigDecimal(1.5))
        );
    }
}
