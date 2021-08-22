package ru.nozdrachev.miniwms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {


    @PostMapping("/income")
    public void income(@RequestBody Map<String,BigDecimal> product) {
        System.out.println(product);
    }

    @PostMapping("/outcome")
    public void outcome(@RequestBody Map<String,BigDecimal> product) {

    }

    @PostMapping("/setTarget")
    public void target(@RequestBody Map<String,BigDecimal> product) {

    }

    @GetMapping("/calcShortage")
    public Map<String,BigDecimal> shortage() {
        return Map.of("apple",BigDecimal.valueOf(10),"banana",BigDecimal.valueOf(15));
    }
}