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

   private Map<String,BigDecimal> productLefts = new HashMap<>();
   private Map<String,BigDecimal> target = new HashMap<>();

    @PostMapping("/income")
    public void income(@RequestBody String productName,@RequestBody BigDecimal productCount) {
            productLefts.put(productName,productCount);
    }

//    @PostMapping("/outcome")
//    public void outcome((@RequestBody Map<String,BigDecimal> product) {
//        productLefts.remove(product);
//    }

    @PostMapping("/srtTarget")
    public void target(@RequestBody String productName,@RequestBody BigDecimal productCount) {
        target.put(productName,productCount);
    }

//    @GetMapping("/calcShortage")
//    public Map<String,BigDecimal> shortage() {
//        Map<String,BigDecimal> calc = new HashMap<>();
//
//        for (Map<String,BigDecimal> c: calc) {
//
//        }
//
//    }
}