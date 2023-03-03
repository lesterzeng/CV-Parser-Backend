package com.avensys.cvparser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Map<String, String> fileData() {
        return new HashMap<String, String>();
    }
}