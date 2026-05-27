package com.github.felopssauro.calc_api_spring.dto;

public class NumberInput {
    private int a;
    private int b;

    public NumberInput() {}

    public NumberInput(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
