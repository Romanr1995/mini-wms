package ru.nozdrachev.miniwms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StockPriceServiceTest {

    static final String EXISTENT1 = "EXISTENT1";

    static final String EXISTENT2 = "EXISTENT1";

    static final String NON_EXISTENT = "Non-EXISTENT";

    static final BigDecimal EXISTENT_CNT1 = BigDecimal.valueOf(10);

    static final BigDecimal EXISTENT_CNT2 = BigDecimal.valueOf(15);

    static final BigDecimal PRICE1 = BigDecimal.valueOf(50);

    static final BigDecimal PRICE2 = BigDecimal.valueOf(100);

    static final BigDecimal NON_EXISTENT_CNT = BigDecimal.valueOf(0);

    StockPriceService stockPriceService;

    StockRepo stockRepoMock;

    @BeforeEach
    void setup() {

        stockRepoMock = mock(StockRepo.class);

        when(stockRepoMock.findByProductName(EXISTENT1)).thenReturn(
                Optional.of(
                        new StockEntity()
                                .setStockCnt(EXISTENT_CNT1)
                                .setProduct(new ProductEntity()
                                        .setName(EXISTENT1)
                                        .setPrice(PRICE1))
                )
        );

        when(stockRepoMock.findByProductName(EXISTENT2)).thenReturn(
                Optional.of(
                        new StockEntity()
                                .setStockCnt(EXISTENT_CNT2)
                                .setProduct(new ProductEntity()
                                        .setName(EXISTENT2)
                                        .setPrice(PRICE2))
                )
        );

        when(stockRepoMock.findByProductName(NON_EXISTENT))
                .thenReturn(Optional.empty());

        stockPriceService = new StockPriceServiceImpl(stockRepoMock);
    }

    @Test
    void проверитьСлучайКогдаПоступаетЗапросНаСуществующиеЗаписи() {

        Map<String, BigDecimal> result = new HashMap<>();

        result.put(EXISTENT1, EXISTENT_CNT1.multiply(PRICE1));

        result.put(EXISTENT2, EXISTENT_CNT2.multiply(PRICE2));

        List<String> req = List.of(EXISTENT1, EXISTENT2);

        assertEquals(result, stockPriceService.getPrice(req));

    }

    @Test
    void проверитьСлучайКогдаПоступаетЗапросНаНесуществующуюЗапись() {

        Map<String, BigDecimal> result = new HashMap<>();

        result.put(NON_EXISTENT, NON_EXISTENT_CNT);

        List<String> req = List.of(NON_EXISTENT);

        assertEquals(result, stockPriceService.getPrice(req));
    }

    @Test
    void проверитьСлучайКогдаПоступаетЗапросНаСуществующиеИНеСуществующиеЗаписи() {

        Map<String, BigDecimal> result = new HashMap<>();

        result.put(EXISTENT1, EXISTENT_CNT1.multiply(PRICE1));

        result.put(NON_EXISTENT, NON_EXISTENT_CNT);

        result.put(EXISTENT2, EXISTENT_CNT2.multiply(PRICE2));

        List<String> req = List.of(EXISTENT1, NON_EXISTENT, EXISTENT2);

        assertEquals(result, stockPriceService.getPrice(req));
    }

    @Test
    void пустойЗапрос() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> stockPriceService.getPrice(List.of())
        );
    }
}
