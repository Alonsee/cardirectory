package com.myprojects.cardirectory.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.myprojects.cardirectory.config",
                "com.myprojects.cardirectory.controller",
                "com.myprojects.cardirectory.service"})
@EnableJpaRepositories("com.myprojects.cardirectory.repository")
@EntityScan("com.myprojects.cardirectory.pojo")
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

}
