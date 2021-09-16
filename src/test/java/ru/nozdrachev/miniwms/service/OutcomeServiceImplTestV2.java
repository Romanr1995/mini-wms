package ru.nozdrachev.miniwms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.ProductRecordDTO;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OutcomeServiceImplTestV2 {

    static final BigDecimal EXISTENT_CNT = new BigDecimal(10);

    static final String EXISTENT = "Existent";

    static final String NON_EXISTENT = "Non-Existent";

    static final BigDecimal CNT_LESS_THAN_EXISTENT = EXISTENT_CNT.divide(BigDecimal.valueOf(2));

    static final BigDecimal CNT_BIGGER_THAN_EXISTENT = EXISTENT_CNT.add(BigDecimal.valueOf(2));

    static final UnitOfMeasurement UNIT = UnitOfMeasurement.BOX;

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

        when(stockRepoMock.findByProductName(NON_EXISTENT))
                .thenReturn(Optional.empty());

        service = new OutcomeServiceImpl(stockRepoMock, unitConversionService);
    }

    @Test
    void проверитьСлучайКогдаБеретсяКоличествоТовараМеньшеОстатка() {
        when(unitConversionService.calculateBaseCnt(any(), any(), any()))
                .thenReturn(CNT_LESS_THAN_EXISTENT);

        service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, CNT_LESS_THAN_EXISTENT, UNIT)));

        ArgumentCaptor<StockEntity> captor = ArgumentCaptor.forClass(StockEntity.class);
        verify(stockRepoMock).save(captor.capture());

        assertEquals(EXISTENT_CNT.subtract(CNT_LESS_THAN_EXISTENT), captor.getValue().getStockCnt());
    }

    @Test
    void проверитьСлучайКогдаТоБеретсяКоличествоТовараБольшеОстатка() {
        when(unitConversionService.calculateBaseCnt(any(), any(), any()))
                .thenReturn(CNT_BIGGER_THAN_EXISTENT);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, CNT_BIGGER_THAN_EXISTENT, UNIT)))
        );
    }

    @Test
    void проверитьСлучайКогдаБеретсяКоличествоТовараРавноеОстатку() {
        when(unitConversionService.calculateBaseCnt(any(), any(), any()))
                .thenReturn(EXISTENT_CNT);

        service.doOutcomeV2(List.of(new ProductRecordDTO(EXISTENT, EXISTENT_CNT, UNIT)));
        verify(stockRepoMock).delete(any());

        verify(stockRepoMock, times(0)).save(any());
    }

    @Test
    void проверитьСлучайКогдаЗадаетсяНесуществующееПоле() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doOutcomeV2(List.of(new ProductRecordDTO(NON_EXISTENT, CNT_LESS_THAN_EXISTENT, UNIT)))
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
}
