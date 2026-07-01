package com.github.felopssauro.calc_api_spring.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.felopssauro.calc_api_spring.entity.Calculation;
import com.github.felopssauro.calc_api_spring.repository.CalculationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalculatorService {
    private final CalculationRepository calculationRepository;
    private final ArithmeticCacheService arithmeticCacheService;

    public CalculatorService(CalculationRepository calculationRepository, ArithmeticCacheService arithmeticCacheService) {
        this.calculationRepository = calculationRepository;
        this.arithmeticCacheService = arithmeticCacheService;
    }

    @Transactional
    @CacheEvict(cacheNames = {"calculationHistory", "calculationsByOperation"}, allEntries = true)
    public int add(int a, int b) {
        int result = arithmeticCacheService.add(a, b);
        saveCalculation("add", a, b, result);
        return result;
    }

    @Transactional
    @CacheEvict(cacheNames = {"calculationHistory", "calculationsByOperation"}, allEntries = true)
    public int subtract(int a, int b) {
        int result = arithmeticCacheService.subtract(a, b);
        saveCalculation("subtract", a, b, result);
        return result;
    }

    @Transactional
    @CacheEvict(cacheNames = {"calculationHistory", "calculationsByOperation"}, allEntries = true)
    public int multiply(int a, int b) {
        int result = arithmeticCacheService.multiply(a, b);
        saveCalculation("multiply", a, b, result);
        return result;
    }

    @Transactional
    @CacheEvict(cacheNames = {"calculationHistory", "calculationsByOperation"}, allEntries = true)
    public double divide(int a, int b) {
        double result = arithmeticCacheService.divide(a, b);
        saveCalculation("divide", a, b, result);
        return result;
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "calculationHistory")
    public List<Calculation> getCalculationHistory() {
        return calculationRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "calculationsByOperation", key = "#operation")
    public List<Calculation> getCalculationsByOperation(String operation) {
        return calculationRepository.findByOperation(operation);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "calculationById", key = "#id", unless = "#result == null")
    public Calculation getCalculationById(Long id) {
        return calculationRepository.findById(id).orElse(null);
    }

    private void saveCalculation(String operation, int operandA, int operandB, double result) {
        Calculation calculation = new Calculation(
                operation,
                operandA,
                operandB,
                result,
                LocalDateTime.now()
        );
        calculationRepository.save(calculation);
    }
}