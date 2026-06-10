package com.github.felopssauro.calc_api_spring.repository;

import com.github.felopssauro.calc_api_spring.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    List<Calculation> findByOperation(String operation);

    List<Calculation> findAllByOrderByCreatedAtDesc();
}
