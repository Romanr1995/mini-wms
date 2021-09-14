package ru.nozdrachev.miniwms.service;

import org.junit.jupiter.api.BeforeEach;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
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

    OutcomeServiceImpl service;

    StockRepo stockRepoMock;

    UnitConversionService unitConversionService;

    @BeforeEach
    void setup() {
        stockRepoMock = mock(StockRepo.class);
        unitConversionService = mock(UnitConversionService.class);

        when(stockRepoMock.findByProductName(EXISTENT)).thenReturn(
                Optional.of(
                        new StockEntity()
                                .setStockCnt(EXISTENT_CNT)
                )
        );

        when(stockRepoMock.findByProductName(NON_EXISTENT)).thenReturn(Optional.empty());

        service = new OutcomeServiceImpl(stockRepoMock,unitConversionService);
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
