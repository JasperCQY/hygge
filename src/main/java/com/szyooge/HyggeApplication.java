package com.szyooge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
@EnableCaching
public class HyggeApplication {
    
    @Bean
    public MappingJackson2HttpMessageConverter json() {
        return new MappingJackson2HttpMessageConverter();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(HyggeApplication.class, args);
    }
}
