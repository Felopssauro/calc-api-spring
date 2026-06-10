package com.github.felopssauro.calc_api_spring.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculation")
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    private int operandA;

    @Column(nullable = false)
    private int operandB;

    @Column(nullable = false)
    private double result;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Calculation() {
    }

    public Calculation(String operation, int operandA, int operandB, double result, LocalDateTime createdAt) {
        this.operation = operation;
        this.operandA = operandA;
        this.operandB = operandB;
        this.result = result;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getOperandA() {
        return operandA;
    }

    public void setOperandA(int operandA) {
        this.operandA = operandA;
    }

    public int getOperandB() {
        return operandB;
    }

    public void setOperandB(int operandB) {
        this.operandB = operandB;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Calculation [id=" + id + ", operation=" + operation + ", operandA=" + operandA + ", operandB="
                + operandB + ", result=" + result + ", createdAt=" + createdAt + "]";
    }

}
