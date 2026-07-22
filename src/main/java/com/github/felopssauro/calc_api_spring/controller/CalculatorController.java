package com.github.felopssauro.calc_api_spring.controller;

import com.github.felopssauro.calc_api_spring.dto.CalculationResult;
import com.github.felopssauro.calc_api_spring.dto.NumberInput;
import com.github.felopssauro.calc_api_spring.repository.CalculationRepository;
import com.github.felopssauro.calc_api_spring.service.CalculatorService;
import com.github.felopssauro.calc_api_spring.entity.Calculation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@RestController
@RequestMapping("/calculator")
@CrossOrigin(origins = "*")
public class CalculatorController {
    private final CalculationRepository calculationRepository;
    private final CalculatorService service;

    public CalculatorController(CalculatorService service,
                                CalculationRepository calculationRepository) {
        this.service = service;
        this.calculationRepository = calculationRepository;
    }

    @PostMapping("/add")
    public CalculationResult add(@RequestBody NumberInput input) {
        int sum = service.add(input.getA(), input.getB());
        return new CalculationResult(sum);
    }

    @PostMapping("/subtract")
    public CalculationResult subtract(@RequestBody NumberInput input) {
        int difference = service.subtract(input.getA(), input.getB());
        return new CalculationResult(difference);
    }

    @PostMapping("/multiply")
    public CalculationResult multiply(@RequestBody NumberInput input) {
        int product = service.multiply(input.getA(), input.getB());
        return new CalculationResult(product);
    }

    @PostMapping("/divide")
    public CalculationResult divide(@RequestBody NumberInput input) {
        try {
            double quocient = service.divide(input.getA(), input.getB());
            return new CalculationResult(quocient);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/history")
    public List<Calculation> getHistory() {
        return service.getCalculationHistory();
    }

    @GetMapping("/history/{id}")
    public Calculation getHistoryById(@PathVariable Long id) {
        Calculation calculation = service.getCalculationById(id);
        if (calculation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Calculation not found");
        }
        return calculation;
    }

    @GetMapping("/history/operation/{type}")
    public List<Calculation> getHistoryByOperation(@PathVariable String type) {
        return service.getCalculationsByOperation(type);
    }
}
