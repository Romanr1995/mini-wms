package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.Map;

public interface CalcShortageService {

    Map<String, BigDecimal> calcShortage();
}
