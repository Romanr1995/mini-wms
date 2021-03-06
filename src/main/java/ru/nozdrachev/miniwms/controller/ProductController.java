package ru.nozdrachev.miniwms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nozdrachev.miniwms.dto.PairNumbers;
import ru.nozdrachev.miniwms.dto.ProductRecordDTO;
import ru.nozdrachev.miniwms.dto.StockDTO;
import ru.nozdrachev.miniwms.service.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    private final IncomeService incomeService;
    private final OutcomeService outcomeService;
    private final TargetService targetService;
    private final StockDTOBalances dtoBalances;
    private final CalcShortageService calcShortageService;
    private final StockPriceService stockPrice;

    public ProductController(IncomeService incomeService,
                             OutcomeService outcomeService,
                             TargetService targetService,
                             StockDTOBalances dtoBalances,
                             CalcShortageService calcShortageService,
                             StockPriceService stockPrice) {
        this.incomeService = incomeService;
        this.outcomeService = outcomeService;
        this.targetService = targetService;
        this.dtoBalances = dtoBalances;
        this.calcShortageService = calcShortageService;
        this.stockPrice = stockPrice;
    }

    @PostMapping("/incomeV2")
    public void incomeV2(@RequestBody List<ProductRecordDTO> product) {
        incomeService.doIncomeV2(product);
    }

    @PostMapping("/outcomeV2")
    public void outcomeV2(@RequestBody List<ProductRecordDTO> product) {
        outcomeService.doOutcomeV2(product);
    }

    @PostMapping("/setTarget")
    public void setTarget(@RequestBody Map<String, PairNumbers> product) {
        targetService.doTarget(product);
    }

    @GetMapping("/stockBalances")
    public List<StockDTO> stockBalances() {
        return dtoBalances.stockBalances();
    }

    @GetMapping("/calcShortage")
    public Map<String, BigDecimal> calcShortage() {
        return calcShortageService.calcShortage();
    }

    @PostMapping("/stockPrice")
    public Map<String, BigDecimal> stockPrice(@RequestBody List<String> product) {
        return stockPrice.getPrice(product);
    }

}