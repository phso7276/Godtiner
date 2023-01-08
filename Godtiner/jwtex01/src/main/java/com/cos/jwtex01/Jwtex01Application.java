package com.cos.jwtex01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.cos.jwtex01.entity"})
public class Jwtex01Application {

	public static void main(String[] args) {
		SpringApplication.run(Jwtex01Application.class, args);
	}

}
