package com.github.felopssauro.calc_api_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CalcApiSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalcApiSpringApplication.class, args);
	}

}
