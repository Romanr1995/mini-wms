package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.PairNumbers;

import java.util.Map;

public interface TargetService {

    void doTarget(Map<String, PairNumbers> inTarget);
}
