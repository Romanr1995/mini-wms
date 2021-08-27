package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.PairNumbers;

import java.util.Map;

public interface OutcomeService {

    void doOutcome(Map<String, PairNumbers> out);
}
