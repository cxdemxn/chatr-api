package com.chatr.shared.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class LibreTranslateConfig {

    @Value("${libretranslate.api.base-url}")
    private String libreTranslateBaseUrl;

    @Value("${libretranslate.api.base-url}")
    private String apiKey;

    @Bean
    RestTemplate libreTranslateRestTemplate() {
        return new RestTemplate();
    }
}
