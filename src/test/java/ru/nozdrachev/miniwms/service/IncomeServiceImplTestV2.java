package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class IncomeServiceImplTestV2 {

    static final BigDecimal EXISTENT_CNT = new BigDecimal(10);

    /** Название существующего товара. */
    static final String EXISTENT = "Existent";

    /** Название отсутствующего товара. */
    static final String NON_EXISTENT = "Non-Existent";

    static final BigDecimal ADD_CNT = new BigDecimal(5);

    IncomeServiceImpl service;

    StockRepo stockRepoMock;
    UnitConversionService unitConversionService;

    @BeforeEach
    void setup() {
        stockRepoMock = mock(StockRepo.class);
        unitConversionService = mock(UnitConversionService.class);

        when(stockRepoMock.findByName(EXISTENT)).thenReturn(
            Optional.of(
                new StockEntity()
                    .setStockCnt(EXISTENT_CNT)
            )
        );

        when(stockRepoMock.findByName(NON_EXISTENT)).thenReturn(Optional.empty());

        service = new IncomeServiceImpl(stockRepoMock,unitConversionService);
    }

    /**
     * stockCnt у записи увеличивается на величину переданное в параметре, если запись существует.
     */
//    @Test
//    void shouldIncreaseStockCntWhenEntityExistent() {
//        service.doIncome(Map.of(EXISTENT, ADD_CNT));
//
//        ArgumentCaptor<StockEntity> argCaptor = ArgumentCaptor.forClass(StockEntity.class);
//        verify(stockRepoMock).save(argCaptor.capture());
//
//        assertEquals(EXISTENT_CNT.add(ADD_CNT), argCaptor.getValue().getStockCnt());
//    }
//
//    @Test
//    void shouldInsertNewRecordWhenEntityNonExistent() {
//        service.doIncome(Map.of(NON_EXISTENT, ADD_CNT));
//
//        ArgumentCaptor<StockEntity> argCaptor = ArgumentCaptor.forClass(StockEntity.class);
//        verify(stockRepoMock).save(argCaptor.capture());
//
//        assertEquals(ADD_CNT, argCaptor.getValue().getStockCnt());
//    }
//
//    @Test
//    void shouldNotInteractWithRepoWhenArgIsEmpty() {
//        service.doIncome(Map.of());
//
//        verifyNoInteractions(stockRepoMock);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenArgContainsNegativeCnt() {
//        Assertions.assertThrows(
//            RuntimeException.class,
//            () -> service.doIncome(Map.of(EXISTENT, new BigDecimal(-1)))
//        );
//    }
//
//    @Test
//    void shouldThrowExceptionWhenArgContainsZeroCnt() {
//        Assertions.assertThrows(
//            RuntimeException.class,
//            () -> service.doIncome(Map.of(EXISTENT, new BigDecimal(0)))
//        );
//    }
}
