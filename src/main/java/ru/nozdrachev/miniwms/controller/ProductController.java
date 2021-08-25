package ru.nozdrachev.miniwms.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nozdrachev.miniwms.service.IncomeService;
import ru.nozdrachev.miniwms.service.OutcomeService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@RestController
public class ProductController {

    private final IncomeService incomeService;
    private final OutcomeService outcomeService;

    public ProductController(@Qualifier("incomeProduct") IncomeService incomeService,
                             @Qualifier("outcomeProduct") OutcomeService outcomeService) {
        this.incomeService = incomeService;
        this.outcomeService = outcomeService;
    }

    @PostMapping("/income")
    public void income(@RequestBody Map<String,BigDecimal> product) {
        incomeService.doIncome(product);
    }

    @PostMapping("/outcome")
    public void outcome(@RequestBody Map<String,BigDecimal> product) {
        outcomeService.doOutcome(product);
    }

    @PostMapping("/setTarget")
    public void target(@RequestBody Map<String,BigDecimal> product) {

    }

    @GetMapping("/calcShortage")
    public Map<String,BigDecimal> shortage() {
        return Map.of("apple",BigDecimal.valueOf(10),"banana",BigDecimal.valueOf(15));
    }
}