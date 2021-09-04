package ru.nozdrachev.miniwms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.nozdrachev.miniwms.dto.StockDTO;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MiniWmsApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    StockRepo stockRepo;

    ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    @Transactional
    void setup() {
        stockRepo.save(new StockEntity().setName("apple").setStockCnt(new BigDecimal(100))
                .setTargetCnt(new BigDecimal(300)));
        stockRepo.save(new StockEntity().setName("banana").setStockCnt(new BigDecimal(500))
                .setTargetCnt(new BigDecimal(500)));
    }


    @AfterEach
    @Transactional
    void cleanup() {
        stockRepo.deleteAll();
    }

    @Test
    void givenJson_andIncomeRequestSuccessful() throws Exception {
        String body = """
                {
                "apple": 1.5,
                "banana": 2.5
                }
                """;
        mockMvc.perform(
                post("/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        )
                .andExpect(status().isOk());
    }

    @Test
    void givenJson_andOutcomeRequestSuccessful() throws Exception {
        String body = """
                {
                "apple": 0.5,
                "banana": 2.5
                }
                """;

        mockMvc.perform(
                post("/outcome")
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

        int cnt = 0;

        for (Map.Entry<String, BigDecimal> m : calc.entrySet()) {

            if (m.getKey().equals("apple")) {
                assertEquals(calc.get("apple"), new BigDecimal("200"));
                cnt++;
            }

            assertEquals(1, cnt);
        }

    }

}
