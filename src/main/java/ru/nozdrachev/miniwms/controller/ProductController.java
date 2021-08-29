package ru.nozdrachev.miniwms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nozdrachev.miniwms.dto.DtoBalances;
import ru.nozdrachev.miniwms.dto.OrderingGoods;
import ru.nozdrachev.miniwms.dto.PairNumbers;
import ru.nozdrachev.miniwms.dto.StockDTO;
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
    private final DtoBalances dtoBalances;
    private final OrderingGoods orderingGoods;

    public ProductController(IncomeService incomeService,
                             OutcomeService outcomeService,
                             TargetService targetService,
                             DtoBalances dtoBalances,
                             OrderingGoods orderingGoods) {
        this.incomeService = incomeService;
        this.outcomeService = outcomeService;
        this.targetService = targetService;
        this.dtoBalances = dtoBalances;
        this.orderingGoods = orderingGoods;
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
    public void target(@RequestBody Map<String, PairNumbers> product) {
        targetService.doTarget(product);
    }

    @GetMapping("/stockBalances")
    public List<StockDTO> getStockBalances() {
        return dtoBalances.getStockDTO();
    }

    @GetMapping("/calcShortage")
    public Map<String, BigDecimal> shortage() {
        return orderingGoods.getOrder();
    }

}