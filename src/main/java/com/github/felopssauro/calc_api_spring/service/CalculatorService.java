package com.github.felopssauro.calc_api_spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.felopssauro.calc_api_spring.entity.Calculation;
import com.github.felopssauro.calc_api_spring.repository.CalculationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalculatorService {
    private final CalculationRepository calculationRepository;

    public CalculatorService(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
    }

    @Transactional
    public int add(int a, int b) {
        int result = a + b;
        saveCalculation("add", a, b, result);
        return result;
    }

    @Transactional
    public int subtract(int a, int b) {
        int result = a - b;
        saveCalculation("subtract", a, b, result);
        return result;
    }

    @Transactional
    public int multiply(int a, int b) {
        int result = a * b;
        saveCalculation("multiply", a, b, result);
        return result;
    }

    @Transactional
    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Undefined. You can't divide by zero.");
        }
        double result = (double) a / b;
        saveCalculation("divide", a, b, result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Calculation> getCalculationHistory() {
        return calculationRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Calculation> getCalculationsByOperation(String operation) {
        return calculationRepository.findByOperation(operation);
    }

    @Transactional(readOnly = true)
    public Optional<Calculation> getCalculationById(Long id) {
        return calculationRepository.findById(id);
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