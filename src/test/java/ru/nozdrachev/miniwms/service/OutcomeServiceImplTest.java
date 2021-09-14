package ru.nozdrachev.miniwms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.ProductRecordDTO;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OutcomeServiceImplTest {

    static final BigDecimal EXISTENT_CNT = new BigDecimal(10);

    static final String EXISTENT = "Existent";

    static final String NON_EXISTENT = "Non-Existent";

    static final BigDecimal SUBTRACT_CNT1 = new BigDecimal(5);

    static final BigDecimal SUBTRACT_CNT2 = new BigDecimal(15);

    static final BigDecimal SUBTRACT_CNT3 = new BigDecimal(10);

    static final UnitOfMeasurement UNIT = UnitOfMeasurement.BOX;

    static final ProductEntity PRODUCT_ENTITY_EXSISTENT = new ProductEntity()
            .setName(EXISTENT)
            .setBaseUnit(UnitOfMeasurement.KILOGRAM);
    static final ProductEntity PRODUCT_ENTITY_NON_EXSISTENT = new ProductEntity()
            .setName(NON_EXISTENT)
            .setBaseUnit(UnitOfMeasurement.KILOGRAM);

    OutcomeServiceImpl service;

    StockRepo stockRepoMock;

    UnitConversionService unitConversionService;

    UnitConversionRepo unitConversionRepoMock;

    @BeforeEach
    void setup() {
        stockRepoMock = mock(StockRepo.class);
        unitConversionService = mock(UnitConversionService.class);
        unitConversionRepoMock = mock(UnitConversionRepo.class);

        when(stockRepoMock.findByProductName(EXISTENT)).thenReturn(
                Optional.of(
                        new StockEntity()
                                .setStockCnt(EXISTENT_CNT)
                )
        );
        when(unitConversionRepoMock.findByProductNameAltUnit(EXISTENT, UNIT)).thenReturn(
                Optional.of(
                        new UnitConversionEntity()
                                .setProduct(PRODUCT_ENTITY_EXSISTENT)
                                .setAltUnit(UNIT)
                                .setCoeff(new BigDecimal(1.3))

                )
        );

        when(stockRepoMock.findByProductName(NON_EXISTENT)).thenReturn(Optional.empty());
        when(unitConversionRepoMock.findByProductNameAltUnit(NON_EXISTENT, UNIT)).thenReturn(
                Optional.of(
                        new UnitConversionEntity()
                                .setProduct(PRODUCT_ENTITY_NON_EXSISTENT)
                                .setAltUnit(UNIT)
                                .setCoeff(new BigDecimal(1.5))

                )
        );
        service = new OutcomeServiceImpl(stockRepoMock, unitConversionService);
    }

    @Test
    void проверитьСлучайКогдаБеретсяКоличествоТовараМеньшеОстатка() {
        service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, SUBTRACT_CNT1, UNIT)));

        ArgumentCaptor<StockEntity> captor = ArgumentCaptor.forClass(StockEntity.class);
        verify(stockRepoMock).save(captor.capture());

        assertEquals(EXISTENT_CNT.subtract(SUBTRACT_CNT1), captor.getValue().getStockCnt());
    }

    @Test
    void проверитьСлучайКогдаТоБеретсяКоличествоТовараБольшеОстатка() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, SUBTRACT_CNT2, UNIT)))
        );
    }

    @Test
    void проверитьСлучайКогдаБеретсяКоличествоТовараРавноеОстатку() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, SUBTRACT_CNT3, UNIT)))
        );
    }

    @Test
    void проверитьСлучайКогдаЗадаетсяНесуществующееПоле() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doOutcomeV2(List.of(new ProductRecordDTO(NON_EXISTENT, SUBTRACT_CNT1, UNIT)))
        );
    }

    @Test
    void проверитьСлучайКогдаПоступаетПустойЗапрос() {
        service.doOutcomeV2(List.of());

        verifyNoInteractions(stockRepoMock);
    }

    @Test
    void проверитьСлучайКогдаПоступаетОтрицательноеЗначение() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, BigDecimal.valueOf(-1), UNIT)))
        );
    }

    @Test
    void проверитьСлучайКогдапоступаетНулевоеЗначение() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, BigDecimal.valueOf(0), UNIT)))
        );
    }
//    @Test
//    void проверитьСлучайКогдаБеретсяКоличествоТовараМеньшеОстатка() {
//        service.doOutcome(Map.of(EXISTENT, SUBTRACT_CNT1));
//
//        ArgumentCaptor<StockEntity> captor = ArgumentCaptor.forClass(StockEntity.class);
//        verify(stockRepoMock).save(captor.capture());
//
//        assertEquals(EXISTENT_CNT.subtract(SUBTRACT_CNT1), captor.getValue().getStockCnt());
//    }
//
//    @Test
//    void проверитьСлучайКогдаТоБеретсяКоличествоТовараБольшеОстатка() {
//        Assertions.assertThrows(
//                RuntimeException.class,
//                () -> service.doOutcome(Map.of(EXISTENT, SUBTRACT_CNT2))
//        );
//    }
//
//    @Test
//    void проверитьСлучайКогдаБеретсяКоличествоТовараРавноеОстатку() {
//        service.doOutcome(Map.of(EXISTENT, SUBTRACT_CNT3));
//
//        verify(stockRepoMock).delete(any());
//
//        verify(stockRepoMock, times(0)).save(any());
//    }
//
//    @Test
//    void проверитьСлучайКогдаЗадаетсяНесуществующееПоле() {
//        Assertions.assertThrows(
//                RuntimeException.class,
//                () -> service.doOutcome(Map.of(NON_EXISTENT, SUBTRACT_CNT1))
//        );
//    }
//
//    @Test
//    void проверитьСлучайКогдаПоступаетПустойЗапрос() {
//        service.doOutcome(Map.of());
//
//        verifyNoInteractions(stockRepoMock);
//    }
//
//    @Test
//    void проверитьСлучайКогдаПоступаетОтрицательноеЗначение() {
//        Assertions.assertThrows(
//                RuntimeException.class,
//                () -> service.doOutcome(Map.of(EXISTENT, new BigDecimal(-1)))
//        );
//    }
//
//    @Test
//    void проверитьСлучайКогдапоступаетНулевоеЗначение() {
//        Assertions.assertThrows(
//                RuntimeException.class,
//                () -> service.doOutcome(Map.of(EXISTENT, new BigDecimal(0)))
//        );
//    }
}
