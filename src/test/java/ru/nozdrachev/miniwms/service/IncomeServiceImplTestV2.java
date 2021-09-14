package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.ProductRecordDTO;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.ProductRepo;
import ru.nozdrachev.miniwms.repo.StockRepo;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IncomeServiceImplTestV2 {

    static final BigDecimal EXISTENT_CNT = new BigDecimal(10);

    /**
     * Название существующего товара.
     */
    static final String EXISTENT = "Existent";

    /**
     * Название отсутствующего товара.
     */
    static final String NON_EXISTENT = "Non-Existent";
    static final UnitOfMeasurement UNIT = UnitOfMeasurement.BOX;
    static final BigDecimal ADD_CNT = new BigDecimal(5);

    static final ProductRecordDTO PRODUCT_EXISTENT = new ProductRecordDTO(EXISTENT, ADD_CNT, UNIT);
    static final ProductRecordDTO PRODUCT_NON_EXISTENT = new ProductRecordDTO(NON_EXISTENT, ADD_CNT, UNIT);
    static final ProductEntity PRODUCT_ENTITY_EXSISTENT = new ProductEntity()
            .setName(EXISTENT)
            .setBaseUnit(UnitOfMeasurement.KILOGRAM);
    static final ProductEntity PRODUCT_ENTITY_NON_EXSISTENT = new ProductEntity()
            .setName(NON_EXISTENT)
            .setBaseUnit(UnitOfMeasurement.KILOGRAM);
    public static final BigDecimal ADD_BASE_CNT = BigDecimal.valueOf(40);

    IncomeServiceImpl service;

    StockRepo stockRepoMock;
    UnitConversionService unitConversionService;
    ProductRepo productRepoMock;


    @BeforeEach
    void setup() {
        stockRepoMock = mock(StockRepo.class);
        unitConversionService = mock(UnitConversionService.class);


        productRepoMock = mock(ProductRepo.class);

        when(stockRepoMock.findByProductName(EXISTENT))
                .thenReturn(
                        Optional.of(
                                new StockEntity()
                                        .setProduct(PRODUCT_ENTITY_EXSISTENT)
                                        .setStockCnt(EXISTENT_CNT)
                                        .setTargetCnt(BigDecimal.valueOf(300))
                        )
                );

        when(unitConversionService.calculateBaseCnt(any(), any(), any()))
                .thenReturn(ADD_BASE_CNT);

        when(productRepoMock.findByName(EXISTENT))
                .thenReturn(Optional.of(PRODUCT_ENTITY_EXSISTENT));

        when(productRepoMock.findByName(NON_EXISTENT))
                .thenReturn(Optional.of(PRODUCT_ENTITY_NON_EXSISTENT));

        when(stockRepoMock.findByProductName(NON_EXISTENT))
                .thenReturn(Optional.empty());

        service = new IncomeServiceImpl(stockRepoMock, unitConversionService, productRepoMock);
    }

    /**
     * stockCnt у записи увеличивается на величину переданное в параметре, если запись существует.
     */

    @Test
    void shouldIncreaseStockCntWhenEntityExistent() {

        service.doIncomeV2(List.of(PRODUCT_EXISTENT));

        ArgumentCaptor<StockEntity> argCaptor = ArgumentCaptor.forClass(StockEntity.class);
        verify(stockRepoMock).save(argCaptor.capture());

        assertEquals(EXISTENT_CNT.add(ADD_BASE_CNT), argCaptor.getValue().getStockCnt());
    }

    @Test
    void shouldInsertNewRecordWhenEntityNonExistent() {
        service.doIncomeV2(List.of(PRODUCT_NON_EXISTENT));

        ArgumentCaptor<StockEntity> argCaptor = ArgumentCaptor.forClass(StockEntity.class);
        verify(stockRepoMock).save(argCaptor.capture());

        assertEquals(ADD_BASE_CNT, argCaptor.getValue().getStockCnt());
    }

    @Test
    void shouldNotInteractWithRepoWhenArgIsEmpty() {
        service.doIncomeV2(List.of());

        verifyNoInteractions(stockRepoMock);
    }

    @Test
    void shouldThrowExceptionWhenArgContainsNegativeCnt() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doIncomeV2(List.of(new ProductRecordDTO(EXISTENT, new BigDecimal(-1), UNIT)))
        );
    }

    @Test
    void shouldThrowExceptionWhenArgContainsZeroCnt() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doIncomeV2(List.of(new ProductRecordDTO(EXISTENT, new BigDecimal(0), UNIT)))
        );
    }

    @Test
    void shouldThrowExceptionWhenArgContainsZeroProductEntity() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> service.doIncomeV2(List.of(new ProductRecordDTO("milk", new BigDecimal(5), UNIT)))
        );
    }
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
