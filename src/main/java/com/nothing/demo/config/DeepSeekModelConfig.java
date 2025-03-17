package com.nothing.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "deepseek.model")
public class DeepSeekModelConfig {
    private String apiUrl;
    private String apiKey;
    // 默认30秒
    private int timeout = 30000;
} 