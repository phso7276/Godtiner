package org.zerock.jpaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaWebApplication.class, args);
    }

}
