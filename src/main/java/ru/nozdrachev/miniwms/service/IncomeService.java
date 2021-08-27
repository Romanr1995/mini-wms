package ru.nozdrachev.miniwms.service;

import ru.nozdrachev.miniwms.dto.PairNumbers;

import java.util.Map;


public interface IncomeService {

    void doIncome(Map<String, PairNumbers> in);
}
