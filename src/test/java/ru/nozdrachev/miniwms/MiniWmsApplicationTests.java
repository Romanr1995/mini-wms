package ru.nozdrachev.miniwms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.domain.UnitOfMeasurement;
import ru.nozdrachev.miniwms.dto.StockDTO;
import ru.nozdrachev.miniwms.entity.ProductEntity;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.entity.UnitConversionEntity;
import ru.nozdrachev.miniwms.repo.ProductRepo;
import ru.nozdrachev.miniwms.repo.StockRepo;
import ru.nozdrachev.miniwms.repo.UnitConversionRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles
@SpringBootTest
@AutoConfigureMockMvc
class MiniWmsApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    StockRepo stockRepo;

    @Autowired
    UnitConversionRepo unitConversionRepo;

    @Autowired
    ProductRepo productRepo;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    @Transactional
    void setup() {
        ProductEntity apple = productRepo.save(new ProductEntity()
                                                   .setName("apple")
                                                   .setBaseUnit(UnitOfMeasurement.KILOGRAM));

        ProductEntity banana = productRepo.save(new ProductEntity()
                                                    .setName("banana")
                                                    .setBaseUnit(UnitOfMeasurement.KILOGRAM));

        stockRepo.save(new StockEntity()
                           .setProduct(apple)
                           .setStockCnt(BigDecimal.valueOf(100))
                           .setTargetCnt(BigDecimal.valueOf(300))
        );

        stockRepo.save(new StockEntity().setProduct(banana)
                           .setStockCnt(BigDecimal.valueOf(500))
                           .setTargetCnt(BigDecimal.valueOf(500))
        );

        unitConversionRepo.save(new UnitConversionEntity()
                                    .setProduct(apple)
                                    .setAltUnit(UnitOfMeasurement.BOX)
                                    .setCoeff(BigDecimal.valueOf(1.5)));
        unitConversionRepo.save(new UnitConversionEntity()
                                    .setProduct(banana)
                                    .setAltUnit(UnitOfMeasurement.BAG)
                                    .setCoeff(new BigDecimal(1.1)));
    }

    @AfterEach
    @Transactional
    void cleanup() {
        stockRepo.deleteAll();
        unitConversionRepo.deleteAll();
        productRepo.deleteAll();
    }

    @Test
    void givenJson_andIncomeRequestSuccessfulV2() throws Exception {
        String body = """
            [
            {"name": "apple","count": 1.0,"unitName": "BOX"},
            {"name": "banana","count": 3.5,"unitName": "BAG"}
            ]
            """;

        mockMvc.perform(
                post("/incomeV2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            )
            .andExpect(status().isOk());
    }

    @Test
    void givenJson_andOutcomeRequestSuccessfulV2() throws Exception {
        String body = """
            [
            {"name": "apple","count": 1.5,"unitName": "BOX"},
            {"name": "banana","count": 2.0,"unitName": "BAG"}
            ]
            """;

        mockMvc.perform(
                post("/outcomeV2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            )
            .andExpect(status().isOk());
    }

    @Test
    void getStockBalancesAndCheckJson() throws Exception {
        String result = mockMvc.perform(get("/stockBalances"))
            .andReturn().getResponse().getContentAsString();

        List<StockDTO> dtos = mapper.readValue(result, new TypeReference<>() {
        });

        int cnt = 0;

        for (StockDTO dto : dtos) {
            if (dto.getName().equals("apple")) {
                cnt++;
                assertEquals(dto.getCnt(), new BigDecimal("100.00"));
            }

            if (dto.getName().equals("banana")) {
                cnt++;
                assertEquals(dto.getCnt(), new BigDecimal("500.00"));
            }
        }

        assertEquals(2, cnt);
    }

    @Test
    void getCalcShortageAndCheckJson() throws Exception {
        String result = mockMvc.perform(get("/calcShortage"))
            .andReturn().getResponse().getContentAsString();

        Map<String, BigDecimal> calc = mapper.readValue(result, new TypeReference<Map<String, BigDecimal>>() {
        });

        assertEquals(calc.get("apple"), new BigDecimal("200.00"));

    }

}
