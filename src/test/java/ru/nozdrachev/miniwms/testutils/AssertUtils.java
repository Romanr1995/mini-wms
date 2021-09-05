package ru.nozdrachev.miniwms.testutils;

import org.junit.jupiter.api.Assertions;
import ru.nozdrachev.miniwms.entity.StockEntity;

public class AssertUtils {

    public static void assertEquals(StockEntity expected, StockEntity actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getStockCnt(), actual.getStockCnt());
        Assertions.assertEquals(expected.getTargetCnt(), actual.getTargetCnt());
    }
}
