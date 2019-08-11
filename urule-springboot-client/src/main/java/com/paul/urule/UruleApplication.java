package com.paul.urule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:urule-core-context.xml"})
@SpringBootApplication
public class UruleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UruleApplication.class, args);
    }

}
