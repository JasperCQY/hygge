package com.szyooge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 微信公众号开发
 * @ClassName: HyggeApplication
 * @author quanyou.chen
 * @date: 2017年7月25日 下午2:23:37
 * @version  v 1.0
 */
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
