package com.github.felopssauro.calc_api_spring.controller;

import com.github.felopssauro.calc_api_spring.dto.CalculationResult;
import com.github.felopssauro.calc_api_spring.dto.NumberInput;
import com.github.felopssauro.calc_api_spring.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/calculator")
@CrossOrigin(origins = "*")
public class CalculatorController {
    private final CalculatorService service;

    public CalculatorController(CalculatorService service) {
        this.service = service;
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
}
