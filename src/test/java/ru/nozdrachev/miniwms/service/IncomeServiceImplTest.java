package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ru.nozdrachev.miniwms.testutils.AssertUtils.assertEquals;

class IncomeServiceImplTest {

    IncomeServiceImpl service;
    StockRepo stockRepoMock;
    UnitConversionService unitConversionService;

    @BeforeEach
    void setup() {
        stockRepoMock = mock(StockRepo.class);
        unitConversionService = mock(UnitConversionService.class);

        service = new IncomeServiceImpl(stockRepoMock,unitConversionService);
    }

    @Test
    void whenEntityIsPresentThenWillUpdated() {
        String name = "apple";
        long id = 10L;
        BigDecimal stockCnt = new BigDecimal(200);
        BigDecimal targetCnt = new BigDecimal(500);

        BigDecimal additionStockCnt = new BigDecimal(10);

        Map<String, BigDecimal> arg = Map.of(name, additionStockCnt);

        when(stockRepoMock.findByName(name))
            .thenReturn(
                Optional.of(
                    new StockEntity()
                        .setId(id)
                        .setName(name)
                        .setStockCnt(stockCnt)
                        .setTargetCnt(targetCnt)
                )
            );

        service.doIncome(arg);

        ArgumentCaptor<StockEntity> argCaptor = ArgumentCaptor.forClass(StockEntity.class);

        verify(stockRepoMock/*, times(1)*/).save(argCaptor.capture());

        StockEntity actual = argCaptor.getValue();

        StockEntity expected = new StockEntity()
            .setId(id)
            .setName(name)
            .setStockCnt(stockCnt.add(additionStockCnt))
            .setTargetCnt(targetCnt);

        assertEquals(expected, actual);

        verify(stockRepoMock).findByName(any());

        verifyNoMoreInteractions(stockRepoMock);
    }

    @Test
    void whenEntityIsEmptyThenNewEntityWillBeSaved() {
        String name = "apple";

        BigDecimal additionStockCnt = new BigDecimal(10);

        Map<String, BigDecimal> arg = Map.of(name, additionStockCnt);

        service.doIncome(arg);

        ArgumentCaptor<StockEntity> argCaptor = ArgumentCaptor.forClass(StockEntity.class);

        verify(stockRepoMock).save(argCaptor.capture());

        StockEntity actual = argCaptor.getValue();

        StockEntity expected = new StockEntity()
            .setName(name)
            .setStockCnt(additionStockCnt);

        assertEquals(expected, actual);

        verify(stockRepoMock).findByName(any());

        verifyNoMoreInteractions(stockRepoMock);
    }
}