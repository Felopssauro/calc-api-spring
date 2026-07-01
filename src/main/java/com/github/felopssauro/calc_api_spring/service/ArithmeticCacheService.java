package com.github.felopssauro.calc_api_spring.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ArithmeticCacheService {

    @Cacheable(cacheNames = "arithmeticResults", key = "'add:' + #a + ':' + #b")
    public int add(int a, int b) {
        return a + b;
    }

    @Cacheable(cacheNames = "arithmeticResults", key = "'subtract:' + #a + ':' + #b")
    public int subtract(int a, int b) {
        return a - b;
    }

    @Cacheable(cacheNames = "arithmeticResults", key = "'multiply:' + #a + ':' + #b")
    public int multiply(int a, int b) {
        return a * b;
    }

    @Cacheable(cacheNames = "arithmeticResults", key = "'divide:' + #a + ':' + #b")
    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Undefined. You can't divide by zero.");
        }
        return (double) a / b;
    }
}