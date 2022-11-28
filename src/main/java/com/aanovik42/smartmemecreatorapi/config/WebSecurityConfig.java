package com.aanovik42.smartmemecreatorapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final boolean requireHttps;

    public WebSecurityConfig(@Value("${deploy.security.require-https}") boolean requireHttps) {
        this.requireHttps = requireHttps;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        if(requireHttps)
            http.requiresChannel((channel) -> channel.anyRequest().requiresSecure());

        http
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
        return http.build();
    }

}
