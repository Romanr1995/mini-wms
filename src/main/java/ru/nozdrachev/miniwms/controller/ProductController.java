package ru.nozdrachev.miniwms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nozdrachev.miniwms.dto.StockDTO;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.service.IncomeService;
import ru.nozdrachev.miniwms.service.OutcomeService;
import ru.nozdrachev.miniwms.service.TargetService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    private final IncomeService incomeService;
    private final OutcomeService outcomeService;
    private final TargetService targetService;

    public ProductController(IncomeService incomeService,
                             OutcomeService outcomeService,
                             TargetService targetService) {
        this.incomeService = incomeService;
        this.outcomeService = outcomeService;
        this.targetService = targetService;
    }

    @PostMapping("/income")
    public void income(@RequestBody Map<String, BigDecimal> product) {
        incomeService.doIncome(product);
    }

    @PostMapping("/outcome")
    public void outcome(@RequestBody Map<String, BigDecimal> product) {
        outcomeService.doOutcome(product);
    }

    @PostMapping("/setTarget")
    public void target(@RequestBody Map<String, BigDecimal> product) {
        targetService.doTarget(product);
    }

    @GetMapping("/stockBalances")
    public List<StockDTO> getStockBalances() {
        return List.of(new StockDTO("apple",BigDecimal.valueOf(3),BigDecimal.valueOf(10)));
    }

    @GetMapping("/calcShortage")
    public Map<String, BigDecimal> shortage() {
        return Map.of("apple", BigDecimal.valueOf(10), "banana", BigDecimal.valueOf(15));
    }
}