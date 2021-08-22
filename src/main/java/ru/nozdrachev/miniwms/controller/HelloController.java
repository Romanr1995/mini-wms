package ru.nozdrachev.miniwms.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import ru.nozdrachev.miniwms.entity.StockEntity;
import ru.nozdrachev.miniwms.repo.StockRepo;

@RestController
@Deprecated
public class HelloController {

    @PersistenceContext
    EntityManager em;

    @Autowired StockRepo stockRepo;


    @GetMapping("/hello")
    @Transactional
    public String hello(){
        stockRepo.save(
            new StockEntity()
                .setName("apple")
                .setStockCnt(BigDecimal.valueOf(10))
                .setTargetCnt(BigDecimal.valueOf(20))
        );

        StockEntity apple = stockRepo.findByName("apple").orElseThrow();

        return "Hello,world";
    }
}
