package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;
import java.util.Map;

public interface TargetService {

    void doTarget(Map<String, BigDecimal> inTarget);
}
