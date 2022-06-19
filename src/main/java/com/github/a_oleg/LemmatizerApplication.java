package com.github.a_oleg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.github.a_oleg.entity")
@EnableJpaRepositories("com.github.a_oleg.repository")
@SpringBootApplication
public class LemmatizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LemmatizerApplication.class, args);
    }
}
