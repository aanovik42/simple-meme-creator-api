package com.aanovik42.smartmemecreatorapi.config;

import com.jhlabs.image.GaussianFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GaussianFilterConfig {

    private final float radius;

    public GaussianFilterConfig(@Value("${gaussianFilter.radius}") float radius) {
        this.radius = radius;
    }

    @Bean
    public GaussianFilter getGaussianFilter() {
        return new GaussianFilter(radius);
    }
}
