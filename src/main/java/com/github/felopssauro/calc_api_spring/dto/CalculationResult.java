package com.github.felopssauro.calc_api_spring.dto;

public class CalculationResult {
    private double result;

    public CalculationResult() {}

    public CalculationResult(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
