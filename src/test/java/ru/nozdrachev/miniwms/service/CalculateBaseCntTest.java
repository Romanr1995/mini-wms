package ru.nozdrachev.miniwms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculateBaseCntTest {

    static final String EXISTENT = "Existent";

    static final String NON_EXISTENT = "Non-Existent";

    static final UnitOfMeasurement UNIT_EXISTENT = UnitOfMeasurement.BAG;

    static final UnitOfMeasurement UNIT_NON_EXISTENT = UnitOfMeasurement.BOTTLE;

    static final BigDecimal ADD_CNT = BigDecimal.valueOf(10);

    static final BigDecimal COEFF = BigDecimal.valueOf(1.5);

    UnitConversionService unitConversionService;

    UnitConversionRepo unitConversionRepo;

    @BeforeEach
    void setup() {
        unitConversionRepo = mock(UnitConversionRepo.class);

        when(unitConversionRepo.findByProductNameAltUnit(EXISTENT, UNIT_EXISTENT))
                .thenReturn(
                        Optional.of(new UnitConversionEntity()
                                .setProduct(new ProductEntity().setName(EXISTENT)
                                        .setBaseUnit(UnitOfMeasurement.KILOGRAM))
                                .setAltUnit(UNIT_EXISTENT)
                                .setCoeff(COEFF))
                );

        unitConversionService = new UnitConversionServiceImpl(unitConversionRepo);
    }

    @Test
    void проверитьСлучайКогдаПоступаетИспользуемоеИмяИИспользуемаяЕдиницаИзмерения() {

        assertEquals(ADD_CNT.multiply(COEFF), unitConversionService.calculateBaseCnt(EXISTENT, ADD_CNT, UNIT_EXISTENT));
    }

    @Test
    void проверитьСлучайКогдаПоступаетНоль() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> unitConversionService.calculateBaseCnt(EXISTENT, BigDecimal.valueOf(0), UNIT_EXISTENT)
        );
    }

    @Test
    void проверитьСлучайКогдаПоступаетОтрицательноеЗначение() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> unitConversionService.calculateBaseCnt(EXISTENT, BigDecimal.valueOf(-5), UNIT_EXISTENT)
        );
    }

    @Test
    void проверитьСлучайКогдаПоступаетНеиспользуемоеИмя() {

        Assertions.assertThrows(
                RuntimeException.class,
                () -> unitConversionService.calculateBaseCnt(NON_EXISTENT, ADD_CNT, UNIT_EXISTENT)
        );
    }

    @Test
    void проверитьСлучайКогдаПоступаетНеиспользуемаяЕдиницаИзмерения() {

        Assertions.assertThrows(
                RuntimeException.class,
                () -> unitConversionService.calculateBaseCnt(EXISTENT, ADD_CNT, UNIT_NON_EXISTENT)
        );
    }
}
