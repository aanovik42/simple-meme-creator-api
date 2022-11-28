package com.aanovik42.smartmemecreatorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootApplication
public class SmartMemeCreatorApiApplication {

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartMemeCreatorApiApplication.class, args);
    }

}
